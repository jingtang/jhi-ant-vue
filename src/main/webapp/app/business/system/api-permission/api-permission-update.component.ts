import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import ViewPermissionService from '../../system//view-permission/view-permission.service';
import { IViewPermission } from '@/shared/model/system/view-permission.model';

import AlertService from '@/shared/alert/alert.service';
import { IApiPermission, ApiPermission } from '@/shared/model/system/api-permission.model';
import ApiPermissionService from './api-permission.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  apiPermission: {
    name: {},
    code: {},
    description: {},
    type: {},
    method: {},
    url: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class ApiPermissionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('apiPermissionService') private apiPermissionService: () => ApiPermissionService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public apiPermission: IApiPermission = new ApiPermission();

  public apiPermissions: IApiPermission[] = [];

  @Inject('viewPermissionService') private viewPermissionService: () => ViewPermissionService;

  public viewPermissions: IViewPermission[] = [];
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
  public apiPermissionId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.apiPermissionId) {
      this.apiPermissionId = this.$route.params.apiPermissionId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.apiPermission, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.apiPermission.id) {
      this.apiPermissionService()
        .update(this.apiPermission)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.systemApiPermission.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.apiPermissionService()
        .create(this.apiPermission)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.systemApiPermission.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveApiPermission(apiPermissionId): void {
    this.apiPermissionService()
      .find(apiPermissionId)
      .subscribe(res => {
        this.apiPermission = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.apiPermissionService().tree(), this.viewPermissionService().tree()]).subscribe(
      ([apiPermissionsRes, viewPermissionsRes]) => {
        this.relationshipsData['apiPermissions'] = apiPermissionsRes.data;
        this.relationshipsData['viewPermissions'] = viewPermissionsRes.data;
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
    if (this.apiPermissionId) {
      this.retrieveApiPermission(this.apiPermissionId);
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
          this.apiPermission = getDataByFormField(this.formJsonData.list, this.apiPermission);
          this.$nextTick(() => {
            this.updateForm.setData(this.apiPermission); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('ApiPermission')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.apiPermission = getDataByFormField(this.formJsonData.list, this.apiPermission);
          this.$nextTick(() => {
            this.updateForm.setData(this.apiPermission); // loadsh的pick方法
          });
        });
    }
  }
}
