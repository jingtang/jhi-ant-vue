import BasicLayout from '@/layouts/BasicLayout.vue';
import { RouteConfig } from 'vue-router';
import { workflowRoute } from '@/business/workflow/workflow.route';
import { filesRoute } from '@/business/files/files.route';
import { settingsRoute } from '@/business/settings/settings.route';
import { systemRoute } from '@/business/system/system.route';
import { modelConfigRoute } from '@/business/modelConfig/modelConfig.route';
import { companyRoute } from '@/business/company/company.route';
import { reportRoute } from '@/business/report/report.route';
// jhipster-needle-add-client-root-folder-router-to-business-router-import - JHipster will import entities to the client root folder router here

export const businessRoute: RouteConfig = {
  path: '/business',
  name: 'business',
  component: BasicLayout,
  meta: { authorities: ['ROLE_ADMIN'], title: '业务' },
  children: [
    workflowRoute,
    filesRoute,
    settingsRoute,
    systemRoute,
    modelConfigRoute,
    companyRoute,
    reportRoute
    // jhipster-needle-add-client-root-folder-router-to-business-router - JHipster will import entities to the client root folder router here
  ]
};
