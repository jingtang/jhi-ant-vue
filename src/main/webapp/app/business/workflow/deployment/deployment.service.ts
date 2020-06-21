import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';
import { IProcessDefinitionList } from '@/shared/model/workflow/process-definition-list.model';
import { IProcessDefinition } from '@/shared/model/workflow/process-definition.model';
import { IFlowableList } from '@/shared/model/workflow/flowable-list.model';
import { IDeployment } from '@/shared/model/workflow/deployment.model';
import { IDeploymentResource } from '@/shared/model/workflow/deployment-resource.model';

const baseApiUrl = 'process-api/repository/deployments';

type EntityResponseType = AxiosResponse<IDeployment>;
type DeploymentResourceListType = AxiosResponse<IDeploymentResource[]>;
type DeploymentResourceType = AxiosResponse<IDeploymentResource>;
type VoidResponseType = AxiosResponse<void>;
type EntityArrayResponseType = AxiosResponse<IFlowableList<IDeployment>>;

export default class DeploymentService {
  /**
   * 部署一个流程
   * @param formData
   */
  public deployProcess(formData: FormData): Observable<EntityResponseType> {
    return Axios.post<IDeployment>(`${baseApiUrl}`, formData);
  }

  /**
   * 查询所有部署的流程
   * @param paginationQuery
   */
  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IFlowableList<IDeployment>>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 查找指定id的流程部署
   * @param id
   */
  public find(id: string): Observable<EntityResponseType> {
    return Axios.get<IDeployment>(`${baseApiUrl}/${id}`);
  }

  /**
   * 删除指定id的流程部署
   * @param id
   */
  public delete(id: string): Observable<VoidResponseType> {
    return Axios.delete<void>(`${baseApiUrl}/${id}`);
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
