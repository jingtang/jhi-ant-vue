export interface IInstanceData {
  processDefinitionId?: string;
  processDefinitionKey?: string;
  message?: string;
  businessKey?: string;
  returnVariables?: boolean;
  variables?: any[];
  tenantId?: string;
}

export class InstanceData implements IInstanceData {
  constructor(
    public processDefinitionId?: string,
    public processDefinitionKey?: string,
    public message?: string,
    public businessKey?: string,
    public returnVariables?: boolean,
    public variables?: any[],
    public tenantId?: string
  ) {}
}
