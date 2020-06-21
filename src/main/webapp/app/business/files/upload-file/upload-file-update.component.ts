import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import moment from 'moment';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import UserService from '@/shared/service/user.service';

import AlertService from '@/shared/alert/alert.service';
import { IUploadFile, UploadFile } from '@/shared/model/files/upload-file.model';
import UploadFileService from './upload-file.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  uploadFile: {
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
    referenceCount: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class UploadFileUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('uploadFileService') private uploadFileService: () => UploadFileService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public uploadFile: IUploadFile = new UploadFile();

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
  public uploadFileId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.uploadFileId) {
      this.uploadFileId = this.$route.params.uploadFileId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.uploadFile, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.uploadFile.id) {
      this.uploadFileService()
        .update(this.uploadFile)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.filesUploadFile.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.uploadFileService()
        .create(this.uploadFile)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.filesUploadFile.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveUploadFile(uploadFileId): void {
    this.uploadFileService()
      .find(uploadFileId)
      .subscribe(res => {
        this.uploadFile = res.data;
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
    if (this.uploadFileId) {
      this.retrieveUploadFile(this.uploadFileId);
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
          this.uploadFile = getDataByFormField(this.formJsonData.list, this.uploadFile);
          this.$nextTick(() => {
            this.updateForm.setData(this.uploadFile); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('UploadFile')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.uploadFile = getDataByFormField(this.formJsonData.list, this.uploadFile);
          this.$nextTick(() => {
            this.updateForm.setData(this.uploadFile); // loadsh的pick方法
          });
        });
    }
  }
}
