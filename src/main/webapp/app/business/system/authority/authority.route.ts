import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const authorityRoute: RouteConfig = {
  path: 'authority',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '角色' },
  children: [
    {
      path: '',
      name: 'authority',
      component: () => import('./authority.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'authority-new',
      component: () => import('./authority-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':authorityId/edit',
      name: 'authority-edit',
      component: () => import('./authority-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':authorityId/detail',
      name: 'authority-detail',
      component: () => import('./authority-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
