export interface IInstanceVariable {
  name?: string;
  type?: string;
  value?: any;
  valueUrl?: string;
  scope?: string;
}

export class InstanceVariable implements IInstanceVariable {
  constructor(public name?: string, public type?: string, public value?: any, public valueUrl?: string, public scope?: string) {}
}
