import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';
import AlertService from '@/shared/alert/alert.service';
import { IProcessEntityRelation, ProcessEntityRelation } from '@/shared/model/workflow/process-entity-relation.model';
import ProcessEntityRelationService from './process-entity-relation.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  processEntityRelation: {
    entityType: {},
    entityId: {},
    processInstanceId: {},
    status: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class ProcessEntityRelationUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('processEntityRelationService') private processEntityRelationService: () => ProcessEntityRelationService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public processEntityRelation: IProcessEntityRelation = new ProcessEntityRelation();
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
  public processEntityRelationId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.processEntityRelationId) {
      this.processEntityRelationId = this.$route.params.processEntityRelationId;
    }
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.processEntityRelation, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.processEntityRelation.id) {
      this.processEntityRelationService()
        .update(this.processEntityRelation)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.workflowProcessEntityRelation.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.processEntityRelationService()
        .create(this.processEntityRelation)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.workflowProcessEntityRelation.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveProcessEntityRelation(processEntityRelationId): void {
    this.processEntityRelationService()
      .find(processEntityRelationId)
      .subscribe(res => {
        this.processEntityRelation = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
  public getData() {
    if (this.processEntityRelationId) {
      this.retrieveProcessEntityRelation(this.processEntityRelationId);
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
          this.processEntityRelation = getDataByFormField(this.formJsonData.list, this.processEntityRelation);
          this.$nextTick(() => {
            this.updateForm.setData(this.processEntityRelation); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('ProcessEntityRelation')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.processEntityRelation = getDataByFormField(this.formJsonData.list, this.processEntityRelation);
          this.$nextTick(() => {
            this.updateForm.setData(this.processEntityRelation); // loadsh的pick方法
          });
        });
    }
  }
}
