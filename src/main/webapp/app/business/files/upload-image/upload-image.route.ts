import { RouteConfig } from 'vue-router';
import { RouteView } from '@/layouts';
export const uploadImageRoute: RouteConfig = {
  path: 'upload-image',
  component: RouteView,
  meta: { authorities: ['ROLE_USER'], title: '上传图片' },
  children: [
    {
      path: '',
      name: 'upload-image',
      component: () => import('./upload-image.vue'),
      meta: { authorities: ['ROLE_USER'], title: '列表' }
    },
    {
      path: 'new',
      name: 'upload-image-new',
      component: () => import('./upload-image-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '新建' }
    },
    {
      path: ':uploadImageId/edit',
      name: 'upload-image-edit',
      component: () => import('./upload-image-update.vue'),
      meta: { authorities: ['ROLE_USER'], title: '编辑' }
    },
    {
      path: ':uploadImageId/detail',
      name: 'upload-image-detail',
      component: () => import('./upload-image-details.vue'),
      meta: { authorities: ['ROLE_USER'], title: '查看' }
    }
  ]
};
