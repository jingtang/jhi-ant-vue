import { mixins } from 'vue-class-component';
import { Component, Inject, Prop, Ref } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICommonTable, CommonTable } from '@/shared/model/modelConfig/common-table.model';
import AlertMixin from '@/shared/alert/alert.mixin';
import JhiDataUtils from '@/shared/data/data-utils.service';
import DeploymentService from '../deployment/deployment.service';
import UserService from '@/shared/service/user.service';
import BusinessTypeService from '@/business/company/business-type/business-type.service';
import VueBpmn from '@/components/VueBpmn/index.vue';
import ProcessDefinitionService from '@/business/workflow/process-definition/process-definition.service';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import ProcessTableConfigService from '@/business/workflow/process-table-config/process-table-config.service';
import { IProcessTableConfig } from '@/shared/model/workflow/process-table-config.model';
import { Mutation } from 'vuex-class';

@Component({
  components: {
    'vue-bpmn': VueBpmn
  }
})
export default class WorkflowDesignComponent extends mixins(JhiDataUtils, Vue2Filters.mixin, AlertMixin) {
  @Inject('deploymentService') private deploymentService: () => DeploymentService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  @Inject('processDefinitionService') private processDefinitionService: () => ProcessDefinitionService;
  @Inject('processTableConfigService') private processTableConfigService: () => ProcessTableConfigService;
  @Inject('userService') private userService: () => UserService;
  @Inject('businessTypeService') private businessTypeService: () => BusinessTypeService;

  public processDefinitionKey = null;
  public processDefinitionName = null;
  public commonTables: ICommonTable[] = [{ id: 1, name: '用户表' }];
  public commonTableId = null;
  public processTableConfig: IProcessTableConfig = null;
  public processDefinitionBpmn = null;
  @Mutation('initProcessData') initProcessData;

  public deployment(formData: FormData) {
    this.deploymentService()
      .deployProcess(formData)
      .subscribe(
        res => {
          this.$message.success('部署完成');
          this.$router.push({ name: 'deployment' });
        },
        error => {
          this.$message.error(error.message);
        }
      );
  }

  public saveToServer(bpmnData: any) {
    const partProcessTableConfig = {
      id: this.processTableConfig.id,
      processBpmnData: bpmnData
    };
    this.processTableConfigService()
      .updateBySpecifiedField(partProcessTableConfig, 'processBpmnData')
      .subscribe(
        res => {
          this.$message.success('更新完成。');
        },
        error => {
          this.$message.error('更新失败！');
          console.log(error.message);
        }
      );
  }

  public created(): void {
    this.commonTableService()
      .retrieve()
      .subscribe(res => {
        this.commonTables = res.data;
        if (this.$route.params.processDefinitionKey) {
          this.processDefinitionKey = this.$route.params.processDefinitionKey;
          const paginationQuery = {
            'processDefinitionKey.equals': this.processDefinitionKey
          };
          this.processTableConfigService()
            .retrieve(paginationQuery)
            .subscribe(
              res => {
                if (res.data && res.data.length > 0) {
                  const addCommonTableId = res.data[0].commonTableId;
                  this.commonTableService()
                    .retrieve({ 'id.equals': addCommonTableId })
                    .subscribe(res => {
                      this.commonTables = this.commonTables.concat(res.data);
                    });
                  this.$nextTick(() => {
                    this.processDefinitionBpmn = res.data[0].processBpmnData;
                    this.commonTableId = res.data[0].commonTableId;
                    this.processDefinitionName = res.data[0].processDefinitionName;
                    this.initProcessData({
                      commonTableId: this.commonTableId,
                      processDefinitionKey: this.processDefinitionKey,
                      processDefinitionName: this.processDefinitionName
                    });
                    this.processTableConfig = res.data[0];
                  });
                }
              },
              error => {
                this.$message.error('未获得流程定义资源数据！');
                console.log(error.message);
                this.processDefinitionKey = null;
              }
            );
        }
      });
  }
}
