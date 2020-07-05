import { RouteConfig } from 'vue-router';
import { PageView } from '@/layouts';
import { commonQueryRoute } from '@/business/commonQuery/common-query/common-query.route';
import { commonQueryItemRoute } from '@/business/commonQuery/common-query-item/common-query-item.route';
// jhipster-needle-add-entity-to-client-root-folder-router-import - JHipster will import entities to the client root folder router here

export const queryRoute: RouteConfig = {
  path: 'commonQuery',
  component: PageView,
  meta: { authorities: ['ROLE_USER'], title: 'commonQuery' },
  children: [
    commonQueryItemRoute,
    commonQueryRoute
    // jhipster-needle-add-entity-to-client-root-folder-router - JHipster will add entities to the client root folder router here
  ]
};
