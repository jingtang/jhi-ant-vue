import { Component, Vue, Inject, Prop, Watch, Ref } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdministrativeDivision, AdministrativeDivision } from '@/shared/model/settings/administrative-division.model';
import AdministrativeDivisionService from './administrative-division.service';
import { forkJoin } from 'rxjs';
import { generateDataForDesigner, getDataByFormField } from '@/utils/entity-form-utils';

const validations: any = {
  administrativeDivision: {
    name: {},
    areaCode: {},
    cityCode: {},
    mergerName: {},
    shortName: {},
    zipCode: {},
    level: {},
    lng: {},
    lat: {}
  }
};

@Component({
  validations,
  components: {}
})
export default class AdministrativeDivisionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('administrativeDivisionService') private administrativeDivisionService: () => AdministrativeDivisionService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  public administrativeDivision: IAdministrativeDivision = new AdministrativeDivision();

  public administrativeDivisions: IAdministrativeDivision[] = [];
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
  public administrativeDivisionId = null;

  created(): void {
    // 判断是否从路由中获得实体id
    if (this.$route.params.administrativeDivisionId) {
      this.administrativeDivisionId = this.$route.params.administrativeDivisionId;
    }
    this.initRelationships();
  }

  public mounted(): void {}

  public getFormValue() {
    this.updateForm
      .getData()
      .then(values => {
        Object.assign(this.administrativeDivision, values);
      })
      .catch(() => {
        this.$message.error('数据获取失败！');
        console.log('验证未通过，获取失败');
      });
  }

  public save(): void {
    this.getFormValue();
    this.isSaving = true;
    if (this.administrativeDivision.id) {
      this.administrativeDivisionService()
        .update(this.administrativeDivision)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.settingsAdministrativeDivision.updated', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'info');
          this.$router.go(-1);
        });
    } else {
      this.administrativeDivisionService()
        .create(this.administrativeDivision)
        .subscribe(param => {
          this.isSaving = false;
          const message = this.$t('jhiAntVueApp.settingsAdministrativeDivision.created', { param: param.data.id }).toString();
          this.alertService().showAlert(message, 'success');
          this.$router.go(-1);
        });
    }
  }

  public retrieveAdministrativeDivision(administrativeDivisionId): void {
    this.administrativeDivisionService()
      .find(administrativeDivisionId)
      .subscribe(res => {
        this.administrativeDivision = res.data;
        this.getFormData();
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.administrativeDivisionService().tree()]).subscribe(
      ([administrativeDivisionsRes]) => {
        this.relationshipsData['administrativeDivisions'] = administrativeDivisionsRes.data;
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
    if (this.administrativeDivisionId) {
      this.retrieveAdministrativeDivision(this.administrativeDivisionId);
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
          this.administrativeDivision = getDataByFormField(this.formJsonData.list, this.administrativeDivision);
          this.$nextTick(() => {
            this.updateForm.setData(this.administrativeDivision); // loadsh的pick方法
          });
        });
    } else {
      this.commonTableService()
        .findByEntityName('AdministrativeDivision')
        .subscribe(res => {
          const commonTableData = res.data;
          if (commonTableData.formConfig && commonTableData.formConfig.length > 0) {
            this.formJsonData = JSON.parse(commonTableData.formConfig);
          } else {
            this.formJsonData.list = generateDataForDesigner(commonTableData);
          }
          this.administrativeDivision = getDataByFormField(this.formJsonData.list, this.administrativeDivision);
          this.$nextTick(() => {
            this.updateForm.setData(this.administrativeDivision); // loadsh的pick方法
          });
        });
    }
  }
}
