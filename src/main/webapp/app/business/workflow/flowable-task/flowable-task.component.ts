import { mixins } from 'vue-class-component';
import { Component, Inject, Prop, Ref } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICommonTable, CommonTable } from '@/shared/model/modelConfig/common-table.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import JhiDataUtils from '@/shared/data/data-utils.service';

import FlowableTaskService from './flowable-task.service';
import { IUser } from '@/shared/model/user.model';
import UserService from '@/shared/service/user.service';
import { IBusinessType } from '@/shared/model/company/business-type.model';
import BusinessTypeService from '@/business/company/business-type/business-type.service';
import { forkJoin } from 'rxjs';
import ProcessDefinitionService from '@/business/workflow/process-definition/process-definition.service';
import { IFlowableTask } from '@/shared/model/workflow/flowable-task.model';
import FlowableTaskUpdateComponent from '@/business/workflow/flowable-task/flowable-task-update.vue';
import kebabCase from 'lodash.kebabcase';
import ProcessInstanceService from '@/business/workflow/process-instance/process-instance.service';
import ProcessFormConfigService from '@/business/workflow/process-form-config/process-form-config.service';
import { ProcessFormConfig } from '@/shared/model/workflow/process-form-config.model';

@Component({
  components: {
    'jhi-flowable-task-update': FlowableTaskUpdateComponent
  }
})
export default class FlowableTaskComponent extends mixins(JhiDataUtils, Vue2Filters.mixin, AlertMixin) {
  @Inject('flowableTaskService') private flowableTaskService: () => FlowableTaskService;
  @Inject('userService') private userService: () => UserService;
  @Inject('businessTypeService') private businessTypeService: () => BusinessTypeService;
  @Inject('processDefinitionService') private processDefinitionService: () => ProcessDefinitionService;
  @Inject('processInstanceService') private processInstanceService: () => ProcessInstanceService;
  @Inject('processFormConfigService') private processFormConfigService: () => ProcessFormConfigService;
  @Ref() public searchInput;
  @Ref('xGrid') public xGrid;
  public processInstanceId: string = null;
  public taskId: string = null;
  public processEntityId: number = null;
  public processDefinitionKey = null;
  public updateFormTag = null;
  public formJsonData = null;
  public showTaskUpdateFrom = false;
  public xGridData = [];
  public xGridColumns = [];
  public xGridTableToolbars = {
    perfect: true,
    custom: true,
    slots: {
      buttons: 'toolbar_buttons'
    }
  };
  public xGridSelectRecords = [];
  private loading: boolean = false;
  public relationshipsData: any = {};
  public xGridPagerConfig = {
    layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
    pageSize: 15,
    pageSizes: [5, 10, 15, 20, 30, 50],
    total: 0,
    pagerCount: 5,
    currentPage: 1
  };

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

  public created(): void {
    this.initRelationships();
  }

  public mounted(): void {
    this.loadAll();
  }

  public clear(): void {
    this.xGridPagerConfig.currentPage = 1;
    this.loadAll();
  }
  public loadAll(): void {
    this.loading = true;

    const paginationQuery = {
      page: this.xGridPagerConfig.currentPage - 1,
      size: this.xGridPagerConfig.pageSize,
      sort: this.sort(),
      filter: this.getFilter()
    };
    this.flowableTaskService()
      .retrieve(paginationQuery)
      .subscribe(
        res => {
          this.xGridData = res.data.data;
          this.xGridPagerConfig.total = res.data.total;
          this.loading = false;
        },
        err => {
          this.$message.error(err.message);
          this.loading = false;
        }
      );
    this.xGridData = [];
  }

  public prepareRemove(instance: ICommonTable): void {
    this.removeId = instance.id;
  }

  public removeById(removeId: string): void {
    this.flowableTaskService()
      .delete(removeId, true)
      .subscribe(
        res => {
          this.$message.success('删除任务成功。');
        },
        error => {
          this.$message.error('删除任务失败！');
          console.log(error.message);
        }
      );
  }

  public handleTask(row: IFlowableTask): void {
    forkJoin([
      this.processDefinitionService().find(row.processDefinitionId),
      this.processInstanceService().find(row.processInstanceId),
      this.processFormConfigService().retrieve({ 'taskNodeId.equals': row.taskDefinitionKey })
    ]).subscribe(([definitionRes, instanceRes, formConfigRes]) => {
      this.processDefinitionKey = definitionRes.data.key;
      this.updateFormTag = 'jhi-' + kebabCase(this.processDefinitionKey) + '-update';
      if (instanceRes && instanceRes.data) {
        const businessKeyArray = instanceRes.data.businessKey.split('_');
        if (businessKeyArray.length === 2) {
          this.processEntityId = Number(businessKeyArray[1]);
        }
      }
      if (formConfigRes && formConfigRes.data && formConfigRes.data.length > 0) {
        const processFormConfig: ProcessFormConfig = formConfigRes.data[0];
        this.formJsonData = processFormConfig.formData;
      }
      this.taskId = row.id;
      this.processInstanceId = row.processInstanceId;
      this.showTaskUpdateFrom = true;
    });
  }
  public sort(): Array<any> {
    const result = [];
    Object.keys(this.mapOfSort).forEach(key => {
      if (this.mapOfSort[key] && this.mapOfSort[key] !== false) {
        if (this.mapOfSort[key] === 'ascend') {
          result.push(key + ',asc');
        } else if (this.mapOfSort[key] === 'descend') {
          result.push(key + ',desc');
        }
      }
    });
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.loadAll();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
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

  public closeTaskUpdate() {
    this.showTaskUpdateFrom = false;
  }

  getCommonTableData() {
    this.xGridColumns = [
      { field: 'assignee', title: '办理人' },
      { field: 'createTime', title: '创建时间' },
      { field: 'name', title: '名称' },
      { field: 'id', title: 'ID' },
      { field: 'owner', title: '所有者' },
      { field: 'priority', title: '优先级' },
      {
        field: 'suspended',
        title: '挂起',
        cellRender: {
          name: 'ASwitch',
          props: {
            disabled: 'disabled'
          }
        }
      },
      {
        title: '操作',
        field: 'operation',
        fixed: 'right',
        width: 120,
        slots: { default: 'recordAction' }
      }
    ];
  }

  filterByColumn(fieldName: string, filterValue: string[]) {
    this.mapOfFilter[fieldName].value = filterValue;
    this.loadAll();
  }
  getFilter() {
    const result: { [key: string]: any } = {};
    if (this.searchValue) {
      result['jhiCommonSearchKeywords'] = this.searchValue;
      return result;
    }
    Object.keys(this.mapOfFilter).forEach(key => {
      const filterResult = [];
      if (this.mapOfFilter[key].type === 'Enum') {
        this.mapOfFilter[key].value.forEach(value => {
          filterResult.push(value);
        });
        result[key + '.in'] = filterResult;
      }
      if (['one-to-one', 'many-to-many', 'many-to-one', 'one-to-many'].includes(this.mapOfFilter[key].type)) {
        this.mapOfFilter[key].value.forEach(value => {
          filterResult.push(value);
        });
        result[key + 'Id.in'] = filterResult;
      }
    });
    return result;
  }

  // 新增Article 并设置为可编辑状态
  newCommonTable() {
    const commonTable = new CommonTable();
    commonTable.id = -1;
    this.commonTables.push(commonTable);
    this.editStatus[commonTable.id].edit = true;
  }

  emitEmpty() {
    this.searchInput.focus();
    this.searchValue = '';
  }

  public newEntity(): void {
    this.$router.push({ path: 'design', append: true });
  }

  public copyEntity() {
    if (this.xGridSelectRecords.length === 1) {
      this.$router.push({ path: 'new/' + this.xGridSelectRecords[0].id, append: true });
    }
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
  public onSelect(selectedKeys, info) {
    console.log('onSelect', info);
    console.log('record', info.node.record);
    const filterData = info.node.dataRef;
    if (filterData.type === 'filterGroup') {
      this.mapOfFilter[info.node.dataRef.key].value = [];
    } else if (filterData.type === 'filterItem') {
      this.mapOfFilter[info.node.dataRef.filterName].value = [info.node.dataRef.filterValue];
    }
    this.loadAll();
    this.selectedKeys = selectedKeys;
  }

  public editClosedEvent({ row, column }) {
    let field = column.property;
    let cellValue = row[field];
    // 判断单元格值是否被修改
    if (this.xGrid.isUpdateByRow(row, field)) {
      const entity = { id: row.id };
      entity[field] = cellValue;
      const options = { 'id.equals': row.id };
    }
  }

  public xGridCheckboxChangeEvent() {
    this.xGridSelectRecords = this.xGrid.getCheckboxRecords();
  }
  public changeEvent(e) {
    console.log(e);
  }

  public initRelationships(): void {
    this.loading = true;
    forkJoin([this.userService().retrieve(), this.businessTypeService().retrieve()]).subscribe(
      ([users, businessTypes]) => {
        this.relationshipsData['users'] = users.data;
        this.relationshipsData['businessTypes'] = businessTypes.data;

        const listOfFiltercreator = users.data.slice(0, users.data.length > 8 ? 7 : users.data.length - 1);
        this.mapOfFilter.creator = { list: listOfFiltercreator, value: [], type: 'many-to-one' };
        const listOfFilterbusinessType = businessTypes.data.slice(0, businessTypes.data.length > 8 ? 7 : businessTypes.data.length - 1);
        this.mapOfFilter.businessType = { list: listOfFilterbusinessType, value: [], type: 'many-to-one' };
        this.getCommonTableData();
      },
      error => {
        this.loading = false;
        this.$message.error({
          content: `数据获取失败`,
          onClose: () => {}
        });
      }
    );
  }

  public xGridPageChange({ currentPage, pageSize }) {
    this.xGridPagerConfig.currentPage = currentPage;
    this.xGridPagerConfig.pageSize = pageSize;
    this.loadAll();
  }

  public switchFilterTree() {
    this.filterTreeSpan = this.filterTreeSpan > 0 ? 0 : 6;
  }
}
