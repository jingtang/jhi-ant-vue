export interface IProcessInstance {
  id?: string;
  url?: string;
  businessKey?: string;
  suspended?: boolean;
  processDefinitionUrl?: string;
  activityId?: string;
  tenantId?: any;
}

export class ProcessInstance implements IProcessInstance {
  constructor(
    public id?: string,
    public url?: string,
    public businessKey?: string,
    public suspended?: boolean,
    public processDefinitionUrl?: string,
    public activityId?: string,
    public tenantId?: any
  ) {}
}
