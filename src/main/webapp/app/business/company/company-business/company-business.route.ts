import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const companyBusinessRoute: RouteConfig = {
  path: 'company-business',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '客户业务' },
  children: [
    {
      path: '',
      name: 'company-business',
      component: () => import('./company-business.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'company-business-new',
      component: () => import('./company-business-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':companyBusinessId/edit',
      name: 'company-business-edit',
      component: () => import('./company-business-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':companyBusinessId/detail',
      name: 'company-business-detail',
      component: () => import('./company-business-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
