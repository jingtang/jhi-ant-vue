export interface IFlowableList<T> {
  data?: T[];
  total?: number;
  start?: number;
  sort?: string;
  order?: string;
  size?: number;
}

export class FlowAbleList<T> {
  constructor(
    public data?: T[],
    public total?: number,
    public start?: number,
    public sort?: string,
    public order?: string,
    public size?: number
  ) {}
}
