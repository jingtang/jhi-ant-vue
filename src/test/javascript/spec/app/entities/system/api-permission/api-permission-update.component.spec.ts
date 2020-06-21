/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ApiPermissionUpdateComponent from '@/business/system/api-permission/api-permission-update.vue';
import ApiPermissionClass from '@/business/system/api-permission/api-permission-update.component';
import ApiPermissionService from '@/business/system/api-permission/api-permission.service';

import ViewPermissionService from '@/business/system/view-permission/view-permission.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ApiPermission Management Update Component', () => {
    let wrapper: Wrapper<ApiPermissionClass>;
    let comp: ApiPermissionClass;
    let apiPermissionServiceStub: SinonStubbedInstance<ApiPermissionService>;

    beforeEach(() => {
      apiPermissionServiceStub = sinon.createStubInstance<ApiPermissionService>(ApiPermissionService);

      wrapper = shallowMount<ApiPermissionClass>(ApiPermissionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          apiPermissionService: () => apiPermissionServiceStub,

          viewPermissionService: () => new ViewPermissionService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.apiPermission = entity;
        // @ts-ignore
        apiPermissionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiPermissionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.apiPermission = entity;
        // @ts-ignore
        apiPermissionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiPermissionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
