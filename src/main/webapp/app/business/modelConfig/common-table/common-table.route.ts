import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const commonTableRoute: RouteConfig = {
  path: 'common-table',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '模型' },
  children: [
    {
      path: '',
      name: 'common-table',
      component: () => import('./common-table.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'common-table-new',
      component: () => import('./common-table-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':commonTableId/edit',
      name: 'common-table-edit',
      component: () => import('./common-table-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':commonTableId/detail',
      name: 'common-table-detail',
      component: () => import('./common-table-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    },
    {
      path: ':commonTableId/designer',
      name: 'common-table-designer',
      component: () => import('./design-form.vue'),
      meta: { authorities: ['ROLE_USER'], title: '设计' }
    },
    {
      path: 'new/:copyFromId',
      name: 'common-table-copy',
      component: () => import('./common-table-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '复制' }
    }
  ]
};
