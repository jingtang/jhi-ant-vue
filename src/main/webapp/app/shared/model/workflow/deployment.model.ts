export interface IDeployment {
  id?: string;
  name?: string;
  deploymentTime?: string;
  category?: string;
  url?: string;
  tenantId?: any;
}

export class Deployment implements IDeployment {
  constructor(
    public id?: string,
    public name?: string,
    public deploymentTime?: string,
    public category?: string,
    public url?: string,
    public tenantId?: any
  ) {}
}
