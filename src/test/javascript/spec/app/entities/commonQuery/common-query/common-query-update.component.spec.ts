/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CommonQueryUpdateComponent from '@/business/commonQuery/common-query/common-query-update.vue';
import CommonQueryClass from '@/business/commonQuery/common-query/common-query-update.component';
import CommonQueryService from '@/business/commonQuery/common-query/common-query.service';

import CommonQueryItemService from '@/business/commonQuery/common-query-item/common-query-item.service';

import UserService from '@/admin/user-management/user-management.service';

import CommonTableService from '@/business/modelConfig/common-table/common-table.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CommonQuery Management Update Component', () => {
    let wrapper: Wrapper<CommonQueryClass>;
    let comp: CommonQueryClass;
    let commonQueryServiceStub: SinonStubbedInstance<CommonQueryService>;

    beforeEach(() => {
      commonQueryServiceStub = sinon.createStubInstance<CommonQueryService>(CommonQueryService);

      wrapper = shallowMount<CommonQueryClass>(CommonQueryUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          commonQueryService: () => commonQueryServiceStub,

          commonQueryItemService: () => new CommonQueryItemService(),

          userService: () => new UserService(),

          commonTableService: () => new CommonTableService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.commonQuery = entity;
        // @ts-ignore
        commonQueryServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commonQueryServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.commonQuery = entity;
        // @ts-ignore
        commonQueryServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commonQueryServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
