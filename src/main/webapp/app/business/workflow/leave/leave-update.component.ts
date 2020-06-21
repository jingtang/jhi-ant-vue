import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import moment from 'moment';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import UploadImageService from '../../files//upload-image/upload-image.service';
import { IUploadImage } from '@/shared/model/files/upload-image.model';

import UserService from '@/shared/service/user.service';

import AlertService from '@/shared/alert/alert.service';
import { ILeave, Leave } from '@/shared/model/workflow/leave.model';
import LeaveService from './leave.service';
import { UPLOAD_IMAGE_URL } from '@/constants';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  leave: {
    createTime: {},
    name: {},
    days: {},
    startTime: {},
    endTime: {},
    reason: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class LeaveUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('leaveService') private leaveService: () => LeaveService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public leave: ILeave = new Leave();

  @Inject('uploadImageService') private uploadImageService: () => UploadImageService;

  public uploadImages: IUploadImage[] = [];
  public imagesFileList: any[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public loading = false;
  @Ref('updateForm') readonly updateForm: any;
  public formJsonData = {
    list: [],
    config: {
      layout: 'horizontal',
      labelCol: { span: 4 },
      wrapperCol: { span: 18 },
      hideRequiredMark: false,
      customStyle: ''
    }
  };
  public relationshipsData: any = {};
  public dataFormContent = [];
  public dataContent = [];
  public leaveId = null;
  public authHeader = { Authorization: 'Bearer ' };
  public uploadImageUrl = UPLOAD_IMAGE_URL;
  public previewImage: string | undefined = '';
  public previewVisible = false;
  public showUploadList = {
    showPreviewIcon: true,
    showRemoveIcon: true,
    hidePreviewIconInNonImage: true
  };

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.leaveId) {
      this.leaveId = this.$route.params.leaveId;
    }
    this.initRelationships();
  }

  public mounted(): void {
    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    this.authHeader.Authorization = 'Bearer ' + token;
  }

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.leave, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.leave.id) {
      this.leaveService()
        .update(this.leave)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.workflowLeave.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.leaveService()
        .create(this.leave)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.workflowLeave.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveLeave(leaveId): void {
    this.leaveService()
      .find(leaveId)
      .subscribe(res => {
        this.leave = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.userService().retrieve()]).subscribe(
      ([usersRes]) => {
        this.relationshipsData['users'] = usersRes.data;
        this.getData();
      },
      error => {
        this.loading = false;
        this.$message.error({
          content: `数据获取失败`,
          onClose: () => {
            this.getData();
          }
        });
      }
    );
  }
  public getData() {
    if (this.leaveId) {
      this.retrieveLeave(this.leaveId);
    } else {
      this.getFormData();
    }
  }
  public beforeUpload(file) {
    const isJPG = file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/gif';
    if (!isJPG) {
      this.$message.error('You can only upload JPG file!');
    }
    const isLt2M = file.size / 1024 / 1024 < 4;
    if (!isLt2M) {
      this.$message.error('Image must smaller than 4MB!');
    }
    return isJPG && isLt2M;
  }
  public getBase64(img, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
  }
  private checkImageDimension(file: File): Promise<boolean> {
    return new Promise(resolve => {
      const img = new Image(); // create image
      img.src = window.URL.createObjectURL(file);
      img.onload = () => {
        const width = img.naturalWidth;
        const height = img.naturalHeight;
        window.URL.revokeObjectURL(img.src);
        resolve(width === height && width >= 3000);
      };
    });
  }
  public handlePreview(file) {
    this.previewImage = file.url || file.thumbUrl;
    this.previewVisible = true;
  }
  public handlePreviewCancel() {
    this.previewVisible = false;
  }
  public getFormData(formDataId?: number) {
    if (formDataId) {
      this.commonTableService()
        .find(formDataId)
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.leave = getDataByFormField(this.formJsonData.list, this.leave);
          this.$nextTick(() => {
            this.updateForm.setData(this.leave); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('Leave')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.leave = getDataByFormField(this.formJsonData.list, this.leave);
          this.$nextTick(() => {
            this.updateForm.setData(this.leave); // loadsh的pick方法
          });
        });
    }
  }
}
