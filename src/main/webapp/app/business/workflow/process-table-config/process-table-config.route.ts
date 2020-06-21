import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const processTableConfigRoute: RouteConfig = {
  path: 'process-table-config',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '流程业务配置' },
  children: [
    {
      path: '',
      name: 'process-table-config',
      component: () => import('./process-table-config.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'process-table-config-new',
      component: () => import('./process-table-config-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':processTableConfigId/edit',
      name: 'process-table-config-edit',
      component: () => import('./process-table-config-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':processTableConfigId/detail',
      name: 'process-table-config-detail',
      component: () => import('./process-table-config-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
