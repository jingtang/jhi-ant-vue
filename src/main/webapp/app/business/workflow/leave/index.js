import LeaveComponent from './leave.vue';
import LeaveCompactComponent from './leave-compact.vue';
import LeaveUpdate from './leave-update.vue';

const Leave = {
  install: function(Vue) {
    Vue.component('jhi-leave', LeaveComponent);
    Vue.component('jhi-leave-compact', LeaveCompactComponent);
    Vue.component('jhi-leave-update', LeaveUpdate);
  }
};

export default Leave;
