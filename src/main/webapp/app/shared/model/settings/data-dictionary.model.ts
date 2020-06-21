export interface IDataDictionary {
  id?: number;
  name?: string;
  code?: string;
  description?: string;
  fontColor?: string;
  backgroundColor?: string;
  children?: IDataDictionary[];
  parentName?: string;
  parentId?: number;
  expand?: boolean;
  nzAddLevel?: number;
}

export class DataDictionary implements IDataDictionary {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public description?: string,
    public fontColor?: string,
    public backgroundColor?: string,
    public children?: IDataDictionary[],
    public parentName?: string,
    public parentId?: number,
    public expand?: boolean,
    public nzAddLevel?: number
  ) {}
}
