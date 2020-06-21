/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CompanyCustomerDetailComponent from '@/business/company/company-customer/company-customer-details.vue';
import CompanyCustomerClass from '@/business/company/company-customer/company-customer-details.component';
import CompanyCustomerService from '@/business/company/company-customer/company-customer.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CompanyCustomer Management Detail Component', () => {
    let wrapper: Wrapper<CompanyCustomerClass>;
    let comp: CompanyCustomerClass;
    let companyCustomerServiceStub: SinonStubbedInstance<CompanyCustomerService>;

    beforeEach(() => {
      companyCustomerServiceStub = sinon.createStubInstance<CompanyCustomerService>(CompanyCustomerService);

      wrapper = shallowMount<CompanyCustomerClass>(CompanyCustomerDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { companyCustomerService: () => companyCustomerServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCompanyCustomer = { id: 123 };
        // @ts-ignore
        companyCustomerServiceStub.find.resolves(foundCompanyCustomer);

        // WHEN
        comp.retrieveCompanyCustomer(123);
        await comp.$nextTick();

        // THEN
        expect(comp.companyCustomer).toBe(foundCompanyCustomer);
      });
    });
  });
});
