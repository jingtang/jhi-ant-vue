/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompanyBusinessDetailComponent from '@/business/company/company-business/company-business-details.vue';
import CompanyBusinessClass from '@/business/company/company-business/company-business-details.component';
import CompanyBusinessService from '@/business/company/company-business/company-business.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompanyBusiness Management Detail Component', () => {
    let wrapper: Wrapper<CompanyBusinessClass>;
    let comp: CompanyBusinessClass;
    let companyBusinessServiceStub: SinonStubbedInstance<CompanyBusinessService>;

    beforeEach(() => {
      companyBusinessServiceStub = sinon.createStubInstance<CompanyBusinessService>(CompanyBusinessService);

      wrapper = shallowMount<CompanyBusinessClass>(CompanyBusinessDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { companyBusinessService: () => companyBusinessServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompanyBusiness = { id: 123 };
        // @ts-ignore
        companyBusinessServiceStub.find.resolves(foundCompanyBusiness);

        // WHEN
        comp.retrieveCompanyBusiness(123);
        await comp.$nextTick();

        // THEN
        expect(comp.companyBusiness).toBe(foundCompanyBusiness);
      });
    });
  });
});
