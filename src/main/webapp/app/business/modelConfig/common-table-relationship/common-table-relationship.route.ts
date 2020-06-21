import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const commonTableRelationshipRoute: RouteConfig = {
  path: 'common-table-relationship',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '模型关系' },
  children: [
    {
      path: '',
      name: 'common-table-relationship',
      component: () => import('./common-table-relationship.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'common-table-relationship-new',
      component: () => import('./common-table-relationship-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':commonTableRelationshipId/edit',
      name: 'common-table-relationship-edit',
      component: () => import('./common-table-relationship-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':commonTableRelationshipId/detail',
      name: 'common-table-relationship-detail',
      component: () => import('./common-table-relationship-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
