import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompanyBusiness } from '@/shared/model/company/company-business.model';
import CompanyBusinessService from './company-business.service';

@Component
export default class CompanyBusinessDetails extends Vue {
  @Inject('companyBusinessService') private companyBusinessService: () => CompanyBusinessService;
  public companyBusiness: ICompanyBusiness = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyBusinessId) {
        vm.retrieveCompanyBusiness(to.params.companyBusinessId);
      }
    });
  }

  public retrieveCompanyBusiness(companyBusinessId) {
    this.companyBusinessService()
      .find(companyBusinessId)
      .subscribe(res => {
        this.companyBusiness = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
