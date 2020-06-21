/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProcessEntityRelationUpdateComponent from '@/business/workflow/process-entity-relation/process-entity-relation-update.vue';
import ProcessEntityRelationClass from '@/business/workflow/process-entity-relation/process-entity-relation-update.component';
import ProcessEntityRelationService from '@/business/workflow/process-entity-relation/process-entity-relation.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ProcessEntityRelation Management Update Component', () => {
    let wrapper: Wrapper<ProcessEntityRelationClass>;
    let comp: ProcessEntityRelationClass;
    let processEntityRelationServiceStub: SinonStubbedInstance<ProcessEntityRelationService>;

    beforeEach(() => {
      processEntityRelationServiceStub = sinon.createStubInstance<ProcessEntityRelationService>(ProcessEntityRelationService);

      wrapper = shallowMount<ProcessEntityRelationClass>(ProcessEntityRelationUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          processEntityRelationService: () => processEntityRelationServiceStub
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.processEntityRelation = entity;
        // @ts-ignore
        processEntityRelationServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processEntityRelationServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.processEntityRelation = entity;
        // @ts-ignore
        processEntityRelationServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processEntityRelationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
