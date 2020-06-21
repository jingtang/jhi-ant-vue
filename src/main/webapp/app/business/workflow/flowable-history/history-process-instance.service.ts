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
import { IHistoryProcessInstance } from '@/shared/model/workflow/history-process-instance.model';

const baseApiUrl = 'process-api/history/historic-process-instances';
const baseQueryUrl = 'process-api/query/historic-process-instances';
type EntityResponseType = AxiosResponse<IHistoryProcessInstance>;
type EntityArrayResponseType = AxiosResponse<IFlowableList<IHistoryProcessInstance>>;

type DeploymentResourceListType = AxiosResponse<IDeploymentResource[]>;
type DeploymentResourceType = AxiosResponse<IDeploymentResource>;
type VoidResponseType = AxiosResponse<void>;
type InstanceVariableArrayResponseType = AxiosResponse<IInstanceVariable[]>;
type InstanceVariableResponseType = AxiosResponse<IInstanceVariable>;

export default class HistoryProcessInstanceService {
  /**
   * 查找指定id的历史流程
   * @param processInstanceId
   */
  public find(processInstanceId: string): Observable<EntityResponseType> {
    return Axios.get<IHistoryProcessInstance>(`${baseApiUrl}/${processInstanceId}`);
  }

  /**
   * 查询所有的历史流程
   * @param paginationQuery
   */
  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IFlowableList<IHistoryProcessInstance>>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 复合查询所有的历史流程
   * @param paginationQuery
   * @param queryData
   */
  public query(paginationQuery?: any, queryData?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.post<IFlowableList<IHistoryProcessInstance>>(baseQueryUrl, queryData, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 删除指定id的历史流程
   * @param processInstanceId
   */
  public delete(processInstanceId: string): Observable<VoidResponseType> {
    return Axios.delete<void>(`${baseApiUrl}/${processInstanceId}`);
  }
}
