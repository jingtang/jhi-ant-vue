export interface IBusinessType {
  id?: number;
  name?: string;
  code?: string;
  description?: string;
  icon?: string;
}

export class BusinessType implements IBusinessType {
  constructor(public id?: number, public name?: string, public code?: string, public description?: string, public icon?: string) {}
}
