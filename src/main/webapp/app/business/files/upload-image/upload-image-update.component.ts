import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import moment from 'moment';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import UserService from '@/shared/service/user.service';

import AlertService from '@/shared/alert/alert.service';
import { IUploadImage, UploadImage } from '@/shared/model/files/upload-image.model';
import UploadImageService from './upload-image.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  uploadImage: {
    fullName: {},
    name: {},
    ext: {},
    type: {},
    url: {},
    path: {},
    folder: {},
    entityName: {},
    createAt: {},
    fileSize: {},
    smartUrl: {},
    mediumUrl: {},
    referenceCount: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class UploadImageUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('uploadImageService') private uploadImageService: () => UploadImageService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public uploadImage: IUploadImage = new UploadImage();

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
  public uploadImageId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.uploadImageId) {
      this.uploadImageId = this.$route.params.uploadImageId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.uploadImage, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.uploadImage.id) {
      this.uploadImageService()
        .update(this.uploadImage)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.filesUploadImage.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.uploadImageService()
        .create(this.uploadImage)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.filesUploadImage.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveUploadImage(uploadImageId): void {
    this.uploadImageService()
      .find(uploadImageId)
      .subscribe(res => {
        this.uploadImage = res.data;
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
    if (this.uploadImageId) {
      this.retrieveUploadImage(this.uploadImageId);
    } else {
      this.getFormData();
    }
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
          this.uploadImage = getDataByFormField(this.formJsonData.list, this.uploadImage);
          this.$nextTick(() => {
            this.updateForm.setData(this.uploadImage); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('UploadImage')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.uploadImage = getDataByFormField(this.formJsonData.list, this.uploadImage);
          this.$nextTick(() => {
            this.updateForm.setData(this.uploadImage); // loadsh的pick方法
          });
        });
    }
  }
}
