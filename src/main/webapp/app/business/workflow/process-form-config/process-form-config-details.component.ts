import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IProcessFormConfig } from '@/shared/model/workflow/process-form-config.model';
import ProcessFormConfigService from './process-form-config.service';

@Component
export default class ProcessFormConfigDetails extends mixins(JhiDataUtils) {
  @Inject('processFormConfigService') private processFormConfigService: () => ProcessFormConfigService;
  public processFormConfig: IProcessFormConfig = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processFormConfigId) {
        vm.retrieveProcessFormConfig(to.params.processFormConfigId);
      }
    });
  }

  public retrieveProcessFormConfig(processFormConfigId) {
    this.processFormConfigService()
      .find(processFormConfigId)
      .subscribe(res => {
        this.processFormConfig = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
