import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import moment from 'moment';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import CompanyUserService from '../../company//company-user/company-user.service';
import { ICompanyUser } from '@/shared/model/company/company-user.model';

import CompanyBusinessService from '../../company//company-business/company-business.service';
import { ICompanyBusiness } from '@/shared/model/company/company-business.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompanyCustomer, CompanyCustomer } from '@/shared/model/company/company-customer.model';
import CompanyCustomerService from './company-customer.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  companyCustomer: {
    name: {},
    code: {},
    address: {},
    phoneNum: {},
    logo: {},
    contact: {},
    createUserId: {},
    createTime: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class CompanyCustomerUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('companyCustomerService') private companyCustomerService: () => CompanyCustomerService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public companyCustomer: ICompanyCustomer = new CompanyCustomer();

  public companyCustomers: ICompanyCustomer[] = [];

  @Inject('companyUserService') private companyUserService: () => CompanyUserService;

  public companyUsers: ICompanyUser[] = [];

  @Inject('companyBusinessService') private companyBusinessService: () => CompanyBusinessService;

  public companyBusinesses: ICompanyBusiness[] = [];
  public isSaving = false;
  public loading = false;
  @Ref('updateForm') readonly updateForm: any;
  public formJsonData = {
    list: [],
    config: {
      layout: 'horizontal',
      labelCol: { span: 4 },
      wrapperCol: { span: 18 },
      hideRequiredMark: false,
      customStyle: ''
    }
  };
  public relationshipsData: any = {};
  public dataFormContent = [];
  public dataContent = [];
  public companyCustomerId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.companyCustomerId) {
      this.companyCustomerId = this.$route.params.companyCustomerId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.companyCustomer, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.companyCustomer.id) {
      this.companyCustomerService()
        .update(this.companyCustomer)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.companyCompanyCustomer.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.companyCustomerService()
        .create(this.companyCustomer)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.companyCompanyCustomer.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveCompanyCustomer(companyCustomerId): void {
    this.companyCustomerService()
      .find(companyCustomerId)
      .subscribe(res => {
        this.companyCustomer = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.companyCustomerService().tree()]).subscribe(
      ([companyCustomersRes]) => {
        this.relationshipsData['companyCustomers'] = companyCustomersRes.data;
        this.getData();
      },
      error => {
        this.loading = false;
        this.$message.error({
          content: `数据获取失败`,
          onClose: () => {
            this.getData();
          }
        });
      }
    );
  }
  public getData() {
    if (this.companyCustomerId) {
      this.retrieveCompanyCustomer(this.companyCustomerId);
    } else {
      this.getFormData();
    }
  }
  public getFormData(formDataId?: number) {
    if (formDataId) {
      this.commonTableService()
        .find(formDataId)
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.companyCustomer = getDataByFormField(this.formJsonData.list, this.companyCustomer);
          this.$nextTick(() => {
            this.updateForm.setData(this.companyCustomer); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('CompanyCustomer')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.companyCustomer = getDataByFormField(this.formJsonData.list, this.companyCustomer);
          this.$nextTick(() => {
            this.updateForm.setData(this.companyCustomer); // loadsh的pick方法
          });
        });
    }
  }
}
