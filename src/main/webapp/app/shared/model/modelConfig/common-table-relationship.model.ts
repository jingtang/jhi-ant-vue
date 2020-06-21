export const enum RelationshipType {
  ONE_TO_MANY = 'ONE_TO_MANY',
  MANY_TO_ONE = 'MANY_TO_ONE',
  MANY_TO_MANY = 'MANY_TO_MANY',
  ONE_TO_ONE = 'ONE_TO_ONE'
}

export const enum SourceType {
  ENTITY = 'ENTITY',
  DATA_DICTIONARY = 'DATA_DICTIONARY'
}

export const enum FixedType {
  LEFT = 'LEFT',
  RIGHT = 'RIGHT'
}

export interface ICommonTableRelationship {
  id?: number;
  name?: string;
  relationshipType?: RelationshipType;
  sourceType?: SourceType;
  otherEntityField?: string;
  otherEntityName?: string;
  relationshipName?: string;
  otherEntityRelationshipName?: string;
  columnWidth?: number;
  order?: number;
  fixed?: FixedType;
  editInList?: boolean;
  enableFilter?: boolean;
  hideInList?: boolean;
  hideInForm?: boolean;
  fontColor?: string;
  backgroundColor?: string;
  help?: string;
  ownerSide?: boolean;
  dataName?: string;
  webComponentType?: string;
  otherEntityIsTree?: boolean;
  showInFilterTree?: boolean;
  dataDictionaryCode?: string;
  clientReadOnly?: boolean;
  relationEntityName?: string;
  relationEntityId?: number;
  dataDictionaryNodeName?: string;
  dataDictionaryNodeId?: number;
  commonTableName?: string;
  commonTableId?: number;
}

export class CommonTableRelationship implements ICommonTableRelationship {
  constructor(
    public id?: number,
    public name?: string,
    public relationshipType?: RelationshipType,
    public sourceType?: SourceType,
    public otherEntityField?: string,
    public otherEntityName?: string,
    public relationshipName?: string,
    public otherEntityRelationshipName?: string,
    public columnWidth?: number,
    public order?: number,
    public fixed?: FixedType,
    public editInList?: boolean,
    public enableFilter?: boolean,
    public hideInList?: boolean,
    public hideInForm?: boolean,
    public fontColor?: string,
    public backgroundColor?: string,
    public help?: string,
    public ownerSide?: boolean,
    public dataName?: string,
    public webComponentType?: string,
    public otherEntityIsTree?: boolean,
    public showInFilterTree?: boolean,
    public dataDictionaryCode?: string,
    public clientReadOnly?: boolean,
    public relationEntityName?: string,
    public relationEntityId?: number,
    public dataDictionaryNodeName?: string,
    public dataDictionaryNodeId?: number,
    public commonTableName?: string,
    public commonTableId?: number
  ) {
    this.editInList = this.editInList || false;
    this.enableFilter = this.enableFilter || false;
    this.hideInList = this.hideInList || false;
    this.hideInForm = this.hideInForm || false;
    this.ownerSide = this.ownerSide || false;
    this.otherEntityIsTree = this.otherEntityIsTree || false;
    this.showInFilterTree = this.showInFilterTree || false;
    this.clientReadOnly = this.clientReadOnly || false;
  }
}
