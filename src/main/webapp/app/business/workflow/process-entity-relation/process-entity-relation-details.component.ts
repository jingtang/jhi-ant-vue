import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProcessEntityRelation } from '@/shared/model/workflow/process-entity-relation.model';
import ProcessEntityRelationService from './process-entity-relation.service';

@Component
export default class ProcessEntityRelationDetails extends Vue {
  @Inject('processEntityRelationService') private processEntityRelationService: () => ProcessEntityRelationService;
  public processEntityRelation: IProcessEntityRelation = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processEntityRelationId) {
        vm.retrieveProcessEntityRelation(to.params.processEntityRelationId);
      }
    });
  }

  public retrieveProcessEntityRelation(processEntityRelationId) {
    this.processEntityRelationService()
      .find(processEntityRelationId)
      .subscribe(res => {
        this.processEntityRelation = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
