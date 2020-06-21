/**
 * 流程定义
 */
export interface IProcessDefinition {
  id?: string;
  url?: string;
  version?: number;
  key?: string;
  category?: string;
  suspended?: boolean;
  name?: string;
  description?: string;
  deploymentId?: string;
  deploymentUrl?: string;
  graphicalNotationDefined?: boolean;
  resource?: string;
  diagramResource?: string;
  startFormDefined?: boolean;
}

export class ProcessDefinition implements IProcessDefinition {
  constructor(
    public id?: string,
    public url?: string,
    public version?: number,
    public key?: string,
    public category?: string,
    public suspended?: boolean,
    public name?: string,
    public description?: string,
    public deploymentId?: string,
    public deploymentUrl?: string,
    public graphicalNotationDefined?: boolean,
    public resource?: string,
    public diagramResource?: string,
    public startFormDefined?: boolean
  ) {}
}
