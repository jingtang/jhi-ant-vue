import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const deploymentRoute: RouteConfig = {
  path: 'deployment',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '流程部署' },
  children: [
    {
      path: '',
      name: 'deployment',
      component: () => import('./deployment.vue'),
      meta: { authorities: ['ROLE_USER'], title: '已部署' }
    }
  ]
};
