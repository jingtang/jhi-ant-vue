import { Moment } from 'moment';

export interface IUploadImage {
  id?: number;
  fullName?: string;
  name?: string;
  ext?: string;
  type?: string;
  url?: string;
  path?: string;
  folder?: string;
  entityName?: string;
  createAt?: Moment;
  fileSize?: number;
  smartUrl?: string;
  mediumUrl?: string;
  referenceCount?: number;
  userLogin?: string;
  userId?: number;
}

export class UploadImage implements IUploadImage {
  constructor(
    public id?: number,
    public fullName?: string,
    public name?: string,
    public ext?: string,
    public type?: string,
    public url?: string,
    public path?: string,
    public folder?: string,
    public entityName?: string,
    public createAt?: Moment,
    public fileSize?: number,
    public smartUrl?: string,
    public mediumUrl?: string,
    public referenceCount?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
