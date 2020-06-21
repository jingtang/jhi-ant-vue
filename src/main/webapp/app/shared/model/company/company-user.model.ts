export interface ICompanyUser {
  id?: number;
  userFirstName?: string;
  userId?: number;
  companyName?: string;
  companyId?: number;
}

export class CompanyUser implements ICompanyUser {
  constructor(
    public id?: number,
    public userFirstName?: string,
    public userId?: number,
    public companyName?: string,
    public companyId?: number
  ) {}
}
