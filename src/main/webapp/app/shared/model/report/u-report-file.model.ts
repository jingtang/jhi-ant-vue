import { Moment } from 'moment';

export interface IUReportFile {
  id?: number;
  name?: string;
  content?: any;
  createAt?: Moment;
  updateAt?: Moment;
  commonTableName?: string;
  commonTableId?: number;
}

export class UReportFile implements IUReportFile {
  constructor(
    public id?: number,
    public name?: string,
    public content?: any,
    public createAt?: Moment,
    public updateAt?: Moment,
    public commonTableName?: string,
    public commonTableId?: number
  ) {}
}
