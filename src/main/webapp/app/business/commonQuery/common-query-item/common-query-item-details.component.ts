import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICommonQueryItem } from '@/shared/model/commonQuery/common-query-item.model';
import CommonQueryItemService from './common-query-item.service';

@Component
export default class CommonQueryItemDetails extends Vue {
  @Inject('commonQueryItemService') private commonQueryItemService: () => CommonQueryItemService;
  public commonQueryItem: ICommonQueryItem = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.commonQueryItemId) {
        vm.retrieveCommonQueryItem(to.params.commonQueryItemId);
      }
    });
  }

  public retrieveCommonQueryItem(commonQueryItemId) {
    this.commonQueryItemService()
      .find(commonQueryItemId)
      .subscribe(res => {
        this.commonQueryItem = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
