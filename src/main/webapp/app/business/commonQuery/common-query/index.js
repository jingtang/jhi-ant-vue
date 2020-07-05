import CommonQueryComponent from './common-query.vue';
import CommonQueryCompactComponent from './common-query-compact.vue';
import CommonQueryUpdate from './common-query-update.vue';

const CommonQuery = {
  install: function(Vue) {
    Vue.component('jhi-common-query', CommonQueryComponent);
    Vue.component('jhi-common-query-compact', CommonQueryCompactComponent);
    Vue.component('jhi-common-query-update', CommonQueryUpdate);
  }
};

export default CommonQuery;
