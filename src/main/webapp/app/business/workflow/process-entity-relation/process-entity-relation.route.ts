import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const processEntityRelationRoute: RouteConfig = {
  path: 'process-entity-relation',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '流程与实体关联' },
  children: [
    {
      path: '',
      name: 'process-entity-relation',
      component: () => import('./process-entity-relation.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'process-entity-relation-new',
      component: () => import('./process-entity-relation-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':processEntityRelationId/edit',
      name: 'process-entity-relation-edit',
      component: () => import('./process-entity-relation-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':processEntityRelationId/detail',
      name: 'process-entity-relation-detail',
      component: () => import('./process-entity-relation-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
