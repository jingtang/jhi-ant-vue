import ProcessTableConfigComponent from './process-table-config.vue';
import ProcessTableConfigCompactComponent from './process-table-config-compact.vue';
import ProcessTableConfigUpdate from './process-table-config-update.vue';

const ProcessTableConfig = {
  install: function(Vue) {
    Vue.component('jhi-process-table-config', ProcessTableConfigComponent);
    Vue.component('jhi-process-table-config-compact', ProcessTableConfigCompactComponent);
    Vue.component('jhi-process-table-config-update', ProcessTableConfigUpdate);
  }
};

export default ProcessTableConfig;
