import { RouteConfig } from 'vue-router';
import { PageView } from '@/layouts';
import { uploadFileRoute } from '@/business/files/upload-file/upload-file.route';
import { uploadImageRoute } from '@/business/files/upload-image/upload-image.route';
// jhipster-needle-add-entity-to-client-root-folder-router-import - JHipster will import entities to the client root folder router here

export const filesRoute: RouteConfig = {
  path: 'files',
  component: PageView,
  meta: { authorities: ['ROLE_USER'], title: '文件管理' },
  children: [
    uploadFileRoute,
    uploadImageRoute
    // jhipster-needle-add-entity-to-client-root-folder-router - JHipster will add entities to the client root folder router here
  ]
};
