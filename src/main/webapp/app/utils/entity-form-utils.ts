import { ICommonTable } from '@/shared/model/modelConfig/common-table.model';
import { FieldType } from '@/shared/model/modelConfig/common-table-field.model';
import { RelationshipType } from '@/shared/model/modelConfig/common-table-relationship.model';
import cloneDeep from 'lodash.clonedeep';
import { basicsList } from '@/components/jhi-data-form/KFormDesign/config/formItemsConfig';
import pick from 'lodash.pick';
import kebabCase from 'lodash.kebabcase';
import moment from 'moment';

function getDefaultItemData(key: string) {
  return cloneDeep(basicsList.find(item => item.type === key));
}

export function generateDataForDesigner(commonTableData: ICommonTable) {
  const dataContent = [];
  if (commonTableData.commonTableFields) {
    commonTableData.commonTableFields.forEach(field => {
      let validateRulesObject = null;
      let rules = [];
      if (field.validateRules) {
        validateRulesObject = JSON.parse(field.validateRules);
      }
      if (!field.hideInForm) {
        switch (field.type) {
          case FieldType.BOOLEAN: {
            const item = getDefaultItemData('switch');
            if (validateRulesObject) {
              if (validateRulesObject.required) {
                rules.push({ required: true, message: '必填项' });
                item.defaultValue = false;
              }
              item.rules = rules;
            }
            item.label = field.title;
            item.model = field.system ? field.entityFieldName : 'extData.' + field.entityFieldName;
            item.key = field.entityFieldName;
            dataContent.push(item);
            break;
          }
          case FieldType.INTEGER:
          case FieldType.DOUBLE:
          case FieldType.FLOAT:
          case FieldType.LONG: {
            const item = getDefaultItemData('number');
            if (validateRulesObject) {
              if (validateRulesObject.required) {
                rules.push({ required: true, message: '必填项' });
                item.defaultValue = 0;
              }
              if (validateRulesObject.min) {
                rules.push({ min: validateRulesObject.min, message: `必须大于${validateRulesObject.min}` });
              }
              if (validateRulesObject.max) {
                rules.push({ max: validateRulesObject.max, message: `必须小于${validateRulesObject.max}` });
              }
              item.rules = rules;
            }
            item.label = field.title;
            item.model = field.system ? field.entityFieldName : 'extData.' + field.entityFieldName;
            item.key = field.entityFieldName;
            dataContent.push(item);
            break;
          }
          case FieldType.ZONED_DATE_TIME: {
            const item = getDefaultItemData('date');
            if (validateRulesObject) {
              if (validateRulesObject.required) {
                rules.push({ required: true, message: '必填项' });
                item.defaultValue = moment();
              }
              item.rules = rules;
            }
            item.label = field.title;
            item.model = field.system ? field.entityFieldName : 'extData.' + field.entityFieldName;
            item.key = field.entityFieldName;
            item.options.format = 'YYYY-MM-DD hh:mm:ss';
            dataContent.push(item);
            break;
          }
          case FieldType.STRING: {
            const item = getDefaultItemData('input');
            if (validateRulesObject) {
              if (validateRulesObject.required) {
                rules.push({ required: true, message: '必填项' });
              }
              if (validateRulesObject.minLength) {
                rules.push({ minlength: validateRulesObject.minLength, message: `不能少于${validateRulesObject.minLength}个字符` });
              }
              if (validateRulesObject.maxLength) {
                rules.push({ maxlength: validateRulesObject.maxLength, message: `不能多于${validateRulesObject.maxLength}个字符` });
              }
              item.rules = rules;
            }
            item.label = field.title;
            item.model = field.system ? field.entityFieldName : 'extData.' + field.entityFieldName;
            item.key = field.entityFieldName;
            item.options.type = 'text';
            item.options.clearable = true;
            dataContent.push(item);
            break;
          }
          case FieldType.TEXTBLOB: {
            const item = getDefaultItemData('editor');
            if (validateRulesObject) {
              if (validateRulesObject.required) {
                rules.push({ required: true, message: '必填项' });
              }
              item.rules = rules;
            }
            item.label = field.title;
            item.model = field.system ? field.entityFieldName : 'extData.' + field.entityFieldName;
            item.key = field.entityFieldName;
            dataContent.push(item);
            break;
          }
          case FieldType.ENUM: {
            const item = getDefaultItemData('select');
            if (validateRulesObject) {
              if (validateRulesObject.required) {
                rules.push({ required: true, message: '必填项' });
              }
              item.rules = rules;
            }
            item.label = field.title;
            item.model = field.system ? field.entityFieldName : 'extData.' + field.entityFieldName;
            item.key = field.entityFieldName;
            item.options.dynamic = false;
            item.options.dynamicKey = '';
            item.options.options = [];
            const fieldValuesObject = JSON.parse(field.fieldValues);
            if (fieldValuesObject) {
              Object.keys(fieldValuesObject).forEach(key => {
                item.options.options.push({ value: key, label: fieldValuesObject[key] });
              });
            }
            dataContent.push(item);
          }
        }
      }
    });
  }
  if (commonTableData.relationships) {
    commonTableData.relationships.forEach(relationship => {
      if (!relationship.hideInForm) {
        switch (relationship.relationshipType) {
          case RelationshipType.MANY_TO_ONE:
            switch (relationship.otherEntityName) {
              case 'UploadFile': {
                const item = getDefaultItemData('uploadFile');
                item.label = relationship.name;
                item.model = relationship.relationshipName + 'Id';
                item.key = relationship.relationshipName + 'Id';
                dataContent.push(item);
                break;
              }
              case 'UploadImage': {
                const item = getDefaultItemData('uploadImg');
                item.label = relationship.name;
                item.model = relationship.relationshipName + 'Id';
                item.key = relationship.relationshipName + 'Id';
                dataContent.push(item);
                break;
              }
              default: {
                if (relationship.otherEntityIsTree) {
                  const item = getDefaultItemData('modalSelect');
                  item.label = relationship.name;
                  item.model = relationship.relationshipName + 'Id';
                  item.key = relationship.relationshipName + 'Id';
                  item.options.dynamic = true;
                  item.options.dynamicKey = relationship.dataName;
                  item.commonTableName = relationship.otherEntityName;
                  item.selectListName = 'jhi-' + kebabCase(relationship.otherEntityName);
                  item.options.valueField = 'id';
                  item.options.labelField = relationship.otherEntityField;
                  dataContent.push(item);
                } else {
                  const item = getDefaultItemData('modalSelect');
                  item.label = relationship.name;
                  item.model = relationship.relationshipName + 'Id';
                  item.key = relationship.relationshipName + 'Id';
                  item.options.valueField = 'id';
                  item.options.labelField = relationship.otherEntityField;
                  item.options.dynamic = true;
                  item.options.dynamicKey = relationship.dataName;
                  item.commonTableName = relationship.otherEntityName;
                  item.selectListName = 'jhi-' + kebabCase(relationship.otherEntityName);
                  dataContent.push(item);
                }
              }
            }
            break;
          case RelationshipType.MANY_TO_MANY:
            if (relationship.ownerSide === true) {
              const item = getDefaultItemData('customSelect');
              item.label = relationship.name;
              item.model = relationship.relationshipName + 'Id';
              item.key = relationship.relationshipName + 'Id';
              item.options.multiple = true;
              item.options.dynamic = true;
              item.options.dynamicKey = relationship.dataName;
              item.options.valueField = 'id';
              item.options.labelField = relationship.otherEntityField;
              dataContent.push(item);
            }
            break;
          case RelationshipType.ONE_TO_ONE:
            if (relationship.ownerSide === true) {
              const item = getDefaultItemData('customSelect');
              item.label = relationship.name;
              item.model = relationship.relationshipName + 'Id';
              item.key = relationship.relationshipName + 'Id';
              item.options.dynamic = true;
              item.options.dynamicKey = relationship.dataName;
              item.options.valueField = 'id';
              item.options.labelField = relationship.otherEntityField;
              dataContent.push(item);
            }
            break;
        }
      }
    });
  }
  return dataContent;
}

export function generateDataFormContent(commonTableData: ICommonTable, relationshipsData, treeNodeId) {
  const dataContent = [
    {
      type: 'number',
      label: 'Id',
      model: 'id',
      key: 'id',
      options: {
        width: '100%',
        placeholder: '系统自动生成',
        clearable: false,
        maxLength: null,
        disabled: true
      }
    }
  ];
  if (commonTableData.commonTableFields) {
    commonTableData.commonTableFields.forEach(field => {
      if (!field.hideInForm) {
        const item: any = {
          label: field.title,
          model: field.system ? field.entityFieldName : 'extData.' + field.entityFieldName,
          key: field.entityFieldName,
          options: {}
        };
        switch (field.type) {
          case FieldType.BOOLEAN:
            item.type = 'switch';
            break;
          case FieldType.INTEGER:
          case FieldType.DOUBLE:
          case FieldType.FLOAT:
          case FieldType.LONG:
            item.type = 'number';
            break;
          case FieldType.ZONED_DATE_TIME:
            item.type = 'date';
            item.options = {
              width: '100%',
              range: false,
              showTime: true,
              disabled: false,
              clearable: false,
              placeholder: '请选择',
              format: 'YYYY-MM-DD hh:mm:ss'
            };
            break;
          case FieldType.STRING:
            item.type = 'input';
            item.options = {
              type: 'text',
              clearable: true
            };
            break;
          case FieldType.TEXTBLOB:
            item.type = 'editor';
            item.options = {
              height: 300,
              placeholder: '请输入',
              chinesization: true,
              disabled: false,
              showLabel: false,
              width: '100%'
            };
            break;
        }
        dataContent.push(item);
      }
    });
  }
  if (commonTableData.relationships) {
    commonTableData.relationships.forEach(relationship => {
      if (!relationship.hideInForm) {
        const item: any = { label: relationship.name };
        switch (relationship.relationshipType) {
          case RelationshipType.MANY_TO_ONE:
            item.model = relationship.relationshipName + 'Id';
            item.key = relationship.relationshipName + 'Id';
            switch (relationship.otherEntityName) {
              case 'UploadFile':
                item.type = 'uploadFile';
                item.options = {
                  multiple: false,
                  disabled: false,
                  drag: false,
                  width: '100%',
                  limit: 1,
                  listType: 'picture-card'
                };
                break;
              case 'UploadImage':
                item.type = 'uploadImg';
                item.options = {
                  multiple: false,
                  disabled: false,
                  width: '100%',
                  limit: 1,
                  listType: 'picture-card'
                };
                break;
              default:
                if (relationship.otherEntityIsTree) {
                  item.type = 'treeSelect';
                  item.options = {
                    multiple: false,
                    showSearch: false,
                    treeCheckable: false,
                    dynamic: false,
                    dynamicKey: '',
                    options: toTreeNode(relationshipsData[relationship.dataName], 'id', relationship.otherEntityField, treeNodeId)
                  };
                } else {
                  item.type = 'customSelect';
                  item.options = {
                    options: relationshipsData[relationship.dataName],
                    multiple: false,
                    valueField: 'id',
                    labelField: relationship.otherEntityField
                  };
                }
            }
            dataContent.push(item);
            break;
          case RelationshipType.MANY_TO_MANY:
            if (relationship.ownerSide === true) {
              item.type = 'customSelect';
              item.model = relationship.relationshipName + 'Id';
              item.key = relationship.relationshipName + 'Id';
              item.options = {
                options: relationshipsData[relationship.dataName],
                multiple: true
              };
              dataContent.push(item);
            }
            break;
          case RelationshipType.ONE_TO_ONE:
            if (relationship.ownerSide === true) {
              item.type = 'customSelect';
              item.model = relationship.relationshipName + 'Id';
              item.key = relationship.relationshipName + 'Id';
              item.options = {
                options: relationshipsData[relationship.dataName],
                multiple: false
              };
              dataContent.push(item);
            }
            break;
        }
      }
    });
  }
  return dataContent;
}

function toTreeNode(items: any, valueFieldName: string, labelFieldName: string, currentId?: any, disabledParent: boolean = false) {
  const nzTreeNode = [];
  if (!items) {
    return nzTreeNode;
  }
  items.forEach(item => {
    let disabledChildren = false;
    const option = {
      value: item[valueFieldName],
      label: item[labelFieldName],
      disabled: disabledParent, // 树形关系中自己不能选择自己做为上级对象。
      children: undefined
    };
    if (item[valueFieldName] === currentId) {
      option.disabled = true;
      disabledChildren = true;
    }
    if (item.children && item.children.length > 0) {
      option.children = toTreeNode(item.children, valueFieldName, labelFieldName, currentId, disabledChildren);
    }
    nzTreeNode.push(option);
  });
  return nzTreeNode;
}

export function getDataByFormField(formFields: any[], data) {
  const fields = formFields.map(field => {
    return field.key;
  });
  return pick(data, fields);
}
