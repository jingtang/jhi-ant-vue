import Vuex from 'vuex';
import VueI18n from 'vue-i18n';
import JhiFormatter from './formatter';
import { setupAxiosInterceptors } from '@/shared/config/axios-interceptor';

import { library } from '@fortawesome/fontawesome-svg-core';
import { faSort } from '@fortawesome/free-solid-svg-icons/faSort';
import { faEye } from '@fortawesome/free-solid-svg-icons/faEye';
import { faSync } from '@fortawesome/free-solid-svg-icons/faSync';
import { faBan } from '@fortawesome/free-solid-svg-icons/faBan';
import { faTrash } from '@fortawesome/free-solid-svg-icons/faTrash';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons/faArrowLeft';
import { faSave } from '@fortawesome/free-solid-svg-icons/faSave';
import { faPlus } from '@fortawesome/free-solid-svg-icons/faPlus';
import { faPencilAlt } from '@fortawesome/free-solid-svg-icons/faPencilAlt';
import { faUser } from '@fortawesome/free-solid-svg-icons/faUser';
import { faHdd } from '@fortawesome/free-solid-svg-icons/faHdd';
import { faTachometerAlt } from '@fortawesome/free-solid-svg-icons/faTachometerAlt';
import { faHeart } from '@fortawesome/free-solid-svg-icons/faHeart';
import { faList } from '@fortawesome/free-solid-svg-icons/faList';
import { faTasks } from '@fortawesome/free-solid-svg-icons/faTasks';
import { faBook } from '@fortawesome/free-solid-svg-icons/faBook';
import { faLock } from '@fortawesome/free-solid-svg-icons/faLock';
import { faSignInAlt } from '@fortawesome/free-solid-svg-icons/faSignInAlt';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons/faSignOutAlt';
import { faThList } from '@fortawesome/free-solid-svg-icons/faThList';
import { faUserPlus } from '@fortawesome/free-solid-svg-icons/faUserPlus';
import { faWrench } from '@fortawesome/free-solid-svg-icons/faWrench';
import { faAsterisk } from '@fortawesome/free-solid-svg-icons/faAsterisk';
import { faFlag } from '@fortawesome/free-solid-svg-icons/faFlag';
import { faBell } from '@fortawesome/free-solid-svg-icons/faBell';
import { faHome } from '@fortawesome/free-solid-svg-icons/faHome';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons/faTimesCircle';
import { faSearch } from '@fortawesome/free-solid-svg-icons/faSearch';
import { faRoad } from '@fortawesome/free-solid-svg-icons/faRoad';
import { faCloud } from '@fortawesome/free-solid-svg-icons/faCloud';
import { faBars } from '@fortawesome/free-solid-svg-icons/faBars';
import { faTimes } from '@fortawesome/free-solid-svg-icons/faTimes';

import VueCookie from 'vue-cookie';
import Vuelidate from 'vuelidate';
import Vue2Filters from 'vue2-filters';

import * as filters from '@/shared/date/filters';
import { accountStore } from '@/shared/config/store/account-store';
import { alertStore } from '@/shared/config/store/alert-store';
import { translationStore } from '@/shared/config/store/translation-store';

const dateTimeFormats = {
  // jhipster-needle-i18n-language-date-time-format - JHipster will add/remove format options in this object
};

export function initVueApp(vue) {
  vue.use(VueCookie);
  vue.use(Vuelidate);
  vue.use(Vue2Filters);
  setupAxiosInterceptors(() => console.log('Unauthorized!'));
  filters.initFilters();
}

export function initFortAwesome(vue) {
  library.add(
    faSort,
    faEye,
    faSync,
    faBan,
    faTrash,
    faArrowLeft,
    faSave,
    faPlus,
    faPencilAlt,
    faUser,
    faTachometerAlt,
    faHeart,
    faList,
    faTasks,
    faBook,
    faHdd,
    faLock,
    faSignInAlt,
    faSignOutAlt,
    faWrench,
    faThList,
    faUserPlus,
    faAsterisk,
    faFlag,
    faBell,
    faHome,
    faRoad,
    faCloud,
    faTimesCircle,
    faSearch,
    faBars,
    faTimes
  );
}

export function initI18N(vue) {
  vue.use(VueI18n);
  return new VueI18n({
    dateTimeFormats,
    silentTranslationWarn: true,
    formatter: new JhiFormatter()
  });
}

export function initVueXStore(vue) {
  vue.use(Vuex);
  return new Vuex.Store({
    modules: {
      accountStore,
      alertStore,
      translationStore
    }
  });
}
