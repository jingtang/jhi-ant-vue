export interface IHistoryTaskInstance {
  id?: string;
  processDefinitionId?: string;
  processDefinitionUrl?: string;
  processInstanceId?: string;
  processInstanceUrl?: string;
  executionId?: string;
  name?: string;
  description?: string;
  deleteReason?: string;
  owner?: string;
  assignee?: string;
  startTime?: string;
  endTime?: string;
  durationInMillis?: number;
  workTimeInMillis?: number;
  claimTime?: string;
  taskDefinitionKey?: string;
  formKey?: string;
  priority?: number;
  dueDate?: string;
  parentTaskId?: string;
  url?: string;
  variables?: object;
  tenantId?: object;
}

export class HistoryTaskInstance implements IHistoryTaskInstance {
  constructor(
    public id?: string,
    public processDefinitionId?: string,
    public processDefinitionUrl?: string,
    public processInstanceId?: string,
    public processInstanceUrl?: string,
    public executionId?: string,
    public name?: string,
    public description?: string,
    public deleteReason?: string,
    public owner?: string,
    public assignee?: string,
    public startTime?: string,
    public endTime?: string,
    public durationInMillis?: number,
    public workTimeInMillis?: number,
    public claimTime?: string,
    public taskDefinitionKey?: string,
    public formKey?: string,
    public priority?: number,
    public dueDate?: string,
    public parentTaskId?: string,
    public url?: string,
    public variables?: object,
    public tenantId?: object
  ) {}
}
