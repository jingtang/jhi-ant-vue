import { ICommonTableField } from '@/shared/model/modelConfig/common-table-field.model';
import { ICommonTableRelationship } from '@/shared/model/modelConfig/common-table-relationship.model';
import { Moment } from 'moment';

export interface ICommonTable {
  id?: number;
  name?: string;
  entityName?: string;
  tableName?: string;
  system?: boolean;
  clazzName?: string;
  generated?: boolean;
  creatAt?: Moment;
  generateAt?: Moment;
  generateClassAt?: Moment;
  description?: string;
  treeTable?: boolean;
  baseTableId?: number;
  recordActionWidth?: number;
  listConfig?: any;
  formConfig?: any;
  editInModal?: boolean;
  commonTableFields?: ICommonTableField[];
  relationships?: ICommonTableRelationship[];
  creatorLogin?: string;
  creatorId?: number;
  businessTypeName?: string;
  businessTypeId?: number;
}

export class CommonTable implements ICommonTable {
  constructor(
    public id?: number,
    public name?: string,
    public entityName?: string,
    public tableName?: string,
    public system?: boolean,
    public clazzName?: string,
    public generated?: boolean,
    public creatAt?: Moment,
    public generateAt?: Moment,
    public generateClassAt?: Moment,
    public description?: string,
    public treeTable?: boolean,
    public baseTableId?: number,
    public recordActionWidth?: number,
    public listConfig?: any,
    public formConfig?: any,
    public editInModal?: boolean,
    public commonTableFields?: ICommonTableField[],
    public relationships?: ICommonTableRelationship[],
    public creatorLogin?: string,
    public creatorId?: number,
    public businessTypeName?: string,
    public businessTypeId?: number
  ) {
    this.system = this.system || false;
    this.generated = this.generated || false;
    this.treeTable = this.treeTable || false;
    this.editInModal = this.editInModal || false;
  }
}
