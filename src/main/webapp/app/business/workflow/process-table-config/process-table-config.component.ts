import { mixins } from 'vue-class-component';
import { Component, Inject, Prop, Ref } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProcessTableConfig, ProcessTableConfig } from '@/shared/model/workflow/process-table-config.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ProcessTableConfigService from './process-table-config.service';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { IUser } from '@/shared/model/user.model';
import UserService from '@/shared/service/user.service';
import { AxiosResponse } from 'axios';
import { xGenerateFilterTree, xGenerateTableColumns } from '@/utils/entity-list-utils';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { forkJoin } from 'rxjs';
import DeploymentService from '@/business/workflow/deployment/deployment.service';

@Component
export default class ProcessTableConfigComponent extends mixins(JhiDataUtils, Vue2Filters.mixin, AlertMixin) {
  @Inject('processTableConfigService') private processTableConfigService: () => ProcessTableConfigService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  @Inject('userService') private userService: () => UserService;
  @Inject('deploymentService') private deploymentService: () => DeploymentService;
  @Ref() public searchInput;
  @Ref('xGrid') public xGrid;
  public xGridData = [];
  public xGridColumns = [];
  public xGridTableToolbars = {
    perfect: true,
    custom: true,
    slots: {
      buttons: 'toolbar_buttons'
    }
  };
  public xGridTreeConfig: boolean | Object = false;
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
  public omitFields = ['id', 'processBpmnData'];
  public processTableConfigs: IProcessTableConfig[] = [];
  public mapOfFilter: { [key: string]: any } = {
    commonTable: { list: [], value: [], type: 'many-to-one' },
    creator: { list: [], value: [], type: 'many-to-one' }
  };
  public editStatus: { [key: string]: any } = {};
  public isFetching = false;
  public searchValue = '';
  commontables: ICommonTable[];
  usersNzTreeNodes: any[]; // todo 有时可能多余。
  users: IUser[];

  get pagination() {
    return {
      pageSize: this.itemsPerPage,
      current: this.page,
      total: this.totalItems,
      showTotal: (total, range) => `第${range[0]}-${range[1]}条，共${total}条`
    };
  }
  public created(): void {
    this.initRelationships();
  }

  public mounted(): void {
    this.loadAll();
  }

  public clear(): void {
    this.pagination.current = 1;
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
    this.processTableConfigService()
      .retrieve(paginationQuery)
      .subscribe(
        res => {
          this.xGridData = res.data;
          this.xGridPagerConfig.total = Number(res.headers['x-total-count']);
          this.loading = false;
        },
        err => {
          this.$message.error(err.message);
          this.loading = false;
        }
      );
  }

  public prepareRemove(instance: IProcessTableConfig): void {
    this.removeId = instance.id;
  }

  public removeById(removeId: number): void {
    this.processTableConfigService()
      .delete(removeId)
      .subscribe((res: AxiosResponse) => {
        const message = this.$t('testnew11App.workflowProcessTableConfig.deleted', { param: this.removeId }).toString();
        this.$message.success(message);
        this.loadAll();
      });
  }
  public removeByIds(ids: string[]) {
    this.processTableConfigService()
      .deleteByIds(ids)
      .subscribe(
        (res: AxiosResponse) => {
          this.$message.success('删除成功');
          this.loadAll();
        },
        err => this.$message.error(err.message)
      );
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

  public editEntity(row: IProcessTableConfig): void {
    this.$router.push({ path: row.id + '/edit', append: true });
  }

  public designProcess(row: IProcessTableConfig): void {
    this.$router.push({ path: '../process-design/' + row.processDefinitionKey + '/edit', append: true });
  }

  public deployProcess(row: IProcessTableConfig): void {
    if (row.processBpmnData && row.processBpmnData.length > 0) {
      const filename = row.processDefinitionKey + '.bpmn';
      const fd = new FormData();
      fd.append('deployment-name', filename);
      const blob = new Blob([row.processBpmnData]);
      fd.append('data', blob, filename);
      this.deploymentService()
        .deployProcess(fd)
        .subscribe(
          res => {
            this.$message.success('部署完成');
            // 更新状态
            const updateProcessTableConfig: IProcessTableConfig = {};
            updateProcessTableConfig.id = row.id;
            updateProcessTableConfig.deploied = true;
            this.processTableConfigService()
              .updateBySpecifiedField(updateProcessTableConfig, 'deploied')
              .subscribe(res => {
                console.log('状态更新完成。');
                this.loadAll();
              });
          },
          error => {
            this.$message.error(error.message);
          }
        );
    } else {
      this.$message.warning('请先设计流程再进行发布!');
    }
  }

  getCommonTableData() {
    this.commonTableService()
      .findByEntityName('ProcessTableConfig')
      .subscribe(res => {
        this.xGridColumns = xGenerateTableColumns(res.data, this.relationshipsData, this.mapOfSort, this.mapOfFilter, this.changeEvent);
        this.treeFilterData = xGenerateFilterTree(res.data, this.relationshipsData);
        if (res.data.treeTable) {
          this.xGridTreeConfig = { children: 'children', indent: 20, line: false, expandAll: false, accordion: false, trigger: 'default' };
        }
        if (this.treeFilterData && this.treeFilterData.length > 0) {
          this.filterTreeSpan = 6;
        }
        this.loading = false;
      });
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
  // 设置某行可编辑
  startEdit(processTableConfig): void {
    const newData = [...this.processTableConfigs];
    const target = newData.filter(item => processTableConfig.id === item.id)[0];
    if (target) {
      target['editableLine'] = true;
      this.processTableConfigs = newData;
    }
  }

  saveEdit(processTableConfig: IProcessTableConfig) {
    if (processTableConfig.id) {
      if (this.omitFields.length > 1) {
        this.processTableConfigService()
          .updateBySpecifiedFields(processTableConfig, this.omitFields)
          .subscribe(param => {
            const message = this.$t('testnew11App.workflowProcessTableConfig.updated', { param: param.data.id }).toString();
            this.$message.success(message);
            this.loadAll();
          });
      } else {
        this.processTableConfigService()
          .update(processTableConfig)
          .subscribe(param => {
            const message = this.$t('testnew11App.workflowProcessTableConfig.updated', { param: param.data.id }).toString();
            this.$message.success(message);
            this.loadAll();
          });
      }
    } else {
      this.processTableConfigService()
        .create(processTableConfig)
        .subscribe(param => {
          const message = this.$t('testnew11App.workflowProcessTableConfig.created', { param: param.data.id }).toString();
          this.$message.success(message);
          this.loadAll();
        });
    }
  }
  cancelEdit(id: string): void {
    this.loadAll();
  }
  // 新增Article 并设置为可编辑状态
  newProcessTableConfig() {
    const processTableConfig = new ProcessTableConfig();
    processTableConfig.id = -1;
    this.processTableConfigs.push(processTableConfig);
    this.editStatus[processTableConfig.id].edit = true;
  }

  emitEmpty() {
    this.searchInput.focus();
    this.searchValue = '';
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

  dataTableRowClick(e) {
    console.log(e, 'rowclick');
  }
  dataTableRowDoubleClick(e) {
    console.log(e, 'rowdblclick');
  }
  dataTableBeforeSelect(rows) {
    console.log(rows, 'beforeselect');
  }
  dataTableAfterSelect(ids, rows) {
    console.log('afterselect……');
    console.log(ids, 'ids');
    console.log(rows, 'rows');
  }
  dataTableAfterLoad(data) {
    console.log(data, 'afterLoad……');
  }
  public editClosedEvent({ row, column }) {
    let field = column.property;
    let cellValue = row[field];
    // 判断单元格值是否被修改
    if (this.xGrid.isUpdateByRow(row, field)) {
      const entity = { id: row.id };
      entity[field] = cellValue;
      this.processTableConfigService()
        .updateBySpecifiedField(entity, field)
        .subscribe(
          res => {
            if (res.status === 200) {
              this.$message.success({
                content: `信息更新成功。 ${field}=${cellValue}`,
                duration: 1
              });
              this.xGrid.reloadRow(row, null, field);
            } else {
              this.$message.warning({
                content: `信息保存可能存在问题！ ${field}=${cellValue}`,
                duration: 5
              });
              this.xGrid.reloadRow(row, null, field);
            }
          },
          error => {
            this.$message.error({
              content: `信息保存可能存在问题！ ${field}=${cellValue}`,
              onClose: () => {}
            });
          }
        );
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
    forkJoin([this.commonTableService().retrieve(), this.userService().retrieve()]).subscribe(
      ([commonTables, users]) => {
        this.relationshipsData['commonTables'] = commonTables.data;
        this.relationshipsData['users'] = users.data;
        const listOfFiltercommonTable = commonTables.data.slice(0, commonTables.data.length > 8 ? 7 : commonTables.data.length - 1);
        this.mapOfFilter.commonTable = { list: listOfFiltercommonTable, value: [], type: 'many-to-one' };
        const listOfFiltercreator = users.data.slice(0, users.data.length > 8 ? 7 : users.data.length - 1);
        this.mapOfFilter.creator = { list: listOfFiltercreator, value: [], type: 'many-to-one' };
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
}
