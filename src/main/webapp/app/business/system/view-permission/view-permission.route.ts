import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const viewPermissionRoute: RouteConfig = {
  path: 'view-permission',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '可视权限' },
  children: [
    {
      path: '',
      name: 'view-permission',
      component: () => import('./view-permission.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'view-permission-new',
      component: () => import('./view-permission-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':viewPermissionId/edit',
      name: 'view-permission-edit',
      component: () => import('./view-permission-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':viewPermissionId/detail',
      name: 'view-permission-detail',
      component: () => import('./view-permission-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
