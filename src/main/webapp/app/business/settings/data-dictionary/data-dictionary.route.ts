import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const dataDictionaryRoute: RouteConfig = {
  path: 'data-dictionary',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '数据字典' },
  children: [
    {
      path: '',
      name: 'data-dictionary',
      component: () => import('./data-dictionary.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'data-dictionary-new',
      component: () => import('./data-dictionary-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':dataDictionaryId/edit',
      name: 'data-dictionary-edit',
      component: () => import('./data-dictionary-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':dataDictionaryId/detail',
      name: 'data-dictionary-detail',
      component: () => import('./data-dictionary-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
