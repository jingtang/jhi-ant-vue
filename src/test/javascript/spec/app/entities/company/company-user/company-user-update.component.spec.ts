/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CompanyUserUpdateComponent from '@/business/company/company-user/company-user-update.vue';
import CompanyUserClass from '@/business/company/company-user/company-user-update.component';
import CompanyUserService from '@/business/company/company-user/company-user.service';

import UserService from '@/admin/user-management/user-management.service';

import CompanyCustomerService from '@/business/company/company-customer/company-customer.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CompanyUser Management Update Component', () => {
    let wrapper: Wrapper<CompanyUserClass>;
    let comp: CompanyUserClass;
    let companyUserServiceStub: SinonStubbedInstance<CompanyUserService>;

    beforeEach(() => {
      companyUserServiceStub = sinon.createStubInstance<CompanyUserService>(CompanyUserService);

      wrapper = shallowMount<CompanyUserClass>(CompanyUserUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          companyUserService: () => companyUserServiceStub,

          userService: () => new UserService(),

          companyCustomerService: () => new CompanyCustomerService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.companyUser = entity;
        // @ts-ignore
        companyUserServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyUserServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.companyUser = entity;
        // @ts-ignore
        companyUserServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(companyUserServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
