import { IProcessDefinition } from '@/shared/model/workflow/process-definition.model';
import { IDeployment } from '@/shared/model/workflow/deployment.model';

export interface IDeploymentList {
  data?: IDeployment[];
  total?: number;
  start?: number;
  sort?: string;
  order?: string;
  size?: number;
}

export class DeploymentList implements IDeploymentList {
  constructor(
    public data?: IDeployment[],
    public total?: number,
    public start?: number,
    public sort?: string,
    public order?: string,
    public size?: number
  ) {}
}
