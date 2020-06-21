/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import LeaveUpdateComponent from '@/business/workflow/leave/leave-update.vue';
import LeaveClass from '@/business/workflow/leave/leave-update.component';
import LeaveService from '@/business/workflow/leave/leave.service';

import UploadImageService from '@/business/files/upload-image/upload-image.service';

import UserService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Leave Management Update Component', () => {
    let wrapper: Wrapper<LeaveClass>;
    let comp: LeaveClass;
    let leaveServiceStub: SinonStubbedInstance<LeaveService>;

    beforeEach(() => {
      leaveServiceStub = sinon.createStubInstance<LeaveService>(LeaveService);

      wrapper = shallowMount<LeaveClass>(LeaveUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          leaveService: () => leaveServiceStub,

          uploadImageService: () => new UploadImageService(),

          userService: () => new UserService()
        }
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.leave = entity;
        // @ts-ignore
        leaveServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(leaveServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.leave = entity;
        // @ts-ignore
        leaveServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(leaveServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
