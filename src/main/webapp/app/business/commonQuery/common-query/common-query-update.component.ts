import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import moment from 'moment';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import CommonQueryItemService from '../../commonQuery//common-query-item/common-query-item.service';
import { ICommonQueryItem } from '@/shared/model/commonQuery/common-query-item.model';

import UserService from '@/shared/service/user.service';

import CommonTableService from '../../modelConfig//common-table/common-table.service';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';

import AlertService from '@/shared/alert/alert.service';
import { ICommonQuery, CommonQuery } from '@/shared/model/commonQuery/common-query.model';
import CommonQueryService from './common-query.service';
import CommonQueryItemComponent from '../../commonQuery//common-query-item/common-query-item.vue';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  commonQuery: {
    name: {
      required,
      maxLength: maxLength(50)
    },
    description: {},
    lastModifiedTime: {}
  }
};

@Component({
  validations,
  components: {
    'jhi-common-query-item': CommonQueryItemComponent
  }
})
export default class CommonQueryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('commonQueryService') private commonQueryService: () => CommonQueryService;
  public commonQuery: ICommonQuery = new CommonQuery();

  @Inject('commonQueryItemService') private commonQueryItemService: () => CommonQueryItemService;

  public commonQueryItems: ICommonQueryItem[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('commonTableService') private commonTableService: () => CommonTableService;

  public commonTables: ICommonTable[] = [];
  public isSaving = false;
  public loading = false;
  @Ref('updateForm') readonly updateForm: any;
  @Ref('itemListComponent') readonly itemListComponent: any;
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
  public dataContent = [];
  public commonQueryId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.commonQueryId) {
      this.commonQueryId = this.$route.params.commonQueryId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {}

  public save(): void {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.commonQuery, values);
        this.isSaving = true;
        this.itemListComponent.saveAll();
        if (this.commonQuery.id) {
          this.commonQueryService()
            .update(this.commonQuery)
            .subscribe(param => {
              this.isSaving = false;
              const message = this.$t('jhiAntVueApp.commonQueryCommonQuery.updated', { param: param.data.id }).toString();
              this.alertService().showAlert(message, 'info');
              this.$router.go(-1);
            });
        } else {
          this.commonQueryService()
            .create(this.commonQuery)
            .subscribe(param => {
              this.isSaving = false;
              const message = this.$t('jhiAntVueApp.commonQueryCommonQuery.created', { param: param.data.id }).toString();
              this.alertService().showAlert(message, 'success');
              this.$router.go(-1);
            });
        }
      })
      .catch(error => {
        this.$message.error('数据获取失败！');
        console.log(error);
      });
  }

  public retrieveCommonQuery(commonQueryId): void {
    this.commonQueryService()
      .find(commonQueryId)
      .subscribe(res => {
        this.commonQuery = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.userService().retrieve(), this.commonTableService().retrieve()]).subscribe(
      ([usersRes, commonTablesRes]) => {
        this.relationshipsData['users'] = usersRes.data;
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
    if (this.commonQueryId || this.updateEntityId) {
      this.retrieveCommonQuery(this.commonQueryId || this.updateEntityId);
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
          this.commonQuery = getDataByFormField(this.formJsonData.list, this.commonQuery);
          this.$nextTick(() => {
            this.updateForm.setData(this.commonQuery); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('CommonQuery')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.commonQuery = getDataByFormField(this.formJsonData.list, this.commonQuery);
          this.$nextTick(() => {
            this.updateForm.setData(this.commonQuery); // loadsh的pick方法
          });
        });
    }
  }
}
