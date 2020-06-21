import { RouteConfig } from 'vue-router';
import { PageView } from '@/layouts';
import { companyCustomerRoute } from '@/business/company/company-customer/company-customer.route';
import { companyUserRoute } from '@/business/company/company-user/company-user.route';
import { companyBusinessRoute } from '@/business/company/company-business/company-business.route';
import { businessTypeRoute } from '@/business/company/business-type/business-type.route';
// jhipster-needle-add-entity-to-client-root-folder-router-import - JHipster will import entities to the client root folder router here

export const companyRoute: RouteConfig = {
  path: 'company',
  component: PageView,
  meta: { authorities: ['ROLE_USER'], title: '客户管理' },
  children: [
    companyCustomerRoute,
    companyUserRoute,
    companyBusinessRoute,
    businessTypeRoute
    // jhipster-needle-add-entity-to-client-root-folder-router - JHipster will add entities to the client root folder router here
  ]
};
