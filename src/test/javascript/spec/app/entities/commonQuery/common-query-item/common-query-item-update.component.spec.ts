/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CommonQueryItemUpdateComponent from '@/business/commonQuery/common-query-item/common-query-item-update.vue';
import CommonQueryItemClass from '@/business/commonQuery/common-query-item/common-query-item-update.component';
import CommonQueryItemService from '@/business/commonQuery/common-query-item/common-query-item.service';

import CommonQueryService from '@/business/commonQuery/common-query/common-query.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CommonQueryItem Management Update Component', () => {
    let wrapper: Wrapper<CommonQueryItemClass>;
    let comp: CommonQueryItemClass;
    let commonQueryItemServiceStub: SinonStubbedInstance<CommonQueryItemService>;

    beforeEach(() => {
      commonQueryItemServiceStub = sinon.createStubInstance<CommonQueryItemService>(CommonQueryItemService);

      wrapper = shallowMount<CommonQueryItemClass>(CommonQueryItemUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          commonQueryItemService: () => commonQueryItemServiceStub,

          commonQueryService: () => new CommonQueryService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.commonQueryItem = entity;
        // @ts-ignore
        commonQueryItemServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commonQueryItemServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.commonQueryItem = entity;
        // @ts-ignore
        commonQueryItemServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commonQueryItemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
