import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const companyCustomerRoute: RouteConfig = {
  path: 'company-customer',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '客户单位' },
  children: [
    {
      path: '',
      name: 'company-customer',
      component: () => import('./company-customer.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'company-customer-new',
      component: () => import('./company-customer-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':companyCustomerId/edit',
      name: 'company-customer-edit',
      component: () => import('./company-customer-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':companyCustomerId/detail',
      name: 'company-customer-detail',
      component: () => import('./company-customer-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
