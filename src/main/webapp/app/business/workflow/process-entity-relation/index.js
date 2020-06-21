import ProcessEntityRelationComponent from './process-entity-relation.vue';
import ProcessEntityRelationCompactComponent from './process-entity-relation-compact.vue';
import ProcessEntityRelationUpdate from './process-entity-relation-update.vue';

const ProcessEntityRelation = {
  install: function(Vue) {
    Vue.component('jhi-process-entity-relation', ProcessEntityRelationComponent);
    Vue.component('jhi-process-entity-relation-compact', ProcessEntityRelationCompactComponent);
    Vue.component('jhi-process-entity-relation-update', ProcessEntityRelationUpdate);
  }
};

export default ProcessEntityRelation;
