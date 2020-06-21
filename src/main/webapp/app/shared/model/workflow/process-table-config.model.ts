export interface IProcessTableConfig {
  id?: number;
  processDefinitionId?: string;
  processDefinitionKey?: string;
  processDefinitionName?: string;
  description?: string;
  processBpmnData?: any;
  deploied?: boolean;
  commonTableName?: string;
  commonTableId?: number;
  creatorLogin?: string;
  creatorId?: number;
}

export class ProcessTableConfig implements IProcessTableConfig {
  constructor(
    public id?: number,
    public processDefinitionId?: string,
    public processDefinitionKey?: string,
    public processDefinitionName?: string,
    public description?: string,
    public processBpmnData?: any,
    public deploied?: boolean,
    public commonTableName?: string,
    public commonTableId?: number,
    public creatorLogin?: string,
    public creatorId?: number
  ) {
    this.deploied = this.deploied || false;
  }
}
