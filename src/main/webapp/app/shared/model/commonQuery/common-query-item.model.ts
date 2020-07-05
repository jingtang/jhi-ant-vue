export interface ICommonQueryItem {
  id?: number;
  prefix?: string;
  fieldName?: string;
  operator?: string;
  value?: string;
  suffix?: string;
  order?: number;
  queryName?: string;
  queryId?: number;
}

export class CommonQueryItem implements ICommonQueryItem {
  constructor(
    public id?: number,
    public prefix?: string,
    public fieldName?: string,
    public operator?: string,
    public value?: string,
    public suffix?: string,
    public order?: number,
    public queryName?: string,
    public queryId?: number
  ) {}
}
