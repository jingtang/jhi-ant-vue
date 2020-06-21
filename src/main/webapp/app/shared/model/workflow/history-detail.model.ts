export interface IHistoryDetail {
  id?: string;
  processInstanceId?: string;
  processInstanceUrl?: string;
  executionId?: string;
  activityInstanceId?: string;
  taskId?: string;
  taskUrl?: string;
  time?: string;
  detailType?: string;
  revision?: string;
  variable?: any;
  propertyId?: string;
  propertyValue?: string;
}

export class HistoryDetail implements IHistoryDetail {
  constructor(
    public id?: string,
    public processInstanceId?: string,
    public processInstanceUrl?: string,
    public executionId?: string,
    public activityInstanceId?: string,
    public taskId?: string,
    public taskUrl?: string,
    public time?: string,
    public detailType?: string,
    public revision?: string,
    public variable?: any,
    public propertyId?: string,
    public propertyValue?: string
  ) {}
}
