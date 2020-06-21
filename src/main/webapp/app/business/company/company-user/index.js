import CompanyUserComponent from './company-user.vue';
import CompanyUserCompactComponent from './company-user-compact.vue';
import CompanyUserUpdate from './company-user-update.vue';

const CompanyUser = {
  install: function(Vue) {
    Vue.component('jhi-company-user', CompanyUserComponent);
    Vue.component('jhi-company-user-compact', CompanyUserCompactComponent);
    Vue.component('jhi-company-user-update', CompanyUserUpdate);
  }
};

export default CompanyUser;
