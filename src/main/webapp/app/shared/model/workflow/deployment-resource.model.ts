export interface IDeploymentResource {
  id?: string;
  url?: string;
  dataUrl?: string;
  mediaType?: string;
  type?: string;
}
export class DeploymentResource implements IDeploymentResource {
  constructor(public id?: string, public url?: string, public dataUrl?: string, public mediaType?: string, public type?: string) {}
}
