import { RouteConfig } from 'vue-router';
import { PageView } from '@/layouts';
import { deploymentRoute } from '@/business/workflow/deployment/deployment.route';
import { processDesignRoute } from '@/business/workflow/process-design/process-design.route';
import { processTableConfigRoute } from '@/business/workflow/process-table-config/process-table-config.route';
import { processFormConfigRoute } from '@/business/workflow/process-form-config/process-form-config.route';
import { processEntityRelationRoute } from '@/business/workflow/process-entity-relation/process-entity-relation.route';
import { leaveRoute } from '@/business/workflow/leave/leave.route';
// jhipster-needle-add-entity-to-client-root-folder-router-import - JHipster will import entities to the client root folder router here

export const workflowRoute: RouteConfig = {
  path: 'workflow',
  component: PageView,
  meta: { authorities: ['ROLE_USER'], title: '工作流' },
  children: [
    deploymentRoute,
    processDesignRoute,
    {
      path: 'process-instance',
      name: 'process-instance',
      component: () => import('./process-instance/process-instance.vue'),
      meta: { authorities: ['ROLE_USER'], title: '流程实例' }
    },
    {
      path: 'flowable-task',
      name: 'flowable-task',
      component: () => import('./flowable-task/flowable-task.vue'),
      meta: { authorities: ['ROLE_USER'], title: '流程任务' }
    },
    {
      path: 'process-definition',
      name: 'process-definition',
      component: () => import('./process-definition/process-definition.vue'),
      meta: { authorities: ['ROLE_USER'], title: '流程定义' }
    },
    processTableConfigRoute,
    processFormConfigRoute,
    processEntityRelationRoute,
    leaveRoute
    // jhipster-needle-add-entity-to-client-root-folder-router - JHipster will add entities to the client root folder router here
  ]
};
