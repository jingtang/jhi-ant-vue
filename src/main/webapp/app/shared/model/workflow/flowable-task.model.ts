export interface IFlowableTask {
  assignee?: string;
  createTime?: string;
  delegationState?: string;
  description?: string;
  dueDate?: string;
  execution?: string;
  id?: string;
  name?: string;
  owner?: string;
  parentTask?: string;
  priority?: number;
  processDefinitionId?: string;
  processDefinitionUrl?: string;
  processInstanceId?: string;
  processDefinition?: string;
  processInstance?: string;
  suspended?: boolean;
  taskDefinitionKey?: string;
  url?: string;
  tenantId?: any;
}

export class FlowableTask implements IFlowableTask {
  constructor(
    public assignee?: string,
    public createTime?: string,
    public delegationState?: string,
    public description?: string,
    public dueDate?: string,
    public execution?: string,
    public id?: string,
    public name?: string,
    public owner?: string,
    public parentTask?: string,
    public priority?: number,
    public processDefinitionId?: string,
    public processDefinitionUrl?: string,
    public processInstanceId?: string,
    public processDefinition?: string,
    public processInstance?: string,
    public suspended?: boolean,
    public taskDefinitionKey?: string,
    public url?: string,
    public tenantId?: any
  ) {}
}
