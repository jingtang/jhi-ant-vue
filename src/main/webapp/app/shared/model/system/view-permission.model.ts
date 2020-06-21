import { IApiPermission } from '@/shared/model/system/api-permission.model';
import { IAuthority } from '@/shared/model/system/authority.model';

export const enum TargetType {
  BLANK = 'BLANK',
  SELF = 'SELF',
  PARENT = 'PARENT',
  TOP = 'TOP'
}

export const enum ViewPermissionType {
  MENU = 'MENU',
  BUTTON = 'BUTTON'
}

export interface IViewPermission {
  id?: number;
  text?: string;
  i18n?: string;
  group?: boolean;
  link?: string;
  externalLink?: string;
  target?: TargetType;
  icon?: string;
  disabled?: boolean;
  hide?: boolean;
  hideInBreadcrumb?: boolean;
  shortcut?: boolean;
  shortcutRoot?: boolean;
  reuse?: boolean;
  code?: string;
  description?: string;
  type?: ViewPermissionType;
  order?: number;
  apiPermissionCodes?: string;
  children?: IViewPermission[];
  apiPermissions?: IApiPermission[];
  parentText?: string;
  parentId?: number;
  authorities?: IAuthority[];
  expand?: boolean;
  nzAddLevel?: number;
}

export class ViewPermission implements IViewPermission {
  constructor(
    public id?: number,
    public text?: string,
    public i18n?: string,
    public group?: boolean,
    public link?: string,
    public externalLink?: string,
    public target?: TargetType,
    public icon?: string,
    public disabled?: boolean,
    public hide?: boolean,
    public hideInBreadcrumb?: boolean,
    public shortcut?: boolean,
    public shortcutRoot?: boolean,
    public reuse?: boolean,
    public code?: string,
    public description?: string,
    public type?: ViewPermissionType,
    public order?: number,
    public apiPermissionCodes?: string,
    public children?: IViewPermission[],
    public apiPermissions?: IApiPermission[],
    public parentText?: string,
    public parentId?: number,
    public authorities?: IAuthority[],
    public expand?: boolean,
    public nzAddLevel?: number
  ) {
    this.group = this.group || false;
    this.disabled = this.disabled || false;
    this.hide = this.hide || false;
    this.hideInBreadcrumb = this.hideInBreadcrumb || false;
    this.shortcut = this.shortcut || false;
    this.shortcutRoot = this.shortcutRoot || false;
    this.reuse = this.reuse || false;
  }
}
