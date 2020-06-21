import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompanyUser } from '@/shared/model/company/company-user.model';
import CompanyUserService from './company-user.service';

@Component
export default class CompanyUserDetails extends Vue {
  @Inject('companyUserService') private companyUserService: () => CompanyUserService;
  public companyUser: ICompanyUser = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyUserId) {
        vm.retrieveCompanyUser(to.params.companyUserId);
      }
    });
  }

  public retrieveCompanyUser(companyUserId) {
    this.companyUserService()
      .find(companyUserId)
      .subscribe(res => {
        this.companyUser = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
