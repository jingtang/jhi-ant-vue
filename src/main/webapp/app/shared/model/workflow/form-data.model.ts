export interface IEnumValue {
  id?: string;
  name?: string;
}
export class EnumValue implements IEnumValue {
  constructor(public id?: string, public name?: string) {}
}
export interface IFormProperty {
  id?: string;
  name?: string;
  type?: string;
  value?: any;
  readable?: boolean;
  writable?: boolean;
  required?: boolean;
  datePattern?: any;
  enumValues?: IEnumValue[];
}

export class FormProperty implements IFormProperty {
  constructor(
    public id?: string,
    public name?: string,
    public type?: string,
    public value?: any,
    public readable?: boolean,
    public writable?: boolean,
    public required?: boolean,
    public datePattern?: any,
    public enumValues?: IEnumValue[]
  ) {}
}

export interface IFormData {
  formKey?: string;
  deploymentId?: string;
  processDefinitionId?: string;
  processDefinitionUrl?: string;
  taskId?: string;
  taskUrl?: string;
  formProperties?: IFormProperty[];
}

export class FormData implements IFormData {
  constructor(
    public formKey?: string,
    public deploymentId?: string,
    public processDefinitionId?: string,
    public processDefinitionUrl?: string,
    public taskId?: string,
    public taskUrl?: string,
    public formProperties?: IFormProperty[]
  ) {}
}
