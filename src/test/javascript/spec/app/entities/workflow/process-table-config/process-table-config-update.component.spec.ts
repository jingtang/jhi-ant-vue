/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProcessTableConfigUpdateComponent from '@/business/workflow/process-table-config/process-table-config-update.vue';
import ProcessTableConfigClass from '@/business/workflow/process-table-config/process-table-config-update.component';
import ProcessTableConfigService from '@/business/workflow/process-table-config/process-table-config.service';

import CommonTableService from '@/business/modelConfig/common-table/common-table.service';

import UserService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ProcessTableConfig Management Update Component', () => {
    let wrapper: Wrapper<ProcessTableConfigClass>;
    let comp: ProcessTableConfigClass;
    let processTableConfigServiceStub: SinonStubbedInstance<ProcessTableConfigService>;

    beforeEach(() => {
      processTableConfigServiceStub = sinon.createStubInstance<ProcessTableConfigService>(ProcessTableConfigService);

      wrapper = shallowMount<ProcessTableConfigClass>(ProcessTableConfigUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          processTableConfigService: () => processTableConfigServiceStub,

          commonTableService: () => new CommonTableService(),

          userService: () => new UserService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.processTableConfig = entity;
        // @ts-ignore
        processTableConfigServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processTableConfigServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.processTableConfig = entity;
        // @ts-ignore
        processTableConfigServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processTableConfigServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
