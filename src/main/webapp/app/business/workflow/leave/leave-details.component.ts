import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILeave } from '@/shared/model/workflow/leave.model';
import LeaveService from './leave.service';

@Component
export default class LeaveDetails extends Vue {
  @Inject('leaveService') private leaveService: () => LeaveService;
  public leave: ILeave = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.leaveId) {
        vm.retrieveLeave(to.params.leaveId);
      }
    });
  }

  public retrieveLeave(leaveId) {
    this.leaveService()
      .find(leaveId)
      .subscribe(res => {
        this.leave = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
