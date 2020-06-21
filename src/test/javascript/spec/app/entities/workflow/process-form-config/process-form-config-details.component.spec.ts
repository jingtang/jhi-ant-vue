/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProcessFormConfigDetailComponent from '@/business/workflow/process-form-config/process-form-config-details.vue';
import ProcessFormConfigClass from '@/business/workflow/process-form-config/process-form-config-details.component';
import ProcessFormConfigService from '@/business/workflow/process-form-config/process-form-config.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProcessFormConfig Management Detail Component', () => {
    let wrapper: Wrapper<ProcessFormConfigClass>;
    let comp: ProcessFormConfigClass;
    let processFormConfigServiceStub: SinonStubbedInstance<ProcessFormConfigService>;

    beforeEach(() => {
      processFormConfigServiceStub = sinon.createStubInstance<ProcessFormConfigService>(ProcessFormConfigService);

      wrapper = shallowMount<ProcessFormConfigClass>(ProcessFormConfigDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { processFormConfigService: () => processFormConfigServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProcessFormConfig = { id: 123 };
        // @ts-ignore
        processFormConfigServiceStub.find.resolves(foundProcessFormConfig);

        // WHEN
        comp.retrieveProcessFormConfig(123);
        await comp.$nextTick();

        // THEN
        expect(comp.processFormConfig).toBe(foundProcessFormConfig);
      });
    });
  });
});
