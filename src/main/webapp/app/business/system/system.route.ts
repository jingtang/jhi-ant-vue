import { RouteConfig } from 'vue-router';
import { PageView } from '@/layouts';
import { userRoute } from '@/business/system/user/user.route';
import { authorityRoute } from '@/business/system/authority/authority.route';
import { viewPermissionRoute } from '@/business/system/view-permission/view-permission.route';
import { apiPermissionRoute } from '@/business/system/api-permission/api-permission.route';
// jhipster-needle-add-entity-to-client-root-folder-router-import - JHipster will import entities to the client root folder router here

export const systemRoute: RouteConfig = {
  path: 'system',
  component: PageView,
  meta: { authorities: ['ROLE_USER'], title: '系统设置' },
  children: [
    userRoute,
    authorityRoute,
    viewPermissionRoute,
    apiPermissionRoute
    // jhipster-needle-add-entity-to-client-root-folder-router - JHipster will add entities to the client root folder router here
  ]
};
