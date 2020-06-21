export interface ITaskComment {
  id?: string;
  message?: string;
  author?: string;
  time?: string;
  taskId?: string;
  processInstanceId?: string;
}

export class TaskComment implements ITaskComment {
  constructor(
    public id?: string,
    public message?: string,
    public author?: string,
    public time?: string,
    public taskId?: string,
    public processInstanceId?: string
  ) {}
}
