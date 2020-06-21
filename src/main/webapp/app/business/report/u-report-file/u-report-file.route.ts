import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const uReportFileRoute: RouteConfig = {
  path: 'u-report-file',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '报表存储' },
  children: [
    {
      path: '',
      name: 'u-report-file',
      component: () => import('./u-report-file.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'u-report-file-new',
      component: () => import('./u-report-file-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':uReportFileId/edit',
      name: 'u-report-file-edit',
      component: () => import('./u-report-file-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':uReportFileId/detail',
      name: 'u-report-file-detail',
      component: () => import('./u-report-file-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
