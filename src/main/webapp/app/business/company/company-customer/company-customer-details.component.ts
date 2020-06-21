import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompanyCustomer } from '@/shared/model/company/company-customer.model';
import CompanyCustomerService from './company-customer.service';

@Component
export default class CompanyCustomerDetails extends Vue {
  @Inject('companyCustomerService') private companyCustomerService: () => CompanyCustomerService;
  public companyCustomer: ICompanyCustomer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.companyCustomerId) {
        vm.retrieveCompanyCustomer(to.params.companyCustomerId);
      }
    });
  }

  public retrieveCompanyCustomer(companyCustomerId) {
    this.companyCustomerService()
      .find(companyCustomerId)
      .subscribe(res => {
        this.companyCustomer = res.data;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
