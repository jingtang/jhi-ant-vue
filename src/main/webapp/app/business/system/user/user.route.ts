import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const userRoute: RouteConfig = {
  path: 'user',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '个人信息' },
  children: [
    {
      path: '',
      name: 'user',
      component: () => import('./user.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'user-new',
      component: () => import('./user-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':userId/edit',
      name: 'user-edit',
      component: () => import('./user-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':userId/detail',
      name: 'user-detail',
      component: () => import('./user-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
