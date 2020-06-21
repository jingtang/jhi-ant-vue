import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const administrativeDivisionRoute: RouteConfig = {
  path: 'administrative-division',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '行政区划' },
  children: [
    {
      path: '',
      name: 'administrative-division',
      component: () => import('./administrative-division.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'administrative-division-new',
      component: () => import('./administrative-division-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':administrativeDivisionId/edit',
      name: 'administrative-division-edit',
      component: () => import('./administrative-division-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':administrativeDivisionId/detail',
      name: 'administrative-division-detail',
      component: () => import('./administrative-division-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
