import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import CommonTableService from '../../modelConfig//common-table/common-table.service';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';

import DataDictionaryService from '../../settings//data-dictionary/data-dictionary.service';
import { IDataDictionary } from '@/shared/model/settings/data-dictionary.model';

import AlertService from '@/shared/alert/alert.service';
import { ICommonTableRelationship, CommonTableRelationship } from '@/shared/model/modelConfig/common-table-relationship.model';
import CommonTableRelationshipService from './common-table-relationship.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  commonTableRelationship: {
    name: {
      required,
      maxLength: maxLength(100)
    },
    relationshipType: {
      required
    },
    sourceType: {},
    otherEntityField: {
      maxLength: maxLength(100)
    },
    otherEntityName: {
      required,
      maxLength: maxLength(100)
    },
    relationshipName: {
      required,
      maxLength: maxLength(100)
    },
    otherEntityRelationshipName: {
      maxLength: maxLength(100)
    },
    columnWidth: {},
    order: {},
    fixed: {},
    editInList: {},
    enableFilter: {},
    hideInList: {},
    hideInForm: {},
    fontColor: {
      maxLength: maxLength(80)
    },
    backgroundColor: {
      maxLength: maxLength(80)
    },
    help: {
      maxLength: maxLength(200)
    },
    ownerSide: {},
    dataName: {
      required,
      maxLength: maxLength(100)
    },
    webComponentType: {
      maxLength: maxLength(100)
    },
    otherEntityIsTree: {},
    showInFilterTree: {},
    dataDictionaryCode: {
      maxLength: maxLength(100)
    },
    clientReadOnly: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class CommonTableRelationshipUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('commonTableRelationshipService') private commonTableRelationshipService: () => CommonTableRelationshipService;
  public commonTableRelationship: ICommonTableRelationship = new CommonTableRelationship();

  @Inject('commonTableService') private commonTableService: () => CommonTableService;

  public commonTables: ICommonTable[] = [];

  @Inject('dataDictionaryService') private dataDictionaryService: () => DataDictionaryService;

  public dataDictionaries: IDataDictionary[] = [];
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
  @Prop(Number) commonTableRelationshipId;
  @Prop(Number) commonTableId;
  @Prop(Boolean) showInModal;
  public dataContent = [];

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.commonTableRelationshipId) {
      this.commonTableRelationshipId = this.$route.params.commonTableRelationshipId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.commonTableRelationship, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.commonTableRelationship.id) {
      this.commonTableRelationshipService()
        .update(this.commonTableRelationship)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.modelConfigCommonTableRelationship.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          if (this.showInModal) {
            this.$emit('cancel', true);
          } else {
            this.$router.go(-1);
          }
        });
    } else {
      this.commonTableRelationshipService()
        .create(this.commonTableRelationship)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.modelConfigCommonTableRelationship.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          if (this.showInModal) {
            this.$emit('cancel', true);
          } else {
            this.$router.go(-1);
          }
        });
    }
  }

  @Watch('commonTableRelationshipId', { immediate: true })
  public retrieveCommonTableRelationship(commonTableRelationshipId): void {
    this.commonTableRelationshipService()
      .find(commonTableRelationshipId)
      .subscribe(res => {
        this.commonTableRelationship = res.data;
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
    forkJoin([this.commonTableService().retrieve(), this.dataDictionaryService().tree()]).subscribe(
      ([commonTablesRes, dataDictionariesRes]) => {
        this.relationshipsData['commonTables'] = commonTablesRes.data;
        this.relationshipsData['dataDictionaries'] = dataDictionariesRes.data;
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
    if (this.commonTableRelationshipId) {
      this.retrieveCommonTableRelationship(this.commonTableRelationshipId);
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
          this.commonTableRelationship = getDataByFormField(this.formJsonData.list, this.commonTableRelationship);
          this.$nextTick(() => {
            this.updateForm.setData(this.commonTableRelationship); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('CommonTableRelationship')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.commonTableRelationship = getDataByFormField(this.formJsonData.list, this.commonTableRelationship);
          this.$nextTick(() => {
            this.updateForm.setData(this.commonTableRelationship); // loadsh的pick方法
          });
        });
    }
  }
}
