import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const leaveRoute: RouteConfig = {
  path: 'leave',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '请假' },
  children: [
    {
      path: '',
      name: 'leave',
      component: () => import('./leave.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'leave-new',
      component: () => import('./leave-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':leaveId/edit',
      name: 'leave-edit',
      component: () => import('./leave-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':leaveId/detail',
      name: 'leave-detail',
      component: () => import('./leave-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
