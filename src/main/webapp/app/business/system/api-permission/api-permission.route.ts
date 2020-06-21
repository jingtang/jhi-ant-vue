import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const apiPermissionRoute: RouteConfig = {
  path: 'api-permission',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: 'API权限' },
  children: [
    {
      path: '',
      name: 'api-permission',
      component: () => import('./api-permission.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'api-permission-new',
      component: () => import('./api-permission-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':apiPermissionId/edit',
      name: 'api-permission-edit',
      component: () => import('./api-permission-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':apiPermissionId/detail',
      name: 'api-permission-detail',
      component: () => import('./api-permission-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
