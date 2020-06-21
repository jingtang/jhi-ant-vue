/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompanyCustomerUpdateComponent from '@/business/company/company-customer/company-customer-update.vue';
import CompanyCustomerClass from '@/business/company/company-customer/company-customer-update.component';
import CompanyCustomerService from '@/business/company/company-customer/company-customer.service';

import CompanyUserService from '@/business/company/company-user/company-user.service';

import CompanyBusinessService from '@/business/company/company-business/company-business.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompanyCustomer Management Update Component', () => {
    let wrapper: Wrapper<CompanyCustomerClass>;
    let comp: CompanyCustomerClass;
    let companyCustomerServiceStub: SinonStubbedInstance<CompanyCustomerService>;

    beforeEach(() => {
      companyCustomerServiceStub = sinon.createStubInstance<CompanyCustomerService>(CompanyCustomerService);

      wrapper = shallowMount<CompanyCustomerClass>(CompanyCustomerUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          companyCustomerService: () => companyCustomerServiceStub,

          companyUserService: () => new CompanyUserService(),

          companyBusinessService: () => new CompanyBusinessService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.companyCustomer = entity;
        // @ts-ignore
        companyCustomerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyCustomerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.companyCustomer = entity;
        // @ts-ignore
        companyCustomerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyCustomerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
