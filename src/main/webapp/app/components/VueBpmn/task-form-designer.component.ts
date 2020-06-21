import { mixins } from 'vue-class-component';
import { Component, Inject, Prop, Ref } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICommonTable, CommonTable } from '@/shared/model/modelConfig/common-table.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import JhiDataUtils from '@/shared/data/data-utils.service';

import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { IUser } from '@/shared/model/user.model';
import UserService from '@/shared/service/user.service';
import { IBusinessType } from '@/shared/model/company/business-type.model';
import BusinessTypeService from '@/business/company/business-type/business-type.service';
import ProcessFormConfigService from '@/business/workflow/process-form-config/process-form-config.service';
import { generateDataForDesigner } from '@/utils/entity-form-utils';
import { Getter } from 'vuex-class';
import { ProcessFormConfig } from '@/shared/model/workflow/process-form-config.model';

@Component
export default class TaskFormDesignerComponent extends mixins(JhiDataUtils, Vue2Filters.mixin, AlertMixin) {
  @Inject('processFormConfigService') private processFormConfigService: () => ProcessFormConfigService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  @Getter('processDefinitionKey') processDefinitionKey: string;
  @Getter('commonTableId') commonTableId: number;

  @Prop({ type: String, required: false, default: '' })
  public nodeId: string;

  @Prop({ type: String, required: false, default: '' })
  public formKey: string;

  public processFormConfig = new ProcessFormConfig();
  public designerData = {
    list: [],
    config: {
      layout: 'horizontal',
      labelCol: { span: 4 },
      wrapperCol: { span: 18 },
      hideRequiredMark: false,
      customStyle: ''
    }
  };

  @Ref('designer') designer: any;

  @Inject('userService') private userService: () => UserService;
  @Inject('businessTypeService') private businessTypeService: () => BusinessTypeService;

  private loading: boolean = false;
  public relationshipsData: any = {};

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public filterTreeSpan = 0;
  @Prop(Object) otherPresetOrder: { [key: string]: any };
  public treeFilterData = [];
  public expandedKeys = [];
  public autoExpandParent = true;
  public checkedKeys = [];
  public selectedKeys = [];
  public mapOfSort: { [key: string]: any } = {};
  public reverse = false;
  public totalItems = 0;
  public omitFields = ['id', 'listConfig', 'formConfig'];
  public commonTables: ICommonTable[] = [];
  public mapOfFilter: { [key: string]: any } = {
    commonTableFields: { list: [], value: [], type: 'one-to-many' },
    relationships: { list: [], value: [], type: 'one-to-many' },
    creator: { list: [], value: [], type: 'many-to-one' },
    businessType: { list: [], value: [], type: 'many-to-one' }
  };
  public editStatus: { [key: string]: any } = {};
  public isFetching = false;
  public searchValue = '';
  usersNzTreeNodes: any[]; // todo 有时可能多余。
  users: IUser[];
  businesstypesNzTreeNodes: any[]; // todo 有时可能多余。
  businesstypes: IBusinessType[];

  get pagination() {
    return {
      pageSize: this.itemsPerPage,
      current: this.page,
      total: this.totalItems,
      showTotal: (total, range) => `第${range[0]}-${range[1]}条，共${total}条`
    };
  }
  public created(): void {
    this.getDesignerData();
  }

  public mounted(): void {}

  public prepareRemove(instance: ICommonTable): void {
    this.removeId = instance.id;
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public editEntity(row: ICommonTable): void {
    this.$router.push({ path: row.id + '/edit', append: true });
  }

  public designForm(id: number): void {
    this.$router.push({ path: id + '/designer', append: true });
  }

  public newEntity(): void {
    this.$router.push({ path: 'new', append: true });
  }

  public onExpand(expandedKeys) {
    console.log('onExpand', expandedKeys);
    // if not set autoExpandParent to false, if children expanded, parent can not collapse.
    // or, you can remove all expanded children keys.
    this.expandedKeys = expandedKeys;
    this.autoExpandParent = false;
  }
  public onCheck(checkedKeys) {
    console.log('onCheck', checkedKeys);
    this.checkedKeys = checkedKeys;
  }

  public changeEvent(e) {
    console.log(e);
  }

  public saveToServer(formConfig) {
    this.processFormConfig.formData = formConfig;
    if (!this.processFormConfig.id) {
      this.processFormConfigService()
        .create(this.processFormConfig)
        .subscribe(
          res => {
            this.processFormConfig = res.data;
            this.$message.success('保存成功。');
          },
          error => {
            this.$message.success('保存失败!');
            console.log(error.message);
          }
        );
    } else {
      this.processFormConfigService()
        .update(this.processFormConfig)
        .subscribe(
          res => {
            this.processFormConfig = res.data;
            this.$message.success('更新成功。');
          },
          error => {
            this.$message.success('更新失败!');
            console.log(error.message);
          }
        );
    }
  }

  /**
   * 从数据库重新生成JsonData数据并导入。
   */
  public rebuildFromServer() {
    // todo 如果存在processFormConfig.id 则从服务器里取，如果没有得重新生成。
    this.commonTableService()
      .find(this.commonTableId)
      .subscribe(
        res => {
          this.designerData.list = generateDataForDesigner(res.data);
          this.importData(this.designerData);
        },
        error => {
          this.$message.error({
            content: `数据获取失败。信息：${error.message}`,
            onClose: () => {}
          });
        }
      );
  }

  public getDesignerData() {
    this.processFormConfigService()
      .retrieve({ 'taskNodeId.equals': this.nodeId })
      .subscribe(
        res => {
          if (res.data && res.data.length > 0 && res.data[0].formData && res.data[0].formData.length > 0) {
            this.processFormConfig = res.data[0];
            const formData = JSON.parse(res.data[0].formData);
            this.importData(formData);
          } else {
            this.commonTableService()
              .find(this.commonTableId)
              .subscribe(res => {
                this.designerData.list = generateDataForDesigner(res.data);
                this.importData(this.designerData);
                this.processFormConfig.commonTableId = this.commonTableId;
                this.processFormConfig.processDefinitionKey = this.processDefinitionKey;
                this.processFormConfig.taskNodeId = this.nodeId;
              });
          }
        },
        error => {
          this.$message.error({
            content: `数据获取失败。信息：${error.message}`,
            onClose: () => {}
          });
        }
      );
  }

  public importData(formData) {
    this.designer.handleSetData(formData);
  }
}
