import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IProcessTableConfig } from '@/shared/model/workflow/process-table-config.model';
import ProcessTableConfigService from './process-table-config.service';

@Component
export default class ProcessTableConfigDetails extends mixins(JhiDataUtils) {
  @Inject('processTableConfigService') private processTableConfigService: () => ProcessTableConfigService;
  public processTableConfig: IProcessTableConfig = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processTableConfigId) {
        vm.retrieveProcessTableConfig(to.params.processTableConfigId);
      }
    });
  }

  public retrieveProcessTableConfig(processTableConfigId) {
    this.processTableConfigService()
      .find(processTableConfigId)
      .subscribe(res => {
        this.processTableConfig = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
