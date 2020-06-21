import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import UserService from '@/shared/service/user.service';

import CompanyCustomerService from '../../company//company-customer/company-customer.service';
import { ICompanyCustomer } from '@/shared/model/company/company-customer.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompanyUser, CompanyUser } from '@/shared/model/company/company-user.model';
import CompanyUserService from './company-user.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  companyUser: {}
};

@Component({
  validations,
  components: {}
})
export default class CompanyUserUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('companyUserService') private companyUserService: () => CompanyUserService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public companyUser: ICompanyUser = new CompanyUser();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

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
  public companyUserId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.companyUserId) {
      this.companyUserId = this.$route.params.companyUserId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.companyUser, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.companyUser.id) {
      this.companyUserService()
        .update(this.companyUser)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.companyCompanyUser.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.companyUserService()
        .create(this.companyUser)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.companyCompanyUser.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveCompanyUser(companyUserId): void {
    this.companyUserService()
      .find(companyUserId)
      .subscribe(res => {
        this.companyUser = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.userService().retrieve(), this.companyCustomerService().tree()]).subscribe(
      ([usersRes, companyCustomersRes]) => {
        this.relationshipsData['users'] = usersRes.data;
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
    if (this.companyUserId) {
      this.retrieveCompanyUser(this.companyUserId);
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
          this.companyUser = getDataByFormField(this.formJsonData.list, this.companyUser);
          this.$nextTick(() => {
            this.updateForm.setData(this.companyUser); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('CompanyUser')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.companyUser = getDataByFormField(this.formJsonData.list, this.companyUser);
          this.$nextTick(() => {
            this.updateForm.setData(this.companyUser); // loadsh的pick方法
          });
        });
    }
  }
}
