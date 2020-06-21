import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const processDesignRoute: RouteConfig = {
  path: 'process-design',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '流程定义' },
  children: [
    {
      path: '',
      name: 'process-design',
      component: () => import('./workflow-design.vue'),
      meta: { authorities: ['ROLE_USER'], title: '设计流程' }
    },
    {
      path: ':processDefinitionKey/edit',
      name: 'deployment-edit',
      component: () => import('./workflow-design.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑流程' }
    }
  ]
};
