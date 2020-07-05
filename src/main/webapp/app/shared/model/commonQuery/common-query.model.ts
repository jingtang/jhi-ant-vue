import { ICommonQueryItem } from '@/shared/model/commonQuery/common-query-item.model';
import { Moment } from 'moment';

export interface ICommonQuery {
  id?: number;
  name?: string;
  description?: string;
  lastModifiedTime?: Moment;
  items?: ICommonQueryItem[];
  modifierFirstName?: string;
  modifierId?: number;
  commonTableName?: string;
  commonTableId?: number;
}

export class CommonQuery implements ICommonQuery {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public lastModifiedTime?: Moment,
    public items?: ICommonQueryItem[],
    public modifierFirstName?: string,
    public modifierId?: number,
    public commonTableName?: string,
    public commonTableId?: number
  ) {}
}
