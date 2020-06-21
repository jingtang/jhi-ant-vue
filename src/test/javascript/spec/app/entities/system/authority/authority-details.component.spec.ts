/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AuthorityDetailComponent from '@/business/system/authority/authority-details.vue';
import AuthorityClass from '@/business/system/authority/authority-details.component';
import AuthorityService from '@/business/system/authority/authority.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Authority Management Detail Component', () => {
    let wrapper: Wrapper<AuthorityClass>;
    let comp: AuthorityClass;
    let authorityServiceStub: SinonStubbedInstance<AuthorityService>;

    beforeEach(() => {
      authorityServiceStub = sinon.createStubInstance<AuthorityService>(AuthorityService);

      wrapper = shallowMount<AuthorityClass>(AuthorityDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { authorityService: () => authorityServiceStub }
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAuthority = { id: 123 };
        // @ts-ignore
        authorityServiceStub.find.resolves(foundAuthority);

        // WHEN
        comp.retrieveAuthority(123);
        await comp.$nextTick();

        // THEN
        expect(comp.authority).toBe(foundAuthority);
      });
    });
  });
});
