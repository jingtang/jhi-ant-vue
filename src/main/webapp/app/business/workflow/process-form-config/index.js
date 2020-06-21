import ProcessFormConfigComponent from './process-form-config.vue';
import ProcessFormConfigCompactComponent from './process-form-config-compact.vue';
import ProcessFormConfigUpdate from './process-form-config-update.vue';

const ProcessFormConfig = {
  install: function(Vue) {
    Vue.component('jhi-process-form-config', ProcessFormConfigComponent);
    Vue.component('jhi-process-form-config-compact', ProcessFormConfigCompactComponent);
    Vue.component('jhi-process-form-config-update', ProcessFormConfigUpdate);
  }
};

export default ProcessFormConfig;
