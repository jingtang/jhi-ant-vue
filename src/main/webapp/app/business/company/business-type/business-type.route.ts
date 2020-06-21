import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const businessTypeRoute: RouteConfig = {
  path: 'business-type',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '业务类型' },
  children: [
    {
      path: '',
      name: 'business-type',
      component: () => import('./business-type.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'business-type-new',
      component: () => import('./business-type-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':businessTypeId/edit',
      name: 'business-type-edit',
      component: () => import('./business-type-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':businessTypeId/detail',
      name: 'business-type-detail',
      component: () => import('./business-type-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
