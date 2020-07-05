import CommonQueryItemComponent from './common-query-item.vue';
import CommonQueryItemCompactComponent from './common-query-item-compact.vue';
import CommonQueryItemUpdate from './common-query-item-update.vue';

const CommonQueryItem = {
  install: function(Vue) {
    Vue.component('jhi-common-query-item', CommonQueryItemComponent);
    Vue.component('jhi-common-query-item-compact', CommonQueryItemCompactComponent);
    Vue.component('jhi-common-query-item-update', CommonQueryItemUpdate);
  }
};

export default CommonQueryItem;
