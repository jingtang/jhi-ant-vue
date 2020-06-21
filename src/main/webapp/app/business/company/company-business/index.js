import CompanyBusinessComponent from './company-business.vue';
import CompanyBusinessCompactComponent from './company-business-compact.vue';
import CompanyBusinessUpdate from './company-business-update.vue';

const CompanyBusiness = {
  install: function(Vue) {
    Vue.component('jhi-company-business', CompanyBusinessComponent);
    Vue.component('jhi-company-business-compact', CompanyBusinessCompactComponent);
    Vue.component('jhi-company-business-update', CompanyBusinessUpdate);
  }
};

export default CompanyBusiness;
