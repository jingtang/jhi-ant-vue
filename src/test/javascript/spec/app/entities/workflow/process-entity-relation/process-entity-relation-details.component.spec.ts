/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProcessEntityRelationDetailComponent from '@/business/workflow/process-entity-relation/process-entity-relation-details.vue';
import ProcessEntityRelationClass from '@/business/workflow/process-entity-relation/process-entity-relation-details.component';
import ProcessEntityRelationService from '@/business/workflow/process-entity-relation/process-entity-relation.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProcessEntityRelation Management Detail Component', () => {
    let wrapper: Wrapper<ProcessEntityRelationClass>;
    let comp: ProcessEntityRelationClass;
    let processEntityRelationServiceStub: SinonStubbedInstance<ProcessEntityRelationService>;

    beforeEach(() => {
      processEntityRelationServiceStub = sinon.createStubInstance<ProcessEntityRelationService>(ProcessEntityRelationService);

      wrapper = shallowMount<ProcessEntityRelationClass>(ProcessEntityRelationDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { processEntityRelationService: () => processEntityRelationServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProcessEntityRelation = { id: 123 };
        // @ts-ignore
        processEntityRelationServiceStub.find.resolves(foundProcessEntityRelation);

        // WHEN
        comp.retrieveProcessEntityRelation(123);
        await comp.$nextTick();

        // THEN
        expect(comp.processEntityRelation).toBe(foundProcessEntityRelation);
      });
    });
  });
});
