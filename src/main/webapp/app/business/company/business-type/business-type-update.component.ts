import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';
import AlertService from '@/shared/alert/alert.service';
import { IBusinessType, BusinessType } from '@/shared/model/company/business-type.model';
import BusinessTypeService from './business-type.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  businessType: {
    name: {},
    code: {},
    description: {},
    icon: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class BusinessTypeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('businessTypeService') private businessTypeService: () => BusinessTypeService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public businessType: IBusinessType = new BusinessType();
  public isSaving = false;
  public loading = false;
  @Ref('updateForm') readonly updateForm: any;
  @Prop(Number) updateEntityId;
  @Prop(Boolean) showInModal;
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
  public businessTypeId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.businessTypeId) {
      this.businessTypeId = this.$route.params.businessTypeId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.businessType, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.businessType.id) {
      this.businessTypeService()
        .update(this.businessType)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.companyBusinessType.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          if (this.showInModal) {
            this.$emit('cancel', true);
          } else {
            this.$router.go(-1);
          }
        });
    } else {
      this.businessTypeService()
        .create(this.businessType)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.companyBusinessType.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          if (this.showInModal) {
            this.$emit('cancel', true);
          } else {
            this.$router.go(-1);
          }
        });
    }
  }

  public retrieveBusinessType(businessTypeId): void {
    this.businessTypeService()
      .find(businessTypeId)
      .subscribe(res => {
        this.businessType = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    if (this.showInModal) {
      this.$emit('cancel', true);
    } else {
      this.$router.go(-1);
    }
  }

  public initRelationships(): void {
    this.getData();
  }
  public getData() {
    if (this.businessTypeId || this.updateEntityId) {
      this.retrieveBusinessType(this.businessTypeId || this.updateEntityId);
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
          this.businessType = getDataByFormField(this.formJsonData.list, this.businessType);
          this.$nextTick(() => {
            this.updateForm.setData(this.businessType); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('BusinessType')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.businessType = getDataByFormField(this.formJsonData.list, this.businessType);
          this.$nextTick(() => {
            this.updateForm.setData(this.businessType); // loadsh的pick方法
          });
        });
    }
  }
}
