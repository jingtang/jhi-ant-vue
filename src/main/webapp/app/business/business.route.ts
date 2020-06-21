import BasicLayout from '@/layouts/BasicLayout.vue';
import { RouteConfig } from 'vue-router';
import { workflowRoute } from '@/business/workflow/workflow.route';
// jhipster-needle-add-client-root-folder-router-to-business-router-import - JHipster will import entities to the client root folder router here

export const businessRoute: RouteConfig = {
  path: '/business',
  name: 'business',
  component: BasicLayout,
  meta: { authorities: ['ROLE_ADMIN'], title: '业务' },
  children: [
    workflowRoute
    // jhipster-needle-add-client-root-folder-router-to-business-router - JHipster will import entities to the client root folder router here
  ]
};
