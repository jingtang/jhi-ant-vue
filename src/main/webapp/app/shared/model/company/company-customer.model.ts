import { ICompanyUser } from '@/shared/model/company/company-user.model';
import { ICompanyBusiness } from '@/shared/model/company/company-business.model';
import { Moment } from 'moment';

export interface ICompanyCustomer {
  id?: number;
  name?: string;
  code?: string;
  address?: string;
  phoneNum?: string;
  logo?: string;
  contact?: string;
  createUserId?: number;
  createTime?: Moment;
  children?: ICompanyCustomer[];
  companyUsers?: ICompanyUser[];
  companyBusinesses?: ICompanyBusiness[];
  parentName?: string;
  parentId?: number;
  expand?: boolean;
  nzAddLevel?: number;
}

export class CompanyCustomer implements ICompanyCustomer {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public address?: string,
    public phoneNum?: string,
    public logo?: string,
    public contact?: string,
    public createUserId?: number,
    public createTime?: Moment,
    public children?: ICompanyCustomer[],
    public companyUsers?: ICompanyUser[],
    public companyBusinesses?: ICompanyBusiness[],
    public parentName?: string,
    public parentId?: number,
    public expand?: boolean,
    public nzAddLevel?: number
  ) {}
}
