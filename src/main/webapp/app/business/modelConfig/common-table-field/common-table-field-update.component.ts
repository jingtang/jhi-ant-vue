import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import CommonTableService from '../../modelConfig//common-table/common-table.service';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';

import AlertService from '@/shared/alert/alert.service';
import { ICommonTableField, CommonTableField } from '@/shared/model/modelConfig/common-table-field.model';
import CommonTableFieldService from './common-table-field.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  commonTableField: {
    title: {
      required,
      maxLength: maxLength(100)
    },
    entityFieldName: {
      required,
      maxLength: maxLength(100)
    },
    type: {},
    tableColumnName: {
      required,
      maxLength: maxLength(100)
    },
    columnWidth: {
      numeric
    },
    order: {},
    editInList: {},
    hideInList: {},
    hideInForm: {},
    enableFilter: {},
    validateRules: {
      maxLength: maxLength(800)
    },
    showInFilterTree: {},
    fixed: {},
    sortable: {},
    treeIndicator: {},
    clientReadOnly: {},
    fieldValues: {
      maxLength: maxLength(2000)
    },
    notNull: {},
    system: {},
    help: {
      maxLength: maxLength(200)
    },
    fontColor: {
      maxLength: maxLength(80)
    },
    backgroundColor: {
      maxLength: maxLength(80)
    }
  }
};

@Component({
  validations,
  components: {}
})
export default class CommonTableFieldUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('commonTableFieldService') private commonTableFieldService: () => CommonTableFieldService;
  public commonTableField: ICommonTableField = new CommonTableField();

  @Inject('commonTableService') private commonTableService: () => CommonTableService;

  public commonTables: ICommonTable[] = [];
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
  @Prop(Number) commonTableFieldId;
  @Prop(Number) commonTableId;
  @Prop(Boolean) showInModal;
  public dataContent = [];

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.commonTableFieldId) {
      this.commonTableFieldId = this.$route.params.commonTableFieldId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.commonTableField, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.commonTableField.id) {
      this.commonTableFieldService()
        .update(this.commonTableField)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.modelConfigCommonTableField.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          if (this.showInModal) {
            this.$emit('cancel', true);
          } else {
            this.$router.go(-1);
          }
        });
    } else {
      this.commonTableFieldService()
        .create(this.commonTableField)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.modelConfigCommonTableField.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          if (this.showInModal) {
            this.$emit('cancel', true);
          } else {
            this.$router.go(-1);
          }
        });
    }
  }

  @Watch('commonTableFieldId', { immediate: true })
  public retrieveCommonTableField(commonTableFieldId): void {
    this.commonTableFieldService()
      .find(commonTableFieldId)
      .subscribe(res => {
        this.commonTableField = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    if (this.showInModal) {
      this.$emit('cancel', false);
    } else {
      this.$router.go(-1);
    }
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.commonTableService().retrieve()]).subscribe(
      ([commonTablesRes]) => {
        this.relationshipsData['commonTables'] = commonTablesRes.data;
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
    if (this.commonTableFieldId) {
      this.retrieveCommonTableField(this.commonTableFieldId);
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
          this.commonTableField = getDataByFormField(this.formJsonData.list, this.commonTableField);
          this.$nextTick(() => {
            this.updateForm.setData(this.commonTableField); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('CommonTableField')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.commonTableField = getDataByFormField(this.formJsonData.list, this.commonTableField);
          this.$nextTick(() => {
            this.updateForm.setData(this.commonTableField); // loadsh的pick方法
          });
        });
    }
  }
}
