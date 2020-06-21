import { RouteConfig } from 'vue-router';
import { PageView } from '@/layouts';
import { dataDictionaryRoute } from '@/business/settings/data-dictionary/data-dictionary.route';
import { gpsInfoRoute } from '@/business/settings/gps-info/gps-info.route';
import { administrativeDivisionRoute } from '@/business/settings/administrative-division/administrative-division.route';
// jhipster-needle-add-entity-to-client-root-folder-router-import - JHipster will import entities to the client root folder router here

export const settingsRoute: RouteConfig = {
  path: 'settings',
  component: PageView,
  meta: { authorities: ['ROLE_USER'], title: '配置管理' },
  children: [
    dataDictionaryRoute,
    gpsInfoRoute,
    administrativeDivisionRoute
    // jhipster-needle-add-entity-to-client-root-folder-router - JHipster will add entities to the client root folder router here
  ]
};
