import { Component, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import CommonTableService from '../../modelConfig//common-table/common-table.service';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';

import UserService from '@/shared/service/user.service';

import AlertService from '@/shared/alert/alert.service';
import { IProcessTableConfig, ProcessTableConfig } from '@/shared/model/workflow/process-table-config.model';
import ProcessTableConfigService from './process-table-config.service';
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
import { quillEditor } from 'vue-quill-editor';
import { UPLOAD_IMAGE_URL } from '@/constants';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  processTableConfig: {
    processDefinitionId: {},
    processDefinitionKey: {},
    processDefinitionName: {},
    description: {},
    processBpmnData: {},
    deploied: {}
  }
};

@Component({
  validations,
  components: {
    'jhi-quill-editor': quillEditor
  }
})
export default class ProcessTableConfigUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('processTableConfigService') private processTableConfigService: () => ProcessTableConfigService;
  public processTableConfig: IProcessTableConfig = new ProcessTableConfig();

  @Inject('commonTableService') private commonTableService: () => CommonTableService;

  public commonTables: ICommonTable[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
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
  public processTableConfigId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.processTableConfigId) {
      this.processTableConfigId = this.$route.params.processTableConfigId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.processTableConfig, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.processTableConfig.id) {
      this.processTableConfigService()
        .update(this.processTableConfig)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.workflowProcessTableConfig.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.processTableConfigService()
        .create(this.processTableConfig)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.workflowProcessTableConfig.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveProcessTableConfig(processTableConfigId): void {
    this.processTableConfigService()
      .find(processTableConfigId)
      .subscribe(res => {
        this.processTableConfig = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.commonTableService().retrieve(), this.userService().retrieve()]).subscribe(
      ([commonTablesRes, usersRes]) => {
        this.relationshipsData['commonTables'] = commonTablesRes.data;
        this.relationshipsData['users'] = usersRes.data;
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
    if (this.processTableConfigId) {
      this.retrieveProcessTableConfig(this.processTableConfigId);
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
          this.processTableConfig = getDataByFormField(this.formJsonData.list, this.processTableConfig);
          this.$nextTick(() => {
            this.updateForm.setData(this.processTableConfig); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('ProcessTableConfig')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.processTableConfig = getDataByFormField(this.formJsonData.list, this.processTableConfig);
          this.$nextTick(() => {
            this.updateForm.setData(this.processTableConfig); // loadsh的pick方法
          });
        });
    }
  }
}
