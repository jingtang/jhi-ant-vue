import { RouteConfig } from 'vue-router';
import { PageView } from '@/layouts';
import { uReportFileRoute } from '@/business/report/u-report-file/u-report-file.route';
// jhipster-needle-add-entity-to-client-root-folder-router-import - JHipster will import entities to the client root folder router here

export const reportRoute: RouteConfig = {
  path: 'report',
  component: PageView,
  meta: { authorities: ['ROLE_USER'], title: 'report' },
  children: [
    uReportFileRoute
    // jhipster-needle-add-entity-to-client-root-folder-router - JHipster will add entities to the client root folder router here
  ]
};
