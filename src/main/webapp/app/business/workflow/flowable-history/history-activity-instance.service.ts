import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';
import { IFlowableList } from '@/shared/model/workflow/flowable-list.model';
import { IHistoryActivityInstance } from '@/shared/model/workflow/history-activity-instance.model';

const baseApiUrl = 'process-api/history/historic-task-instances';
const baseQueryUrl = 'process-api/query/historic-task-instances';
type EntityResponseType = AxiosResponse<IHistoryActivityInstance>;
type EntityArrayResponseType = AxiosResponse<IFlowableList<IHistoryActivityInstance>>;

export default class HistoryActivityInstanceService {
  /**
   * 查找指定id的历史活动
   * @param taskId
   */
  public find(taskId: string): Observable<EntityResponseType> {
    return Axios.get<IHistoryActivityInstance>(`${baseApiUrl}/${taskId}`);
  }

  /**
   * 查询所有的历史活动
   * @param paginationQuery
   */
  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IFlowableList<IHistoryActivityInstance>>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 复合查询所有的历史活动
   * @param paginationQuery
   * @param queryData
   */
  public query(paginationQuery?: any, queryData?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.post<IFlowableList<IHistoryActivityInstance>>(baseQueryUrl, queryData, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }
}
