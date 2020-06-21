import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import AlertService from '@/shared/alert/alert.service';
import { IDataDictionary, DataDictionary } from '@/shared/model/settings/data-dictionary.model';
import DataDictionaryService from './data-dictionary.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  dataDictionary: {
    name: {},
    code: {},
    description: {},
    fontColor: {},
    backgroundColor: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class DataDictionaryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('dataDictionaryService') private dataDictionaryService: () => DataDictionaryService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public dataDictionary: IDataDictionary = new DataDictionary();

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
  public dataContent = [];
  public dataDictionaryId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.dataDictionaryId) {
      this.dataDictionaryId = this.$route.params.dataDictionaryId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.dataDictionary, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.dataDictionary.id) {
      this.dataDictionaryService()
        .update(this.dataDictionary)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.settingsDataDictionary.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.dataDictionaryService()
        .create(this.dataDictionary)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.settingsDataDictionary.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveDataDictionary(dataDictionaryId): void {
    this.dataDictionaryService()
      .find(dataDictionaryId)
      .subscribe(res => {
        this.dataDictionary = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.dataDictionaryService().tree()]).subscribe(
      ([dataDictionariesRes]) => {
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
    if (this.dataDictionaryId) {
      this.retrieveDataDictionary(this.dataDictionaryId);
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
          this.dataDictionary = getDataByFormField(this.formJsonData.list, this.dataDictionary);
          this.$nextTick(() => {
            this.updateForm.setData(this.dataDictionary); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('DataDictionary')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.dataDictionary = getDataByFormField(this.formJsonData.list, this.dataDictionary);
          this.$nextTick(() => {
            this.updateForm.setData(this.dataDictionary); // loadsh的pick方法
          });
        });
    }
  }
}
