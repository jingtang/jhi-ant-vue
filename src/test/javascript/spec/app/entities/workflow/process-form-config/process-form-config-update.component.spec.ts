/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProcessFormConfigUpdateComponent from '@/business/workflow/process-form-config/process-form-config-update.vue';
import ProcessFormConfigClass from '@/business/workflow/process-form-config/process-form-config-update.component';
import ProcessFormConfigService from '@/business/workflow/process-form-config/process-form-config.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ProcessFormConfig Management Update Component', () => {
    let wrapper: Wrapper<ProcessFormConfigClass>;
    let comp: ProcessFormConfigClass;
    let processFormConfigServiceStub: SinonStubbedInstance<ProcessFormConfigService>;

    beforeEach(() => {
      processFormConfigServiceStub = sinon.createStubInstance<ProcessFormConfigService>(ProcessFormConfigService);

      wrapper = shallowMount<ProcessFormConfigClass>(ProcessFormConfigUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          processFormConfigService: () => processFormConfigServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.processFormConfig = entity;
        // @ts-ignore
        processFormConfigServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processFormConfigServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.processFormConfig = entity;
        // @ts-ignore
        processFormConfigServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processFormConfigServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
