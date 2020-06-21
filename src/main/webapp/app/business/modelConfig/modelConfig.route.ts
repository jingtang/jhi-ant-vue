import { RouteConfig } from 'vue-router';
import { PageView } from '@/layouts';
import { commonTableRoute } from '@/business/modelConfig/common-table/common-table.route';
import { commonTableFieldRoute } from '@/business/modelConfig/common-table-field/common-table-field.route';
import { commonTableRelationshipRoute } from '@/business/modelConfig/common-table-relationship/common-table-relationship.route';
// jhipster-needle-add-entity-to-client-root-folder-router-import - JHipster will import entities to the client root folder router here

export const modelConfigRoute: RouteConfig = {
  path: 'modelConfig',
  component: PageView,
  meta: { authorities: ['ROLE_USER'], title: 'modelConfig' },
  children: [
    commonTableRoute,
    commonTableFieldRoute,
    commonTableRelationshipRoute
    // jhipster-needle-add-entity-to-client-root-folder-router - JHipster will add entities to the client root folder router here
  ]
};
