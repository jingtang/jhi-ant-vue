import { mixins } from 'vue-class-component';
import { Component, Inject, Prop, Ref, Watch } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICommonQueryItem, CommonQueryItem } from '@/shared/model/commonQuery/common-query-item.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CommonQueryItemService from './common-query-item.service';
import { ICommonQuery } from '@/shared/model/commonQuery/common-query.model';
import CommonQueryService from '@/business/commonQuery/common-query/common-query.service';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import CommonTableService from '@/business/modelConfig/common-table/common-table.service';
import { AxiosResponse } from 'axios';
import { getFilter, xGenerateFilterTree, xGenerateTableColumns } from '@/utils/entity-list-utils';
import CommonQueryItemUpdate from '@/business/commonQuery/common-query-item/common-query-item-update.vue';
import { forkJoin } from 'rxjs';

@Component
export default class CommonQueryItemComponent extends mixins(Vue2Filters.mixin, AlertMixin) {
  @Inject('commonQueryItemService') private commonQueryItemService: () => CommonQueryItemService;
  @Inject('commonQueryService') private commonQueryService: () => CommonQueryService;
  @Inject('commonTableService') private commonTableService: () => CommonTableService;
  @Ref('searchInput') public searchInput;
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

  public xGridFilterConfig = {
    remote: true
  };

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public filterTreeSpan = 0;

  public commonTableColumns = [];

  @Prop(Object) otherPresetOrder: { [key: string]: any };
  public treeFilterData = [];
  public expandedKeys = [];
  public autoExpandParent = true;
  public checkedKeys = [];
  public selectedKeys = [];
  public mapOfSort: { [key: string]: any } = {};
  public reverse = false;
  public totalItems = 0;
  public omitFields = ['id'];
  public commonQueryItems: ICommonQueryItem[] = [];
  public mapOfFilter: { [key: string]: any } = {
    query: { list: [], value: [], type: 'many-to-one' }
  };
  public editStatus: { [key: string]: any } = {};
  public isFetching = false;
  @Prop(Boolean) showInOther;
  public editInModal = false;
  public updateModalVisible: boolean = false;
  public commonQueryItemId = null;
  public clickCommonQueryItemId;
  public searchValue = '';
  @Prop(Number) queryId: number;
  @Prop(Number) commonTableId: number;
  commonqueries: ICommonQuery[];

  public created(): void {
    this.initRelationships();
    if (this.queryId) {
      this.mapOfFilter['query'] = { list: [], value: [this.queryId], type: 'many-to-one' };
    }
  }

  public mounted(): void {
    this.loadAll();
  }

  public clear(): void {
    this.xGridPagerConfig.currentPage = 1;
    this.loadAll();
  }
  @Watch('commonTableId')
  public loadCommonTableData() {
    if (this.commonTableId) {
      this.commonTableService()
        .find(this.commonTableId)
        .subscribe(res => {
          const commonTableData = res.data;
          commonTableData.commonTableFields.forEach(field => {
            const selectOption = { value: field.entityFieldName, label: field.title, fieldType: field.type.toString() };
            this.commonTableColumns.push(selectOption);
          });
        });
    }
  }
  @Watch('queryId')
  public loadAll(): void {
    if (this.showInOther && !this.queryId) {
      return;
    }
    if (this.queryId) {
      this.mapOfFilter['query'] = { list: [], value: [this.queryId], type: 'many-to-one' };
    }
    this.loading = true;

    const paginationQuery = {
      listModelName: 'CommonQueryItem',
      page: this.xGridPagerConfig.currentPage - 1,
      size: this.xGridPagerConfig.pageSize,
      sort: this.sort(),
      filter: getFilter(this.searchValue, this.mapOfFilter)
    };
    this.commonQueryItemService()
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

  public prepareRemove(instance: ICommonQueryItem): void {
    this.removeId = instance.id;
  }

  public removeById(removeId: number): void {
    this.commonQueryItemService()
      .delete(removeId)
      .subscribe((res: AxiosResponse) => {
        const message = this.$t('jhiAntVueApp.commonQueryCommonQueryItem.deleted', { param: this.removeId }).toString();
        this.$message.success(message);
        this.loadAll();
      });
  }
  public removeByIds(ids: string[]) {
    this.commonQueryItemService()
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
    if (this.showInOther && this.otherPresetOrder) {
      Object.keys(this.otherPresetOrder).forEach(key => {
        if (this.otherPresetOrder[key] && this.otherPresetOrder[key] !== false) {
          if (this.otherPresetOrder[key] === 'ascend') {
            result.push(key + ',asc');
          } else if (this.otherPresetOrder[key] === 'descend') {
            result.push(key + ',desc');
          }
        }
      });
      return result;
    }
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

  public editEntity(row: ICommonQueryItem): void {
    if (this.showInOther || this.editInModal) {
      this.commonQueryItemId = row.id;
      this.updateModalVisible = true;
    } else {
      this.$router.push({ path: row.id + '/edit', append: true });
    }
  }

  public changeFieldName({ row }, { value }) {
    console.log(value);
    let findColumn = this.commonTableColumns.find(column => column.value === value);
    console.log(findColumn);
    if (findColumn) {
      row.fieldType = findColumn.fieldType.toString();
    }
    console.log(row);
    console.log(this.commonTableColumns);
  }
  getCommonTableData() {
    const queryItemColumns = [];
    const checkBoxColumn = { type: 'checkbox', width: 60 };
    const idColumn = { field: 'id', minWidth: 100, params: { type: 'LONG' }, title: 'Id' };
    const prefixColumn = { field: 'prefix', minWidth: 100, params: { type: 'STRING' }, title: '前置符号', editRender: {} };
    prefixColumn.editRender = {
      name: '$select',
      options: [
        { value: '(', label: '左括号' },
        { value: ')', label: '右括号' },
        { value: 'AND', label: '并且' },
        { value: 'OR', label: '或者' }
      ]
    };
    const fieldNameColumn = { field: 'fieldName', minWidth: 100, params: { type: 'STRING' }, title: '字段名称', editRender: {} };
    fieldNameColumn.editRender = { name: '$select', options: this.commonTableColumns, events: { change: this.changeFieldName } };
    const fieldTypeColumn = { field: 'fieldType', minWidth: 100, params: { type: 'STRING' }, title: '字段类型' };
    const operatorColumn = { field: 'operator', minWidth: 100, params: { type: 'STRING' }, title: '操作符号', editRender: {} };
    operatorColumn.editRender = {
      name: '$select',
      options: [
        { value: '>', label: '大于' },
        { value: '<', label: '小于' },
        { value: '=', label: '等于' },
        { value: 'contains', label: '包含' }
      ]
    };
    const valueColumn = { field: 'value', minWidth: 100, params: { type: 'STRING' }, title: '比较值', editRender: { name: '$input' } };
    const suffixColumn = { field: 'suffix', minWidth: 100, params: { type: 'STRING' }, title: '后置符号', editRender: {} };
    suffixColumn.editRender = {
      name: '$select',
      options: [
        { value: '(', label: '左括号' },
        { value: ')', label: '右括号' },
        { value: 'AND', label: '并且' },
        { value: 'OR', label: '或者' }
      ]
    };
    const orderColumn = { field: 'order', minWidth: 100, params: { type: 'INTEGER' }, title: '顺序' };
    const actionColumn = { title: '操作', field: 'operation', fixed: 'right', width: 140, slots: { default: 'recordAction' } };
    this.xGridColumns = [];
    this.xGridColumns.push(checkBoxColumn);
    this.xGridColumns.push(idColumn);
    this.xGridColumns.push(prefixColumn);
    this.xGridColumns.push(fieldNameColumn);
    this.xGridColumns.push(fieldTypeColumn);
    this.xGridColumns.push(operatorColumn);
    this.xGridColumns.push(valueColumn);
    this.xGridColumns.push(suffixColumn);
    this.xGridColumns.push(orderColumn);
    this.xGridColumns.push(actionColumn);
    this.loading = false;
    /*this.commonTableService()
      .findByEntityName('CommonQueryItem')
      .subscribe(res => {
        this.editInModal = res.data.editInModal;
        this.xGridColumns = xGenerateTableColumns(res.data, this.relationshipsData, this.mapOfSort, this.mapOfFilter, this.changeEvent);
        console.log(this.xGridColumns);
        this.treeFilterData = xGenerateFilterTree(res.data, this.relationshipsData);
        if (res.data.treeTable) {
          this.xGridTreeConfig = { children: 'children', indent: 20, line: false, expandAll: false, accordion: false, trigger: 'default' };
        }
        if (this.treeFilterData && this.treeFilterData.length > 0) {
          this.filterTreeSpan = 6;
        }
        this.loading = false;
      });*/
  }

  filterByColumn(fieldName: string, filterValue: string[]) {
    this.mapOfFilter[fieldName].value = filterValue;
    this.loadAll();
  }
  getFilterTest() {
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
    if (this.queryId) {
      result['queryId.in'] = this.queryId;
    }
    return result;
  }

  cancelEdit(id: string): void {
    this.loadAll();
  }

  emitEmpty() {
    this.searchInput.focus();
    this.searchValue = '';
  }

  public updateModalCancel(e) {
    this.updateModalVisible = false;
    this.loadAll();
  }

  public newEntity(): void {
    console.log(this.commonTableId);
    console.log(this.queryId);
    if (this.commonTableId && this.queryId) {
      const newItem = new CommonQueryItem();
      newItem.queryId = this.queryId;
      this.xGridData.push(newItem);
    }
  }

  public editClosedEvent({ row, column }) {
    let field = column.property;
    let cellValue = row[field];
    // 判断单元格值是否被修改
    if (this.xGrid.isUpdateByRow(row, field)) {
      const entity = { id: row.id };
      entity[field] = cellValue;
      this.commonQueryItemService()
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
    forkJoin([this.commonQueryService().retrieve()]).subscribe(
      ([commonQueries]) => {
        this.relationshipsData['commonQueries'] = commonQueries.data;
        const listOfFilterquery = commonQueries.data.slice(0, commonQueries.data.length > 8 ? 7 : commonQueries.data.length - 1);
        this.mapOfFilter.query = { list: listOfFilterquery, value: [], type: 'many-to-one' };
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

  public xGridSortChange({ property, order }) {
    this.mapOfSort = {};
    this.mapOfSort[property] = order;
    this.loadAll();
  }

  public xGridFilterChange({ column, property, values, datas, filters, $event }) {
    const type = column.params ? column.params.type : '';
    var tempValues;
    if (type === 'STRING') {
      tempValues = values;
    }
    if (type === 'INTEGER' || type === 'LONG' || type === 'DOUBLE' || type === 'FLOAT' || type === 'ZONED_DATE_TIME') {
      tempValues = datas[0];
    }
    this.mapOfFilter[property] = { value: tempValues, type: type };
    this.loadAll();
  }

  public switchFilterTree() {
    this.filterTreeSpan = this.filterTreeSpan > 0 ? 0 : 6;
  }

  public saveAll() {
    this.xGridData.forEach(item => {
      if (item.id && typeof item.id === 'number') {
        this.commonQueryItemService()
          .update(item)
          .subscribe(res => {
            console.log('更新成功');
          });
      } else {
        const copy = item;
        copy.id = null;
        this.commonQueryItemService()
          .create(copy)
          .subscribe(res => {
            console.log('创建成功');
          });
      }
    });
  }
}
