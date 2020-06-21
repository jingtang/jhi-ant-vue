export interface IFlowableModel {
  id?: string;
  key?: string;
  category?: string;
  version?: number;
  metaInfo?: string;
  deploymentId?: string;
  url?: string;
  createTime?: string;
  lastUpdateTime?: string;
  deploymentUrl?: string;
  tenantId?: any;
}
export class FlowableModel implements IFlowableModel {
  constructor(
    public id?: string,
    public key?: string,
    public category?: string,
    public version?: number,
    public metaInfo?: string,
    public deploymentId?: string,
    public url?: string,
    public createTime?: string,
    public lastUpdateTime?: string,
    public deploymentUrl?: string,
    public tenantId?: any
  ) {}
}
