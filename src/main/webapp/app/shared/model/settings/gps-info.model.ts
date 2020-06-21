export const enum GpsType {
  BAIDU = 'BAIDU',
  GOOGLE_MAP = 'GOOGLE_MAP',
  GOOGLE_EART = 'GOOGLE_EART',
  AMAP = 'AMAP'
}

export interface IGpsInfo {
  id?: number;
  type?: GpsType;
  latitude?: number;
  longitude?: number;
  address?: string;
}

export class GpsInfo implements IGpsInfo {
  constructor(public id?: number, public type?: GpsType, public latitude?: number, public longitude?: number, public address?: string) {}
}
