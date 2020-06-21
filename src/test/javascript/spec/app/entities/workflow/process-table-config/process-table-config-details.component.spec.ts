/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProcessTableConfigDetailComponent from '@/business/workflow/process-table-config/process-table-config-details.vue';
import ProcessTableConfigClass from '@/business/workflow/process-table-config/process-table-config-details.component';
import ProcessTableConfigService from '@/business/workflow/process-table-config/process-table-config.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProcessTableConfig Management Detail Component', () => {
    let wrapper: Wrapper<ProcessTableConfigClass>;
    let comp: ProcessTableConfigClass;
    let processTableConfigServiceStub: SinonStubbedInstance<ProcessTableConfigService>;

    beforeEach(() => {
      processTableConfigServiceStub = sinon.createStubInstance<ProcessTableConfigService>(ProcessTableConfigService);

      wrapper = shallowMount<ProcessTableConfigClass>(ProcessTableConfigDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { processTableConfigService: () => processTableConfigServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProcessTableConfig = { id: 123 };
        // @ts-ignore
        processTableConfigServiceStub.find.resolves(foundProcessTableConfig);

        // WHEN
        comp.retrieveProcessTableConfig(123);
        await comp.$nextTick();

        // THEN
        expect(comp.processTableConfig).toBe(foundProcessTableConfig);
      });
    });
  });
});
