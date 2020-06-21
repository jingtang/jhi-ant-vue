import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const processFormConfigRoute: RouteConfig = {
  path: 'process-form-config',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '流程表单' },
  children: [
    {
      path: '',
      name: 'process-form-config',
      component: () => import('./process-form-config.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'process-form-config-new',
      component: () => import('./process-form-config-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':processFormConfigId/edit',
      name: 'process-form-config-edit',
      component: () => import('./process-form-config-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':processFormConfigId/detail',
      name: 'process-form-config-detail',
      component: () => import('./process-form-config-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
