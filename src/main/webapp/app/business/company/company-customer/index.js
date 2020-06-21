import CompanyCustomerComponent from './company-customer.vue';
import CompanyCustomerCompactComponent from './company-customer-compact.vue';
import CompanyCustomerUpdate from './company-customer-update.vue';

const CompanyCustomer = {
  install: function(Vue) {
    Vue.component('jhi-company-customer', CompanyCustomerComponent);
    Vue.component('jhi-company-customer-compact', CompanyCustomerCompactComponent);
    Vue.component('jhi-company-customer-update', CompanyCustomerUpdate);
  }
};

export default CompanyCustomer;
