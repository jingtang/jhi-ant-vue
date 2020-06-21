export interface IHistoryProcessInstance {
  id?: string;
  businessKey?: string;
  processDefinitionId?: string;
  processDefinitionUrl?: string;
  startTime?: string;
  endTime?: string;
  durationInMillis?: number;
  startUserId?: string;
  startActivityId?: string;
  endActivityId?: string;
  deleteReason?: string;
  superProcessInstanceId?: string;
  url?: string;
  variables?: string;
  tenantId?: any;
}

export class HistoryProcessInstance implements IHistoryProcessInstance {
  constructor(
    public id?: string,
    public businessKey?: string,
    public processDefinitionId?: string,
    public processDefinitionUrl?: string,
    public startTime?: string,
    public endTime?: string,
    public durationInMillis?: number,
    public startUserId?: string,
    public startActivityId?: string,
    public endActivityId?: string,
    public deleteReason?: string,
    public superProcessInstanceId?: string,
    public url?: string,
    public variables?: string,
    public tenantId?: any
  ) {}
}
