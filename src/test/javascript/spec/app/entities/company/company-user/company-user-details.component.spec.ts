/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompanyUserDetailComponent from '@/business/company/company-user/company-user-details.vue';
import CompanyUserClass from '@/business/company/company-user/company-user-details.component';
import CompanyUserService from '@/business/company/company-user/company-user.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompanyUser Management Detail Component', () => {
    let wrapper: Wrapper<CompanyUserClass>;
    let comp: CompanyUserClass;
    let companyUserServiceStub: SinonStubbedInstance<CompanyUserService>;

    beforeEach(() => {
      companyUserServiceStub = sinon.createStubInstance<CompanyUserService>(CompanyUserService);

      wrapper = shallowMount<CompanyUserClass>(CompanyUserDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { companyUserService: () => companyUserServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompanyUser = { id: 123 };
        // @ts-ignore
        companyUserServiceStub.find.resolves(foundCompanyUser);

        // WHEN
        comp.retrieveCompanyUser(123);
        await comp.$nextTick();

        // THEN
        expect(comp.companyUser).toBe(foundCompanyUser);
      });
    });
  });
});
