import { Moment } from 'moment';

export const enum CompanyBusinessStatus {
  OPEN = 'OPEN',
  TRIAL = 'TRIAL',
  CLOSE = 'CLOSE'
}

export interface ICompanyBusiness {
  id?: number;
  status?: CompanyBusinessStatus;
  expirationTime?: Moment;
  startTime?: Moment;
  operateUserId?: number;
  groupId?: string;
  businessTypeName?: string;
  businessTypeId?: number;
  companyName?: string;
  companyId?: number;
}

export class CompanyBusiness implements ICompanyBusiness {
  constructor(
    public id?: number,
    public status?: CompanyBusinessStatus,
    public expirationTime?: Moment,
    public startTime?: Moment,
    public operateUserId?: number,
    public groupId?: string,
    public businessTypeName?: string,
    public businessTypeId?: number,
    public companyName?: string,
    public companyId?: number
  ) {}
}
