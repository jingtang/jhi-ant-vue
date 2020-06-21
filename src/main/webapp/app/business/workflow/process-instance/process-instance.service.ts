import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';
import { IFlowableList } from '@/shared/model/workflow/flowable-list.model';
import { IDeploymentResource } from '@/shared/model/workflow/deployment-resource.model';
import { IProcessInstance } from '@/shared/model/workflow/process-instance.model';
import { IInstanceData } from '@/shared/model/workflow/instance-data.model';
import { IInvolvePeople } from '@/shared/model/workflow/involve-people.model';
import { IInstanceVariable } from '@/shared/model/workflow/instance-variable.model';

const baseApiUrl = 'process-api/runtime/process-instances';
const baseQueryUrl = 'process-api/query/process-instances';
type EntityResponseType = AxiosResponse<IProcessInstance>;
type DeploymentResourceListType = AxiosResponse<IDeploymentResource[]>;
type DeploymentResourceType = AxiosResponse<IDeploymentResource>;
type VoidResponseType = AxiosResponse<void>;
type EntityArrayResponseType = AxiosResponse<IFlowableList<IProcessInstance[]>>;
type InvolvePeopleArrayResponseType = AxiosResponse<IInvolvePeople[]>;
type InvolvePeopleResponseType = AxiosResponse<IInvolvePeople>;
type InstanceVariableArrayResponseType = AxiosResponse<IInstanceVariable[]>;
type InstanceVariableResponseType = AxiosResponse<IInstanceVariable>;

export default class ProcessInstanceService {
  /**
   * 查找指定id的流程实例
   * @param id
   */
  public find(processInstanceId: string): Observable<EntityResponseType> {
    return Axios.get<IProcessInstance>(`${baseApiUrl}/${processInstanceId}`);
  }

  /**
   * 删除指定id的流程实例
   * @param id
   */
  public delete(processInstanceId: string): Observable<VoidResponseType> {
    return Axios.delete<void>(`${baseApiUrl}/${processInstanceId}`);
  }

  /**
   * 激活或挂起指定id的流程实例
   * @param id
   */
  public operator(processInstanceId: string, operate: string): Observable<VoidResponseType> {
    return Axios.put<void>(`${baseApiUrl}/${processInstanceId}`, { action: operate });
  }

  /**
   * 开始一个新的流程实例
   * @param id
   */
  public start(instanceData: IInstanceData): Observable<EntityResponseType> {
    return Axios.post<IProcessInstance>(`${baseApiUrl}`, instanceData);
  }

  /**
   * 查询所有的流程实例
   * @param paginationQuery
   */
  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IFlowableList<IProcessInstance[]>>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 查询所有的流程实例
   * @param paginationQuery
   */
  public query(paginationQuery?: any, queryData?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.post<IFlowableList<IProcessInstance[]>>(baseApiUrl, queryData, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 查找指定id的流程实例
   * @param id
   */
  public diagram(processInstanceId: string): Observable<EntityResponseType> {
    return Axios.get<IProcessInstance>(`${baseApiUrl}/${processInstanceId}/diagram`);
  }

  /**
   * 获得流程的所有参与人
   * @param id
   */
  public involvePeople(processInstanceId: string): Observable<InvolvePeopleArrayResponseType> {
    return Axios.get<IInvolvePeople[]>(`${baseApiUrl}/${processInstanceId}/identitylinks`);
  }

  /**
   * 增加流程参与人
   * @param id
   */
  public addPeople(people: IInvolvePeople, processInstanceId: string): Observable<InvolvePeopleResponseType> {
    return Axios.post<IInvolvePeople>(`${baseApiUrl}/${processInstanceId}/identitylinks`, people);
  }

  /**
   * 增加流程参与人
   * @param id
   */
  public removePeople(processInstanceId: string, userId: string, type: string): Observable<InvolvePeopleResponseType> {
    return Axios.delete<IInvolvePeople>(`${baseApiUrl}/${processInstanceId}/identitylinks/users/{userId}/{type}`);
  }

  /**
   * 获得流程所有变量的值
   * @param id
   */
  public findVariables(processInstanceId: string): Observable<InstanceVariableArrayResponseType> {
    return Axios.get<IInstanceVariable[]>(`${baseApiUrl}/${processInstanceId}/variables`);
  }

  /**
   * 获得指定名称变量的值
   * @param id
   */
  public getVariable(processInstanceId: string, variableName: string): Observable<InstanceVariableResponseType> {
    return Axios.get<IInstanceVariable>(`${baseApiUrl}/${processInstanceId}/variables/${variableName}`);
  }

  /**
   * 创建指定名称变量的值，变量名存在时409错误提示。
   * @param id
   */
  public createVariables(processInstanceId: string, variables: IInstanceVariable[]): Observable<InstanceVariableArrayResponseType> {
    return Axios.post<IInstanceVariable[]>(`${baseApiUrl}/${processInstanceId}/variables`, variables);
  }

  /**
   * 创建指定名称变量的值,变量名存在时会覆盖，不存在时会创建
   * @param id
   */
  public updateVariables(processInstanceId: string, variables: IInstanceVariable[]): Observable<InstanceVariableArrayResponseType> {
    return Axios.put<IInstanceVariable[]>(`${baseApiUrl}/${processInstanceId}/variables`, variables);
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
