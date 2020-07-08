import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import CommonQueryService from '../../commonQuery//common-query/common-query.service';
import { ICommonQuery } from '@/shared/model/commonQuery/common-query.model';

import AlertService from '@/shared/alert/alert.service';
import { ICommonQueryItem, CommonQueryItem } from '@/shared/model/commonQuery/common-query-item.model';
import CommonQueryItemService from './common-query-item.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  commonQueryItem: {
    prefix: {},
    fieldName: {},
    fieldType: {},
    operator: {},
    value: {},
    suffix: {},
    order: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class CommonQueryItemUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('commonQueryItemService') private commonQueryItemService: () => CommonQueryItemService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public commonQueryItem: ICommonQueryItem = new CommonQueryItem();

  @Inject('commonQueryService') private commonQueryService: () => CommonQueryService;

  public commonQueries: ICommonQuery[] = [];
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
  @Prop(Number) updateEntityId;
  @Prop(Boolean) showInModal;
  @Prop(Number) commonQueryItemId;
  @Prop(Number) queryId;

  public dataContent = [];

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.commonQueryItemId) {
      this.commonQueryItemId = this.$route.params.commonQueryItemId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.commonQueryItem, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.commonQueryItem.id) {
      this.commonQueryItemService()
        .update(this.commonQueryItem)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.commonQueryCommonQueryItem.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          if (this.showInModal) {
            this.$emit('cancel', true);
          } else {
            this.$router.go(-1);
          }
        });
    } else {
      this.commonQueryItemService()
        .create(this.commonQueryItem)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.commonQueryCommonQueryItem.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          if (this.showInModal) {
            this.$emit('cancel', true);
          } else {
            this.$router.go(-1);
          }
        });
    }
  }

  @Watch('commonQueryItemId', { immediate: true })
  public retrieveCommonQueryItem(commonQueryItemId): void {
    this.commonQueryItemService()
      .find(commonQueryItemId)
      .subscribe(res => {
        this.commonQueryItem = res.data;
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
    forkJoin([this.commonQueryService().retrieve()]).subscribe(
      ([commonQueriesRes]) => {
        this.relationshipsData['commonQueries'] = commonQueriesRes.data;
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
    if (this.commonQueryItemId || this.updateEntityId) {
      this.retrieveCommonQueryItem(this.commonQueryItemId || this.updateEntityId);
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
          this.commonQueryItem = getDataByFormField(this.formJsonData.list, this.commonQueryItem);
          this.$nextTick(() => {
            this.updateForm.setData(this.commonQueryItem); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('CommonQueryItem')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.commonQueryItem = getDataByFormField(this.formJsonData.list, this.commonQueryItem);
          this.$nextTick(() => {
            this.updateForm.setData(this.commonQueryItem); // loadsh的pick方法
          });
        });
    }
  }
}
