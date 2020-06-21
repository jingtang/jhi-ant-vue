export interface IAdministrativeDivision {
  id?: number;
  name?: string;
  areaCode?: string;
  cityCode?: string;
  mergerName?: string;
  shortName?: string;
  zipCode?: string;
  level?: number;
  lng?: number;
  lat?: number;
  children?: IAdministrativeDivision[];
  parentName?: string;
  parentId?: number;
  expand?: boolean;
  nzAddLevel?: number;
}

export class AdministrativeDivision implements IAdministrativeDivision {
  constructor(
    public id?: number,
    public name?: string,
    public areaCode?: string,
    public cityCode?: string,
    public mergerName?: string,
    public shortName?: string,
    public zipCode?: string,
    public level?: number,
    public lng?: number,
    public lat?: number,
    public children?: IAdministrativeDivision[],
    public parentName?: string,
    public parentId?: number,
    public expand?: boolean,
    public nzAddLevel?: number
  ) {}
}
