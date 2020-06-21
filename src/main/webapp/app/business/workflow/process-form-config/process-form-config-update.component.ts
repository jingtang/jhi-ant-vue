import { Component, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';
import AlertService from '@/shared/alert/alert.service';
import { IProcessFormConfig, ProcessFormConfig } from '@/shared/model/workflow/process-form-config.model';
import ProcessFormConfigService from './process-form-config.service';
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
import { quillEditor } from 'vue-quill-editor';
import { UPLOAD_IMAGE_URL } from '@/constants';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  processFormConfig: {
    processDefinitionKey: {},
    taskNodeId: {},
    taskNodeName: {},
    commonTableId: {},
    formData: {}
  }
};

@Component({
  validations,
  components: {
    'jhi-quill-editor': quillEditor
  }
})
export default class ProcessFormConfigUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('processFormConfigService') private processFormConfigService: () => ProcessFormConfigService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public processFormConfig: IProcessFormConfig = new ProcessFormConfig();
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
  public processFormConfigId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.processFormConfigId) {
      this.processFormConfigId = this.$route.params.processFormConfigId;
    }
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.processFormConfig, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.processFormConfig.id) {
      this.processFormConfigService()
        .update(this.processFormConfig)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.workflowProcessFormConfig.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.processFormConfigService()
        .create(this.processFormConfig)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.workflowProcessFormConfig.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveProcessFormConfig(processFormConfigId): void {
    this.processFormConfigService()
      .find(processFormConfigId)
      .subscribe(res => {
        this.processFormConfig = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
  public getData() {
    if (this.processFormConfigId) {
      this.retrieveProcessFormConfig(this.processFormConfigId);
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
          this.processFormConfig = getDataByFormField(this.formJsonData.list, this.processFormConfig);
          this.$nextTick(() => {
            this.updateForm.setData(this.processFormConfig); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('ProcessFormConfig')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.processFormConfig = getDataByFormField(this.formJsonData.list, this.processFormConfig);
          this.$nextTick(() => {
            this.updateForm.setData(this.processFormConfig); // loadsh的pick方法
          });
        });
    }
  }
}
