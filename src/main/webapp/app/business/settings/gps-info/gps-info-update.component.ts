import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';
import AlertService from '@/shared/alert/alert.service';
import { IGpsInfo, GpsInfo } from '@/shared/model/settings/gps-info.model';
import GpsInfoService from './gps-info.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  gpsInfo: {
    type: {},
    latitude: {},
    longitude: {},
    address: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class GpsInfoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('gpsInfoService') private gpsInfoService: () => GpsInfoService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public gpsInfo: IGpsInfo = new GpsInfo();
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
  public gpsInfoId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.gpsInfoId) {
      this.gpsInfoId = this.$route.params.gpsInfoId;
    }
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.gpsInfo, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.gpsInfo.id) {
      this.gpsInfoService()
        .update(this.gpsInfo)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.settingsGpsInfo.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.gpsInfoService()
        .create(this.gpsInfo)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.settingsGpsInfo.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveGpsInfo(gpsInfoId): void {
    this.gpsInfoService()
      .find(gpsInfoId)
      .subscribe(res => {
        this.gpsInfo = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
  public getData() {
    if (this.gpsInfoId) {
      this.retrieveGpsInfo(this.gpsInfoId);
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
          this.gpsInfo = getDataByFormField(this.formJsonData.list, this.gpsInfo);
          this.$nextTick(() => {
            this.updateForm.setData(this.gpsInfo); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('GpsInfo')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.gpsInfo = getDataByFormField(this.formJsonData.list, this.gpsInfo);
          this.$nextTick(() => {
            this.updateForm.setData(this.gpsInfo); // loadsh的pick方法
          });
        });
    }
  }
}
