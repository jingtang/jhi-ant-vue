export const enum FieldType {
  INTEGER = 'INTEGER',
  LONG = 'LONG',
  BOOLEAN = 'BOOLEAN',
  STRING = 'STRING',
  FLOAT = 'FLOAT',
  DOUBLE = 'DOUBLE',
  ZONED_DATE_TIME = 'ZONED_DATE_TIME',
  TEXTBLOB = 'TEXTBLOB',
  IMAGEBLOB = 'IMAGEBLOB',
  ENUM = 'ENUM',
  UPLOAD_IMAGE = 'UPLOAD_IMAGE',
  UPLOAD_FILE = 'UPLOAD_FILE',
  ENTITY = 'ENTITY',
  RADIO = 'RADIO',
  MULTI_SELECT = 'MULTI_SELECT',
  DATA_DICTIONARY = 'DATA_DICTIONARY'
}

export const enum FixedType {
  LEFT = 'LEFT',
  RIGHT = 'RIGHT'
}

export interface ICommonTableField {
  id?: number;
  title?: string;
  entityFieldName?: string;
  type?: FieldType;
  tableColumnName?: string;
  columnWidth?: number;
  order?: number;
  editInList?: boolean;
  hideInList?: boolean;
  hideInForm?: boolean;
  enableFilter?: boolean;
  validateRules?: string;
  showInFilterTree?: boolean;
  fixed?: FixedType;
  sortable?: boolean;
  treeIndicator?: boolean;
  clientReadOnly?: boolean;
  fieldValues?: string;
  notNull?: boolean;
  system?: boolean;
  help?: string;
  fontColor?: string;
  backgroundColor?: string;
  commonTableName?: string;
  commonTableId?: number;
}

export class CommonTableField implements ICommonTableField {
  constructor(
    public id?: number,
    public title?: string,
    public entityFieldName?: string,
    public type?: FieldType,
    public tableColumnName?: string,
    public columnWidth?: number,
    public order?: number,
    public editInList?: boolean,
    public hideInList?: boolean,
    public hideInForm?: boolean,
    public enableFilter?: boolean,
    public validateRules?: string,
    public showInFilterTree?: boolean,
    public fixed?: FixedType,
    public sortable?: boolean,
    public treeIndicator?: boolean,
    public clientReadOnly?: boolean,
    public fieldValues?: string,
    public notNull?: boolean,
    public system?: boolean,
    public help?: string,
    public fontColor?: string,
    public backgroundColor?: string,
    public commonTableName?: string,
    public commonTableId?: number
  ) {
    this.editInList = this.editInList || false;
    this.hideInList = this.hideInList || false;
    this.hideInForm = this.hideInForm || false;
    this.enableFilter = this.enableFilter || false;
    this.showInFilterTree = this.showInFilterTree || false;
    this.sortable = this.sortable || false;
    this.treeIndicator = this.treeIndicator || false;
    this.clientReadOnly = this.clientReadOnly || false;
    this.notNull = this.notNull || false;
    this.system = this.system || false;
  }
}
