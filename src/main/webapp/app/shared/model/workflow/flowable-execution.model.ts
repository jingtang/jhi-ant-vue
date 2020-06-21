export interface IFlowableExecution {
  id?: string;
  url?: string;
  parentId?: string;
  parentUrl?: string;
  processInstanceId?: string;
  processInstanceUrl?: string;
  suspended?: boolean;
  activityId?: string;
  tenantId?: any;
}
export class FlowableExecution implements IFlowableExecution {
  constructor(
    public id?: string,
    public url?: string,
    public parentId?: string,
    public parentUrl?: string,
    public processInstanceId?: string,
    public processInstanceUrl?: string,
    public suspended?: boolean,
    public activityId?: string,
    public tenantId?: any
  ) {}
}
