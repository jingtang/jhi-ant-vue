/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompanyBusinessUpdateComponent from '@/business/company/company-business/company-business-update.vue';
import CompanyBusinessClass from '@/business/company/company-business/company-business-update.component';
import CompanyBusinessService from '@/business/company/company-business/company-business.service';

import BusinessTypeService from '@/business/company/business-type/business-type.service';

import CompanyCustomerService from '@/business/company/company-customer/company-customer.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompanyBusiness Management Update Component', () => {
    let wrapper: Wrapper<CompanyBusinessClass>;
    let comp: CompanyBusinessClass;
    let companyBusinessServiceStub: SinonStubbedInstance<CompanyBusinessService>;

    beforeEach(() => {
      companyBusinessServiceStub = sinon.createStubInstance<CompanyBusinessService>(CompanyBusinessService);

      wrapper = shallowMount<CompanyBusinessClass>(CompanyBusinessUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          companyBusinessService: () => companyBusinessServiceStub,

          businessTypeService: () => new BusinessTypeService(),

          companyCustomerService: () => new CompanyCustomerService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.companyBusiness = entity;
        // @ts-ignore
        companyBusinessServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyBusinessServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.companyBusiness = entity;
        // @ts-ignore
        companyBusinessServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyBusinessServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
