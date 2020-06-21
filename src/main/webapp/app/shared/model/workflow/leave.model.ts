import { IUploadImage } from '@/shared/model/files/upload-image.model';
import { Moment } from 'moment';

export interface ILeave {
  id?: number;
  createTime?: Moment;
  name?: string;
  days?: number;
  startTime?: Moment;
  endTime?: Moment;
  reason?: string;
  images?: IUploadImage[];
  creatorLogin?: string;
  creatorId?: number;
}

export class Leave implements ILeave {
  constructor(
    public id?: number,
    public createTime?: Moment,
    public name?: string,
    public days?: number,
    public startTime?: Moment,
    public endTime?: Moment,
    public reason?: string,
    public images?: IUploadImage[],
    public creatorLogin?: string,
    public creatorId?: number
  ) {}
}
