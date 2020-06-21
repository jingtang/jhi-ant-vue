import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const companyUserRoute: RouteConfig = {
  path: 'company-user',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '人员账号' },
  children: [
    {
      path: '',
      name: 'company-user',
      component: () => import('./company-user.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'company-user-new',
      component: () => import('./company-user-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':companyUserId/edit',
      name: 'company-user-edit',
      component: () => import('./company-user-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':companyUserId/detail',
      name: 'company-user-detail',
      component: () => import('./company-user-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
