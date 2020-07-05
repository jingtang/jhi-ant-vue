import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICommonQuery } from '@/shared/model/commonQuery/common-query.model';
import CommonQueryService from './common-query.service';

@Component
export default class CommonQueryDetails extends Vue {
  @Inject('commonQueryService') private commonQueryService: () => CommonQueryService;
  public commonQuery: ICommonQuery = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.commonQueryId) {
        vm.retrieveCommonQuery(to.params.commonQueryId);
      }
    });
  }

  public retrieveCommonQuery(commonQueryId) {
    this.commonQueryService()
      .find(commonQueryId)
      .subscribe(res => {
        this.commonQuery = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
