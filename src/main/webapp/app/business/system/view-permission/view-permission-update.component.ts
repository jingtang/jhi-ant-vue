import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import ApiPermissionService from '../../system//api-permission/api-permission.service';
import { IApiPermission } from '@/shared/model/system/api-permission.model';

import AuthorityService from '../../system//authority/authority.service';
import { IAuthority } from '@/shared/model/system/authority.model';

import AlertService from '@/shared/alert/alert.service';
import { IViewPermission, ViewPermission } from '@/shared/model/system/view-permission.model';
import ViewPermissionService from './view-permission.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  viewPermission: {
    text: {},
    i18n: {},
    group: {},
    link: {},
    externalLink: {},
    target: {},
    icon: {},
    disabled: {},
    hide: {},
    hideInBreadcrumb: {},
    shortcut: {},
    shortcutRoot: {},
    reuse: {},
    code: {},
    description: {},
    type: {},
    order: {},
    apiPermissionCodes: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class ViewPermissionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('viewPermissionService') private viewPermissionService: () => ViewPermissionService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public viewPermission: IViewPermission = new ViewPermission();

  public viewPermissions: IViewPermission[] = [];

  @Inject('apiPermissionService') private apiPermissionService: () => ApiPermissionService;

  public apiPermissions: IApiPermission[] = [];

  @Inject('authorityService') private authorityService: () => AuthorityService;

  public authorities: IAuthority[] = [];
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
  public viewPermissionId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.viewPermissionId) {
      this.viewPermissionId = this.$route.params.viewPermissionId;
    }
    this.initRelationships();
    this.viewPermission.apiPermissions = [];
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.viewPermission, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.viewPermission.id) {
      this.viewPermissionService()
        .update(this.viewPermission)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.systemViewPermission.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.viewPermissionService()
        .create(this.viewPermission)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.systemViewPermission.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveViewPermission(viewPermissionId): void {
    this.viewPermissionService()
      .find(viewPermissionId)
      .subscribe(res => {
        this.viewPermission = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.apiPermissionService().tree(), this.viewPermissionService().tree(), this.authorityService().tree()]).subscribe(
      ([apiPermissionsRes, viewPermissionsRes, authoritiesRes]) => {
        this.relationshipsData['apiPermissions'] = apiPermissionsRes.data;
        this.relationshipsData['viewPermissions'] = viewPermissionsRes.data;
        this.relationshipsData['authorities'] = authoritiesRes.data;
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
    if (this.viewPermissionId) {
      this.retrieveViewPermission(this.viewPermissionId);
    } else {
      this.getFormData();
    }
  }
  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
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
          this.viewPermission = getDataByFormField(this.formJsonData.list, this.viewPermission);
          this.$nextTick(() => {
            this.updateForm.setData(this.viewPermission); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('ViewPermission')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.viewPermission = getDataByFormField(this.formJsonData.list, this.viewPermission);
          this.$nextTick(() => {
            this.updateForm.setData(this.viewPermission); // loadsh的pick方法
          });
        });
    }
  }
}
