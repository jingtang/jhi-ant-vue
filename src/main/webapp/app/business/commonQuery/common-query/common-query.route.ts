import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const commonQueryRoute: RouteConfig = {
  path: 'common-query',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '通用查询' },
  children: [
    {
      path: '',
      name: 'common-query',
      component: () => import('./common-query.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'common-query-new',
      component: () => import('./common-query-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':commonQueryId/edit',
      name: 'common-query-edit',
      component: () => import('./common-query-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':commonQueryId/detail',
      name: 'common-query-detail',
      component: () => import('./common-query-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
