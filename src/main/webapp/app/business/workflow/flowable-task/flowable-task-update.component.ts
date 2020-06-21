import { Component, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import CommonTableService from '../../modelConfig//common-table/common-table.service';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import UserService from '@/shared/service/user.service';
import AlertService from '@/shared/alert/alert.service';
import { IProcessTableConfig, ProcessTableConfig } from '@/shared/model/workflow/process-table-config.model';
import ProcessTableConfigService from '../process-table-config/process-table-config.service';
import FlowableTaskService from './flowable-task.service';
import TaskCommentService from '@/business/workflow/flowable-task/task-comment.service';
import { forkJoin } from 'rxjs';
import HistoryTaskInstanceComponent from '@/business/workflow/flowable-history/history-task-instance.vue';

@Component({
  components: {
    'jhi-history-task-instance': HistoryTaskInstanceComponent
  }
})
export default class FlowableTaskUpdateComponent extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('processTableConfigService') private processTableConfigService: () => ProcessTableConfigService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  @Inject('userService') private userService: () => UserService;
  @Inject('flowableTaskService') private flowableTaskService: () => FlowableTaskService;
  @Inject('taskCommentService') private taskCommentService: () => TaskCommentService;

  @Prop(String) updateFormTag;
  @Prop(Number) processEntityId;
  @Prop(String) formJsonData;
  @Prop(String) taskId;
  @Prop(String) processInstanceId;
  public comment: string = '';
  public methodsObject: any = {};
  public processTableConfig: IProcessTableConfig = new ProcessTableConfig();
  public commonTables: ICommonTable[] = [];
  public users: Array<any> = [];
  public isSaving = false;
  public loading = false;
  public relationshipsData: any = {};
  public dataFormContent = [];
  public dataContent = [];
  public processTableConfigId = null;

  get updateFormTagName() {
    console.log(this.updateFormTag ? this.updateFormTag : '');
    return this.updateFormTag ? this.updateFormTag : '';
  }

  public created() {
    this.methodsObject.taskOperate = this.taskOperate;
  }

  public save(): void {
    this.isSaving = true;
    if (this.processTableConfig.id) {
      this.processTableConfigService()
        .update(this.processTableConfig)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('testnew12App.workflowProcessTableConfig.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.processTableConfigService()
        .create(this.processTableConfig)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('testnew12App.workflowProcessTableConfig.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public taskOperate(action: string) {
    const taskPayload: any = {};
    switch (action) {
      case 'approve':
        // todo 审批
        taskPayload.action = 'complete';
        if (this.comment) {
          const commentMessage = { message: this.comment, saveProcessInstanceId: true };
          forkJoin([
            this.flowableTaskService().operateTask(this.taskId, taskPayload),
            this.taskCommentService().create(this.taskId, commentMessage)
          ]).subscribe(
            ([taskRes, taskCommentRes]) => {
              if (taskRes.status === 200 && taskCommentRes.data) {
                this.$message.success('操作完成。');
                this.$emit('closeModal');
              }
            },
            error => {
              this.$message.error('操作失败');
            }
          );
        }
        break;
      case 'reject':
        taskPayload.action = 'resolve';
        if (this.comment) {
          const commentMessage = { message: this.comment, saveProcessInstanceId: true };
          forkJoin([
            this.flowableTaskService().operateTask(this.taskId, taskPayload),
            this.taskCommentService().create(this.taskId, commentMessage)
          ]).subscribe(([taskRes, taskCommentRes]) => {
            if (taskRes.data && taskCommentRes.data) {
              this.$message.success('操作完成。');
            }
          });
        }
        break;
      case 'delegate':
        taskPayload.action = 'delegate';
        taskPayload.assignee = '';
        this.flowableTaskService().operateTask(this.taskId, taskPayload);
        // todo 委派
        break;
      case 'signon':
        // todo 签收
        break;
      case 'notify':
        // todo 知会
        break;
      case 'passaround':
        // todo 传阅
        break;
      case 'read':
        // todo 已阅
        break;
      case 'turnto':
        // todo 转办
        break;
      case 'submit':
        // todo 提交
        break;
      case 'accredit':
        // todo 授权
        break;
      case 'cooperate':
        // todo 协同
        break;
      case 'review':
        // todo 评审
        break;
    }
  }
}
