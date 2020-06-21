import { IUser } from '@/shared/model/user.model';
import { IViewPermission } from '@/shared/model/system/view-permission.model';

export interface IAuthority {
  id?: number;
  name?: string;
  code?: string;
  info?: string;
  order?: number;
  display?: boolean;
  children?: IAuthority[];
  users?: IUser[];
  viewPermissions?: IViewPermission[];
  parentName?: string;
  parentId?: number;
  expand?: boolean;
  nzAddLevel?: number;
}

export class Authority implements IAuthority {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public info?: string,
    public order?: number,
    public display?: boolean,
    public children?: IAuthority[],
    public users?: IUser[],
    public viewPermissions?: IViewPermission[],
    public parentName?: string,
    public parentId?: number,
    public expand?: boolean,
    public nzAddLevel?: number
  ) {
    this.display = this.display || false;
  }
}
