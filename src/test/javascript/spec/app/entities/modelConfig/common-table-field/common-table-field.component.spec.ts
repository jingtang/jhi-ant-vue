/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CommonTableFieldComponent from '@/business/modelConfig/common-table-field/common-table-field.vue';
import CommonTableFieldClass from '@/business/modelConfig/common-table-field/common-table-field.component';
import CommonTableFieldService from '@/business/modelConfig/common-table-field/common-table-field.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {}
  }
};

describe('Component Tests', () => {
  describe('CommonTableField Management Component', () => {
    let wrapper: Wrapper<CommonTableFieldClass>;
    let comp: CommonTableFieldClass;
    let commonTableFieldServiceStub: SinonStubbedInstance<CommonTableFieldService>;

    beforeEach(() => {
      commonTableFieldServiceStub = sinon.createStubInstance<CommonTableFieldService>(CommonTableFieldService);
      // @ts-ignore
      commonTableFieldServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CommonTableFieldClass>(CommonTableFieldComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          commonTableFieldService: () => commonTableFieldServiceStub
        }
      });
      comp = wrapper.vm;
    });

    it('should be a Vue instance', () => {
      expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('Should call load all on init', async () => {
      // GIVEN
      // @ts-ignore
      commonTableFieldServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadAll();
      await comp.$nextTick();

      // THEN
      expect(commonTableFieldServiceStub.retrieve.called).toBeTruthy();
      expect(comp.commonTableFields[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      // @ts-ignore
      commonTableFieldServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(commonTableFieldServiceStub.retrieve.called).toBeTruthy();
      expect(comp.commonTableFields[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      commonTableFieldServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(commonTableFieldServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      commonTableFieldServiceStub.retrieve.reset();
      // @ts-ignore
      commonTableFieldServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(commonTableFieldServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.commonTableFields[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      // @ts-ignore
      commonTableFieldServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeById(123);
      await comp.$nextTick();

      // THEN
      expect(commonTableFieldServiceStub.delete.called).toBeTruthy();
      expect(commonTableFieldServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
