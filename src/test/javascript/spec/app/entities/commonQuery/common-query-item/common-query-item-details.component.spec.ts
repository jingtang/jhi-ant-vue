/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CommonQueryItemDetailComponent from '@/business/commonQuery/common-query-item/common-query-item-details.vue';
import CommonQueryItemClass from '@/business/commonQuery/common-query-item/common-query-item-details.component';
import CommonQueryItemService from '@/business/commonQuery/common-query-item/common-query-item.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CommonQueryItem Management Detail Component', () => {
    let wrapper: Wrapper<CommonQueryItemClass>;
    let comp: CommonQueryItemClass;
    let commonQueryItemServiceStub: SinonStubbedInstance<CommonQueryItemService>;

    beforeEach(() => {
      commonQueryItemServiceStub = sinon.createStubInstance<CommonQueryItemService>(CommonQueryItemService);

      wrapper = shallowMount<CommonQueryItemClass>(CommonQueryItemDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { commonQueryItemService: () => commonQueryItemServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCommonQueryItem = { id: 123 };
        // @ts-ignore
        commonQueryItemServiceStub.find.resolves(foundCommonQueryItem);

        // WHEN
        comp.retrieveCommonQueryItem(123);
        await comp.$nextTick();

        // THEN
        expect(comp.commonQueryItem).toBe(foundCommonQueryItem);
      });
    });
  });
});
