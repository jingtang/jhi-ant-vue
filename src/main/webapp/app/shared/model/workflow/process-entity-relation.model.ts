export const enum ProcessEntityStatus {
  START = 'START',
  RUNNING = 'RUNNING',
  END = 'END'
}

export interface IProcessEntityRelation {
  id?: number;
  entityType?: string;
  entityId?: number;
  processInstanceId?: string;
  status?: ProcessEntityStatus;
}

export class ProcessEntityRelation implements IProcessEntityRelation {
  constructor(
    public id?: number,
    public entityType?: string,
    public entityId?: number,
    public processInstanceId?: string,
    public status?: ProcessEntityStatus
  ) {}
}
