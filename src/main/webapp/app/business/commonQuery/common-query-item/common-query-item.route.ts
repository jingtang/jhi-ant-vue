import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const commonQueryItemRoute: RouteConfig = {
  path: 'common-query-item',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '通用查询条目' },
  children: [
    {
      path: '',
      name: 'common-query-item',
      component: () => import('./common-query-item.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'common-query-item-new',
      component: () => import('./common-query-item-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':commonQueryItemId/edit',
      name: 'common-query-item-edit',
      component: () => import('./common-query-item-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':commonQueryItemId/detail',
      name: 'common-query-item-detail',
      component: () => import('./common-query-item-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
