/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LeaveDetailComponent from '@/business/workflow/leave/leave-details.vue';
import LeaveClass from '@/business/workflow/leave/leave-details.component';
import LeaveService from '@/business/workflow/leave/leave.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Leave Management Detail Component', () => {
    let wrapper: Wrapper<LeaveClass>;
    let comp: LeaveClass;
    let leaveServiceStub: SinonStubbedInstance<LeaveService>;

    beforeEach(() => {
      leaveServiceStub = sinon.createStubInstance<LeaveService>(LeaveService);

      wrapper = shallowMount<LeaveClass>(LeaveDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { leaveService: () => leaveServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLeave = { id: 123 };
        // @ts-ignore
        leaveServiceStub.find.resolves(foundLeave);

        // WHEN
        comp.retrieveLeave(123);
        await comp.$nextTick();

        // THEN
        expect(comp.leave).toBe(foundLeave);
      });
    });
  });
});
