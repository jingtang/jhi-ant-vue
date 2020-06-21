/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AuthorityUpdateComponent from '@/business/system/authority/authority-update.vue';
import AuthorityClass from '@/business/system/authority/authority-update.component';
import AuthorityService from '@/business/system/authority/authority.service';

import UserService from '@/admin/user-management/user-management.service';

import ViewPermissionService from '@/business/system/view-permission/view-permission.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Authority Management Update Component', () => {
    let wrapper: Wrapper<AuthorityClass>;
    let comp: AuthorityClass;
    let authorityServiceStub: SinonStubbedInstance<AuthorityService>;

    beforeEach(() => {
      authorityServiceStub = sinon.createStubInstance<AuthorityService>(AuthorityService);

      wrapper = shallowMount<AuthorityClass>(AuthorityUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          authorityService: () => authorityServiceStub,

          userService: () => new UserService(),

          viewPermissionService: () => new ViewPermissionService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.authority = entity;
        // @ts-ignore
        authorityServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(authorityServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.authority = entity;
        // @ts-ignore
        authorityServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(authorityServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
