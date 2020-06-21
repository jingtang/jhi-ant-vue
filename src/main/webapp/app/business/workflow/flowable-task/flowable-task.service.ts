import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';
import { IFlowableList } from '@/shared/model/workflow/flowable-list.model';
import { IDeployment } from '@/shared/model/workflow/deployment.model';
import { IDeploymentResource } from '@/shared/model/workflow/deployment-resource.model';
import { IFlowableTask } from '@/shared/model/workflow/flowable-task.model';
import { IInstanceVariable } from '@/shared/model/workflow/instance-variable.model';

const baseApiUrl = 'process-api/runtime/tasks';
const baseQueryUrl = 'process-api/query/tasks';
type EntityResponseType = AxiosResponse<IFlowableTask>;
type DeploymentResourceListType = AxiosResponse<IDeploymentResource[]>;
type DeploymentResourceType = AxiosResponse<IDeploymentResource>;
type VoidResponseType = AxiosResponse<void>;
type EntityArrayResponseType = AxiosResponse<IFlowableList<IFlowableTask>>;
type InstanceVariableArrayResponseType = AxiosResponse<IInstanceVariable[]>;
type InstanceVariableResponseType = AxiosResponse<IInstanceVariable>;

export default class FlowableTaskService {
  /**
   * 查找指定id的任务
   * @param taskId
   */
  public find(taskId: string): Observable<EntityResponseType> {
    return Axios.get<IFlowableTask>(`${baseApiUrl}/${taskId}`);
  }

  /**
   * 查询所有的任务
   * @param paginationQuery
   */
  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IFlowableList<IFlowableTask>>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 复合查询所有的任务
   * @param paginationQuery
   * @param queryData
   */
  public query(paginationQuery?: any, queryData?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.post<IFlowableList<IFlowableTask>>(baseQueryUrl, queryData, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 更新指定id的任务
   * @param taskId
   * @param flowalbeTask
   */
  public update(taskId: string, flowalbeTask: IFlowableTask): Observable<EntityResponseType> {
    return Axios.put<IFlowableTask>(`${baseApiUrl}/${taskId}`, flowalbeTask);
  }

  /**
   * 任务操作
   * @param taskId
   * @param payload
   */
  public operateTask(taskId: string, payload: any): Observable<EntityResponseType> {
    return Axios.post<IFlowableTask>(`${baseApiUrl}/${taskId}`, payload);
  }

  /**
   * 删除指定id的任务
   * @param taskId
   * @param cascadeHistory
   * @param deleteReason
   */
  public delete(taskId: string, cascadeHistory?: boolean, deleteReason?: string): Observable<VoidResponseType> {
    return Axios.delete<void>(`${baseApiUrl}/${taskId}?cascadeHistory=${cascadeHistory}&deleteReason=${deleteReason}`);
  }

  /**
   * 查找指定任务的所有变量
   * @param taskId
   */
  public findVariables(taskId: string, scope?: string): Observable<InstanceVariableArrayResponseType> {
    return Axios.get<IInstanceVariable[]>(`${baseApiUrl}/${taskId}/variables?scope=${scope}`);
  }

  /**
   * 查找指定任务的单个变量值
   * @param taskId
   */
  public getVariable(taskId: string, variableName: string, scope?: string): Observable<InstanceVariableResponseType> {
    return Axios.get<IInstanceVariable>(`${baseApiUrl}/${taskId}/variables/${variableName}?scope=${scope}`);
  }

  /**
   * 部署一个流程
   * @param formData
   */
  public deployProcess(formData: FormData): Observable<EntityResponseType> {
    return Axios.post<IDeployment>(`${baseApiUrl}`, formData);
  }

  /**
   * 查找指定id的流程的资源
   * @param id
   */
  public findAllResources(id: string): Observable<DeploymentResourceListType> {
    return Axios.get<IDeploymentResource[]>(`${baseApiUrl}/${id}/resources`);
  }

  /**
   * 查找指定id的流程的指定resourceId的资源
   * @param id
   */
  public findResource(deploymentId: string, resourceId: string): Observable<DeploymentResourceType> {
    return Axios.get<IDeploymentResource>(`${baseApiUrl}/${deploymentId}/resources/${resourceId}`);
  }

  /**
   * 查找指定id的流程的指定resourceId的资源数据
   * @param id
   */
  public getResourceData(deploymentId: string, resourceId: string): Observable<DeploymentResourceType> {
    return Axios.get<IDeploymentResource>(`${baseApiUrl}/${deploymentId}/resourcedata/${resourceId}`);
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
