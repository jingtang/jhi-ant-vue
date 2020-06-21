import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';
import { IFlowableList } from '@/shared/model/workflow/flowable-list.model';
import { IDeploymentResource } from '@/shared/model/workflow/deployment-resource.model';
import { IInstanceVariable } from '@/shared/model/workflow/instance-variable.model';
import { IHistoryProcessInstance } from '@/shared/model/workflow/history-process-instance.model';
import { IHistoryTaskInstance } from '@/shared/model/workflow/history-task-instance.model';

const baseApiUrl = 'process-api/history/historic-task-instances';
const baseQueryUrl = 'process-api/query/historic-task-instances';
type EntityResponseType = AxiosResponse<IHistoryTaskInstance>;
type EntityArrayResponseType = AxiosResponse<IFlowableList<IHistoryTaskInstance>>;

type DeploymentResourceListType = AxiosResponse<IDeploymentResource[]>;
type DeploymentResourceType = AxiosResponse<IDeploymentResource>;
type VoidResponseType = AxiosResponse<void>;
type InstanceVariableArrayResponseType = AxiosResponse<IInstanceVariable[]>;
type InstanceVariableResponseType = AxiosResponse<IInstanceVariable>;

export default class HistoryTaskInstanceService {
  /**
   * 查找指定id的历史任务
   * @param taskId
   */
  public find(taskId: string): Observable<EntityResponseType> {
    return Axios.get<IHistoryTaskInstance>(`${baseApiUrl}/${taskId}`);
  }

  /**
   * 查询所有的历史任务
   * @param paginationQuery
   */
  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IFlowableList<IHistoryTaskInstance>>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 复合查询所有的历史任务
   * @param paginationQuery
   * @param queryData
   */
  public query(paginationQuery?: any, queryData?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.post<IFlowableList<IHistoryTaskInstance>>(baseQueryUrl, queryData, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 删除指定id的历史任务
   * @param taskId
   */
  public delete(taskId: string): Observable<VoidResponseType> {
    return Axios.delete<void>(`${baseApiUrl}/${taskId}`);
  }
}
