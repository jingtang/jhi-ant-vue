export interface IProcessFormConfig {
  id?: number;
  processDefinitionKey?: string;
  taskNodeId?: string;
  taskNodeName?: string;
  commonTableId?: number;
  formData?: any;
}

export class ProcessFormConfig implements IProcessFormConfig {
  constructor(
    public id?: number,
    public processDefinitionKey?: string,
    public taskNodeId?: string,
    public taskNodeName?: string,
    public commonTableId?: number,
    public formData?: any
  ) {}
}
