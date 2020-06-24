import { FieldType, FixedType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';
import { upperFirst } from 'lodash';
import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';

export function generateTableColumns(
  commonTableData: ICommonTable,
  relationshipsData,
  mapOfSort: { [key: string]: any },
  mapOfFilter: { [key: string]: any },
  apiService
) {
  const result: any[] = [];
  const fields = commonTableData.commonTableFields.filter(field => !field.hideInList);
  fields.forEach(field => {
    const column: any = {
      title: field.title,
      dataIndex: field.entityFieldName,
      key: field.entityFieldName,
      width: field.columnWidth
    };
    if (field.fixed === FixedType.LEFT) {
      column.fixed = 'left';
    } else if (field.fixed === FixedType.RIGHT) {
      column.fixed = 'right';
    }
    if (field.sortable) {
      column.sorter = true;
      mapOfSort[field.entityFieldName] = false;
    }
    switch (field.type) {
      case FieldType.ZONED_DATE_TIME:
        column.edit = {
          type: 'date',
          on: {
            open: values => {
              //开启编辑前回调
              console.log(values, 'open');
            },
            beforeChange: values => {
              //编辑值提交前回调
              console.log(values, 'beforeChange');
            },
            verify: values => {
              //编辑值验证方法
              console.log(values, 'verify');
              // return false;
            },
            change: values => {
              //编辑提交后回调
              console.log(values, 'change');
              const entity = { id: values.record.id };
              entity[values.column.dataIndex] = values.value;
              apiService()
                .updateBySpecifiedField(entity, values.column.dataIndex)
                .subscribe(res => {
                  values.record = res.data;
                });
            },
            pressEnter: values => {
              //编辑器按回车时发生
              console.log(values, 'pressEnter');
            }
          }
        };
        if (!field.editInList) {
          column.edit.props = { disabled: 'disabled' };
        }
        result.push(column);
        break;
      case FieldType.ENUM:
        break;
      case FieldType.DOUBLE:
      case FieldType.FLOAT:
      case FieldType.INTEGER:
      case FieldType.LONG:
        if (field.editInList) {
          column.edit = {
            type: 'number',
            props: {
              defaultValue: 0
            },
            on: {
              open: values => {
                //开启编辑前回调
                console.log(values, 'open');
              },
              beforeChange: values => {
                //编辑值提交前回调
                console.log(values, 'beforeChange');
              },
              verify: values => {
                //编辑值验证方法
                console.log(values, 'verify');
                return true;
              },
              change: values => {
                //编辑提交后回调
                console.log(values, 'change');
                const entity = { id: values.record.id };
                entity[values.column.dataIndex] = values.value;
                apiService()
                  .updateBySpecifiedField(entity, values.column.dataIndex)
                  .subscribe(res => {
                    values.record = res.data;
                  });
              },
              pressEnter: values => {
                //编辑器按回车时发生
                console.log(values, 'pressEnter');
              }
            }
          };
        }
        result.push(column);
        break;
      case FieldType.STRING:
        if (field.editInList) {
          column.edit = {
            type: 'input',
            on: {
              open: values => {
                //开启编辑前回调
                console.log(values, 'open');
              },
              beforeChange: values => {
                //编辑值提交前回调
                console.log(values, 'beforeChange');
              },
              verify: values => {
                //编辑值验证方法
                console.log(values, 'verify');
                return true;
              },
              change: values => {
                //编辑提交后回调
                console.log(values, 'change');
                const entity = { id: values.record.id };
                entity[values.column.dataIndex] = values.value;
                apiService()
                  .updateBySpecifiedField(entity, values.column.dataIndex)
                  .subscribe(res => {
                    values.record = res.data;
                  });
              },
              pressEnter: values => {
                //编辑器按回车时发生
                console.log(values, 'pressEnter');
              }
            }
          };
        }
        result.push(column);
        break;
      case FieldType.BOOLEAN:
        column.edit = {
          type: 'switch',
          on: {
            open: values => {
              //开启编辑前回调
              console.log(values, 'open');
            },
            beforeChange: values => {
              //编辑值提交前回调
              console.log(values, 'beforeChange');
            },
            verify: values => {
              //编辑值验证方法
              console.log(values, 'verify');
              return true;
            },
            change: values => {
              //编辑提交后回调
              console.log(values, 'change');
              const entity = { id: values.record.id };
              entity[values.column.dataIndex] = values.value;
              apiService()
                .updateBySpecifiedField(entity, values.column.dataIndex)
                .subscribe(res => {
                  values.record = res.data;
                });
            },
            pressEnter: values => {
              //编辑器按回车时发生
              console.log(values, 'pressEnter');
            }
          }
        };
        if (!field.editInList) {
          column.edit.props = { disabled: 'disabled' };
        }
        if (field.enableFilter) {
          mapOfFilter[field.entityFieldName] = {
            list: [{ text: '是', value: 'true' }, { text: '否', value: 'false' }],
            value: [],
            type: 'boolean'
          };
        }
        result.push(column);
    }
  });
  const relationships = commonTableData.relationships.filter(relationship => !relationship.hideInList);
  relationships.forEach(relationship => {
    const column: any = {
      width: relationship.columnWidth,
      title: relationship.name,
      key: relationship.relationshipName,
      sortOrder: mapOfSort[relationship.relationshipName]
    };
    switch (relationship.relationshipType) {
      case RelationshipType.MANY_TO_ONE:
        column.dataIndex = relationship.relationshipName + upperFirst(relationship.otherEntityField);
        console.log(relationshipsData[relationship.dataName]);
        column.edit = {
          type: 'select',
          textField: relationship.otherEntityField,
          valueField: 'id',
          data: relationshipsData[relationship.dataName],
          props: {
            showSearch: true,
            optionFilterProp: 'children',
            filterOption: (input, option, data) => {
              return data.text.toLowerCase().indexOf(input.toLowerCase()) >= 0 || data.code.toLowerCase().indexOf(input.toLowerCase()) >= 0;
            }
          }
        };
        result.push(column);
    }
  });
  const actionColumn = {
    title: 'Action',
    key: 'operation',
    fixed: 'right',
    width: 100,
    scopedSlots: { customRender: 'recordAction' }
  };
  result.push(actionColumn);
  return result;
}

export function generateFilterTree(commonTableData: ICommonTable, relationshipData) {
  const result = [];
  const fields = commonTableData.commonTableFields.filter(field => field.showInFilterTree);
  fields.forEach(field => {
    const filterItem: any = {};
    filterItem.title = field.title;
    filterItem.key = field.entityFieldName;
    filterItem.value = field.entityFieldName;
    filterItem.type = 'filterGroup';
    filterItem.children = [];
    switch (field.type) {
      case FieldType.BOOLEAN:
        const filerSubItemTrue: any = {};
        filerSubItemTrue.filterName = field.entityFieldName + '.equals';
        filerSubItemTrue.filterValue = true;
        filerSubItemTrue.title = '是';
        filerSubItemTrue.type = 'filterItem';
        filerSubItemTrue.value = field.entityFieldName + 'true';
        const filerSubItemFalse: any = {};
        filerSubItemTrue.filterName = field.entityFieldName + '.equals';
        filerSubItemTrue.filterValue = false;
        filerSubItemTrue.title = '否';
        filerSubItemTrue.type = 'filterItem';
        filerSubItemTrue.value = field.entityFieldName + 'false';
        filterItem.children.push(filerSubItemTrue);
        filterItem.children.push(filerSubItemFalse);
        result.push(filterItem);
        break;
      case FieldType.DATA_DICTIONARY:
      case FieldType.ENUM:
      case FieldType.ZONED_DATE_TIME:
    }
  });
  const relationships = commonTableData.relationships.filter(relationship => relationship.showInFilterTree);
  relationships.forEach(relationship => {
    const filterItem: any = {};
    filterItem.title = relationship.name;
    filterItem.key = relationship.relationshipName;
    filterItem.value = relationship.relationshipName;
    filterItem.type = 'filterGroup';
    filterItem.children = [];
    if (relationship.otherEntityIsTree) {
      filterItem.children = transformToFilterTree(relationshipData[relationship.dataName], relationship);
      result.push(filterItem);
    } else {
      relationshipData[relationship.dataName].forEach(record => {
        const filterSubItem: any = {};
        filterSubItem.filterName = relationship.relationshipName;
        filterSubItem.filterValue = record.id;
        filterSubItem.key = relationship.relationshipName + record.id;
        filterSubItem.title = record[relationship.otherEntityField];
        filterSubItem.type = 'filterItem';
        filterSubItem.value = `${relationship.relationshipName}${record.id}`;
        filterSubItem.record = record;
        filterItem.children.push(filterSubItem);
      });
      result.push(filterItem);
    }
  });
  return result;
}

function transformToFilterTree(record, relationship) {
  const result = [];
  const filterItem: any = {};
  filterItem.title = relationship.name;
  filterItem.key = relationship.relationshipName;
  filterItem.value = relationship.relationshipName;
  filterItem.type = 'filterGroup';
  filterItem.children = [];
  record.forEach(recordItem => {
    const filterSubItem: any = {};
    filterSubItem.filterName = relationship.relationshipName;
    filterSubItem.filterValue = recordItem.id;
    filterSubItem.title = recordItem[relationship.otherEntityField];
    filterSubItem.type = 'filterItem';
    filterSubItem.key = relationship.relationshipName + recordItem.id;
    filterSubItem.value = `${relationship.relationshipName}${recordItem.id}`;
    filterSubItem.record = recordItem;
    if (recordItem.children) {
      filterSubItem.children = transformToFilterTree(recordItem.children, relationship);
    }
    result.push(filterSubItem);
  });
  return result;
}

export function xGenerateTableColumns(
  commonTableData: ICommonTable,
  relationshipsData,
  mapOfSort: { [key: string]: any },
  mapOfFilter: { [key: string]: any },
  changeEvent: Function,
  noRecordAction?: boolean
) {
  const result: any[] = [];
  // 增加一个checkbox列
  result.push({ type: 'checkbox', width: 60 });
  const fields = commonTableData.commonTableFields.filter(field => !field.hideInList);
  fields.forEach(field => {
    const column: any = {
      title: field.title,
      field: field.entityFieldName,
      minWidth: field.columnWidth
    };
    if (field.treeIndicator) {
      column.treeNode = true;
    }
    if (field.fixed === FixedType.LEFT) {
      column.fixed = 'left';
    } else if (field.fixed === FixedType.RIGHT) {
      column.fixed = 'right';
    }
    if (field.sortable) {
      column.sortable = true;
      column.remoteSort = true;
      mapOfSort[field.entityFieldName] = false;
    }
    switch (field.type) {
      case FieldType.ZONED_DATE_TIME:
        column.params = { type: 'ZONED_DATE_TIME' };
        if (field.editInList) {
          column.editRender = {
            name: 'ADatePicker',
            props: {
              type: 'date',
              format: 'YYYY-MM-DD hh:mm:ss'
            },
            events: {
              ok: changeEvent,
              change: changeEvent
            }
          };
        }
        if (field.enableFilter) {
          column.filters = [{ data: [] }];
          column.filterRender = { name: 'AARangePicker' };
        }
        column.formatter = 'momentFormatter';
        result.push(column);
        break;
      case FieldType.ENUM:
        column.params = { type: 'ENUM' };
        if (field.enableFilter) {
          const fieldValuesObject = JSON.parse(field.fieldValues);
          const filters = [];
          Object.keys(fieldValuesObject).forEach(key => {
            filters.push({ label: fieldValuesObject[key], value: key });
          });
          column.filters = filters;
        }
        result.push(column);
        break;
      case FieldType.DOUBLE:
        column.params = { type: 'DOUBLE' };
        if (field.editInList) {
          column.editRender = {
            name: 'AInputNumber',
            events: {
              change: changeEvent,
              pressEnter: changeEvent
            }
          };
        }
        if (field.enableFilter) {
          column.filters = [{ data: [0, 100] }];
          column.filterRender = { name: 'ASlider', props: { range: true, marks: { 0: '0', 100: '100' } } };
        }
        result.push(column);
        break;
      case FieldType.FLOAT:
        column.params = { type: 'FLOAT' };
        if (field.editInList) {
          column.editRender = {
            name: 'AInputNumber',
            events: {
              change: changeEvent,
              pressEnter: changeEvent
            }
          };
        }
        if (field.enableFilter) {
          column.filters = [{ data: [0, 100] }];
          column.filterRender = { name: 'ASlider', props: { range: true, marks: { 0: '0', 100: '100' } } };
        }
        result.push(column);
        break;
      case FieldType.INTEGER:
        column.params = { type: 'INTEGER' };
        if (field.editInList) {
          column.editRender = {
            name: 'AInputNumber',
            events: {
              change: changeEvent,
              pressEnter: changeEvent
            }
          };
        }
        if (field.enableFilter) {
          column.filters = [{ data: [0, 100] }];
          column.filterRender = { name: 'ASlider', props: { range: true, marks: { 0: '0', 100: '100' } } };
        }
        result.push(column);
        break;
      case FieldType.LONG:
        column.params = { type: 'LONG' };
        if (field.editInList) {
          column.editRender = {
            name: 'AInputNumber',
            events: {
              change: changeEvent,
              pressEnter: changeEvent
            }
          };
        }
        if (field.enableFilter) {
          column.filters = [{ data: [0, 100] }];
          column.filterRender = { name: 'ASlider', props: { range: true, marks: { 0: '0', 100: '100' } } };
        }
        result.push(column);
        break;
      case FieldType.STRING:
        column.params = { type: 'STRING' };
        if (field.editInList) {
          column.editRender = {
            name: 'AInput',
            events: {
              change: changeEvent,
              pressEnter: changeEvent
            }
          };
        }
        if (field.enableFilter) {
          column.filters = [{ data: '' }];
          column.filterRender = { name: 'AInput', props: { placeholder: '请输入包含的信息' } };
        }
        result.push(column);
        break;
      case FieldType.BOOLEAN:
        if (!field.editInList) {
          column.cellRender = {
            name: 'ASwitch',
            props: {
              disabled: 'disabled'
            }
          };
        } else {
          column.editRender = {
            name: 'ASwitch',
            type: 'visible',
            events: {
              change: changeEvent
            }
          };
        }
        if (field.enableFilter) {
          if (field.fieldValues) {
            const fieldValuesObject = JSON.parse(field.fieldValues);
            const filterArray = [];
            Object.keys(fieldValuesObject).forEach(key => {
              filterArray.push({ label: fieldValuesObject[key], value: key });
            });
          } else {
            column.filters = [{ label: '是', value: 'true' }, { label: '否', value: 'false' }];
          }
        }
        result.push(column);
    }
  });
  const relationships = commonTableData.relationships.filter(relationship => !relationship.hideInList);
  relationships.forEach(relationship => {
    const column: any = {
      minWidth: relationship.columnWidth,
      title: relationship.name,
      // key: relationship.relationshipName,
      sortOrder: mapOfSort[relationship.relationshipName]
    };
    switch (relationship.relationshipType) {
      case RelationshipType.MANY_TO_ONE:
        if (relationship.editInList) {
          column.field = relationship.relationshipName + 'Id';
          column.editRender = {
            name: 'ASelectListModal',
            options: relationshipsData[relationship.dataName],
            optionProps: {
              value: 'id',
              label: relationship.otherEntityField
            }
          };
        } else {
          column.field = relationship.relationshipName + upperFirst(relationship.otherEntityField);
        }
        result.push(column);
    }
  });
  const actionColumn = {
    title: '操作',
    field: 'operation',
    fixed: 'right',
    width: 140,
    slots: { default: 'recordAction' }
  };
  if (!noRecordAction) {
    result.push(actionColumn);
  }
  return result;
}

export function xGenerateFilterTree(commonTableData: ICommonTable, relationshipData) {
  const result = [];
  const fields = commonTableData.commonTableFields.filter(field => field.showInFilterTree);
  fields.forEach(field => {
    const filterItem: any = {};
    filterItem.title = field.title;
    filterItem.key = field.entityFieldName;
    filterItem.value = field.entityFieldName;
    filterItem.type = 'filterGroup';
    filterItem.children = [];
    switch (field.type) {
      case FieldType.BOOLEAN:
        const filerSubItemTrue: any = {};
        filerSubItemTrue.filterName = field.entityFieldName + '.equals';
        filerSubItemTrue.filterValue = true;
        filerSubItemTrue.title = '是';
        filerSubItemTrue.type = 'filterItem';
        filerSubItemTrue.value = field.entityFieldName + 'true';
        const filerSubItemFalse: any = {};
        filerSubItemTrue.filterName = field.entityFieldName + '.equals';
        filerSubItemTrue.filterValue = false;
        filerSubItemTrue.title = '否';
        filerSubItemTrue.type = 'filterItem';
        filerSubItemTrue.value = field.entityFieldName + 'false';
        filterItem.children.push(filerSubItemTrue);
        filterItem.children.push(filerSubItemFalse);
        result.push(filterItem);
        break;
      case FieldType.DATA_DICTIONARY:
      case FieldType.ENUM:
      case FieldType.ZONED_DATE_TIME:
    }
  });
  const relationships = commonTableData.relationships.filter(relationship => relationship.showInFilterTree);
  relationships.forEach(relationship => {
    const filterItem: any = {};
    filterItem.title = relationship.name;
    filterItem.key = relationship.relationshipName;
    filterItem.value = relationship.relationshipName;
    filterItem.type = 'filterGroup';
    filterItem.children = [];
    if (relationship.otherEntityIsTree) {
      filterItem.children = xTransformToFilterTree(relationshipData[relationship.dataName], relationship);
      result.push(filterItem);
    } else {
      relationshipData[relationship.dataName].forEach(record => {
        const filterSubItem: any = {};
        filterSubItem.filterName = relationship.relationshipName;
        filterSubItem.filterValue = record.id;
        filterSubItem.key = relationship.relationshipName + record.id;
        filterSubItem.title = record[relationship.otherEntityField];
        filterSubItem.type = 'filterItem';
        filterSubItem.value = `${relationship.relationshipName}${record.id}`;
        filterSubItem.record = record;
        filterItem.children.push(filterSubItem);
      });
      result.push(filterItem);
    }
  });
  return result;
}

function xTransformToFilterTree(record, relationship) {
  const result = [];
  const filterItem: any = {};
  filterItem.title = relationship.name;
  filterItem.key = relationship.relationshipName;
  filterItem.value = relationship.relationshipName;
  filterItem.type = 'filterGroup';
  filterItem.children = [];
  record.forEach(recordItem => {
    const filterSubItem: any = {};
    filterSubItem.filterName = relationship.relationshipName;
    filterSubItem.filterValue = recordItem.id;
    filterSubItem.title = recordItem[relationship.otherEntityField];
    filterSubItem.type = 'filterItem';
    filterSubItem.key = relationship.relationshipName + recordItem.id;
    filterSubItem.value = `${relationship.relationshipName}${recordItem.id}`;
    filterSubItem.record = recordItem;
    if (recordItem.children) {
      filterSubItem.children = transformToFilterTree(recordItem.children, relationship);
    }
    result.push(filterSubItem);
  });
  return result;
}

export function getFilter(searchValue: string, mapOfFilter: { [key: string]: any }) {
  const result: { [key: string]: any } = {};
  if (searchValue) {
    result['jhiCommonSearchKeywords'] = searchValue;
    return result;
  }
  Object.keys(mapOfFilter).forEach(key => {
    const filterResult = [];
    if (mapOfFilter[key].type === 'Enum') {
      mapOfFilter[key].value.forEach(value => {
        filterResult.push(value);
      });
      result[key + '.in'] = filterResult;
    }
    if (['one-to-one', 'many-to-many', 'many-to-one', 'one-to-many'].includes(mapOfFilter[key].type)) {
      mapOfFilter[key].value.forEach(value => {
        filterResult.push(value);
      });
      result[key + 'Id.in'] = filterResult;
    }
    if (mapOfFilter[key].type === 'STRING') {
      result[key + '.contains'] = mapOfFilter[key].value;
    }
    if (
      mapOfFilter[key].type === 'LONG' ||
      mapOfFilter[key].type === 'INTEGER' ||
      mapOfFilter[key].type === 'FLOAT' ||
      mapOfFilter[key].type === 'DOUBLE'
    ) {
      result[key + '.greaterThanOrEqual'] = mapOfFilter[key].value[0];
      result[key + '.lessThanOrEqual'] = mapOfFilter[key].value[1];
    }
    if (mapOfFilter[key].type === 'ZONED_DATE_TIME') {
      result[key + '.greaterThanOrEqual'] =
        mapOfFilter[key].value[0] && mapOfFilter[key].value[0].isValid() ? mapOfFilter[key].value[0].toJSON() : null;
      result[key + '.lessThanOrEqual'] =
        mapOfFilter[key].value[1] && mapOfFilter[key].value[1].isValid() ? mapOfFilter[key].value[1].toJSON() : null;
    }
  });
  return result;
}
