import { IViewPermission } from '@/shared/model/system/view-permission.model';

export const enum ApiPermissionType {
  BUSINESS = 'BUSINESS',
  API = 'API'
}

export interface IApiPermission {
  id?: number;
  name?: string;
  code?: string;
  description?: string;
  type?: ApiPermissionType;
  children?: IApiPermission[];
  parentName?: string;
  parentId?: number;
  viewPermissions?: IViewPermission[];
  expand?: boolean;
  nzAddLevel?: number;
}

export class ApiPermission implements IApiPermission {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public description?: string,
    public type?: ApiPermissionType,
    public children?: IApiPermission[],
    public parentName?: string,
    public parentId?: number,
    public viewPermissions?: IViewPermission[],
    public expand?: boolean,
    public nzAddLevel?: number
  ) {}
}
