/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CommonQueryDetailComponent from '@/business/commonQuery/common-query/common-query-details.vue';
import CommonQueryClass from '@/business/commonQuery/common-query/common-query-details.component';
import CommonQueryService from '@/business/commonQuery/common-query/common-query.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CommonQuery Management Detail Component', () => {
    let wrapper: Wrapper<CommonQueryClass>;
    let comp: CommonQueryClass;
    let commonQueryServiceStub: SinonStubbedInstance<CommonQueryService>;

    beforeEach(() => {
      commonQueryServiceStub = sinon.createStubInstance<CommonQueryService>(CommonQueryService);

      wrapper = shallowMount<CommonQueryClass>(CommonQueryDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { commonQueryService: () => commonQueryServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCommonQuery = { id: 123 };
        // @ts-ignore
        commonQueryServiceStub.find.resolves(foundCommonQuery);

        // WHEN
        comp.retrieveCommonQuery(123);
        await comp.$nextTick();

        // THEN
        expect(comp.commonQuery).toBe(foundCommonQuery);
      });
    });
  });
});
