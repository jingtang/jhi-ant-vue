export interface IHistoryActivityInstance {
  id?: string;
  activityId?: string;
  activityName?: string;
  activityType?: string;
  processDefinitionId?: string;
  processDefinitionUrl?: string;
  processInstanceId?: string;
  processInstanceUrl?: string;
  executionId?: string;
  taskId?: string;
  calledProcessInstanceId?: string;
  assignee?: string;
  startTime?: string;
  endTime?: string;
  durationInMillis?: string;
  tenantId?: string;
}

export class HistoryActivityInstance implements IHistoryActivityInstance {
  constructor(
    public id?: string,
    public activityId?: string,
    public activityName?: string,
    public activityType?: string,
    public processDefinitionId?: string,
    public processDefinitionUrl?: string,
    public processInstanceId?: string,
    public processInstanceUrl?: string,
    public executionId?: string,
    public taskId?: string,
    public calledProcessInstanceId?: string,
    public assignee?: string,
    public startTime?: string,
    public endTime?: string,
    public durationInMillis?: string,
    public tenantId?: string
  ) {}
}
