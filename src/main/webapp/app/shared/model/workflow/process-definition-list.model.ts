/**
 * 流程定义
 */
import { IProcessDefinition } from '@/shared/model/workflow/process-definition.model';

export interface IProcessDefinitionList {
  data?: IProcessDefinition[];
  total?: number;
  start?: number;
  sort?: string;
  order?: string;
  size?: number;
}

export class ProcessDefinitionList implements IProcessDefinitionList {
  constructor(
    public data?: IProcessDefinition[],
    public total?: number,
    public start?: number,
    public sort?: string,
    public order?: string,
    public size?: number
  ) {}
}
