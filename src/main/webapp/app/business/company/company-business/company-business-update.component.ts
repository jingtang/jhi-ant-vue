import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import moment from 'moment';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import BusinessTypeService from '../../company//business-type/business-type.service';
import { IBusinessType } from '@/shared/model/company/business-type.model';

import CompanyCustomerService from '../../company//company-customer/company-customer.service';
import { ICompanyCustomer } from '@/shared/model/company/company-customer.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompanyBusiness, CompanyBusiness } from '@/shared/model/company/company-business.model';
import CompanyBusinessService from './company-business.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  companyBusiness: {
    status: {},
    expirationTime: {},
    startTime: {},
    operateUserId: {},
    groupId: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class CompanyBusinessUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('companyBusinessService') private companyBusinessService: () => CompanyBusinessService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public companyBusiness: ICompanyBusiness = new CompanyBusiness();

  @Inject('businessTypeService') private businessTypeService: () => BusinessTypeService;

  public businessTypes: IBusinessType[] = [];

  @Inject('companyCustomerService') private companyCustomerService: () => CompanyCustomerService;

  public companyCustomers: ICompanyCustomer[] = [];
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
  public companyBusinessId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.companyBusinessId) {
      this.companyBusinessId = this.$route.params.companyBusinessId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.companyBusiness, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.companyBusiness.id) {
      this.companyBusinessService()
        .update(this.companyBusiness)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.companyCompanyBusiness.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.companyBusinessService()
        .create(this.companyBusiness)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.companyCompanyBusiness.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveCompanyBusiness(companyBusinessId): void {
    this.companyBusinessService()
      .find(companyBusinessId)
      .subscribe(res => {
        this.companyBusiness = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.businessTypeService().retrieve(), this.companyCustomerService().tree()]).subscribe(
      ([businessTypesRes, companyCustomersRes]) => {
        this.relationshipsData['businessTypes'] = businessTypesRes.data;
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
    if (this.companyBusinessId) {
      this.retrieveCompanyBusiness(this.companyBusinessId);
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
          this.companyBusiness = getDataByFormField(this.formJsonData.list, this.companyBusiness);
          this.$nextTick(() => {
            this.updateForm.setData(this.companyBusiness); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('CompanyBusiness')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.companyBusiness = getDataByFormField(this.formJsonData.list, this.companyBusiness);
          this.$nextTick(() => {
            this.updateForm.setData(this.companyBusiness); // loadsh的pick方法
          });
        });
    }
  }
}
