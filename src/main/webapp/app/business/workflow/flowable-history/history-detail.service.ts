import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';
import { IFlowableList } from '@/shared/model/workflow/flowable-list.model';
import { IHistoryActivityInstance } from '@/shared/model/workflow/history-activity-instance.model';
import { IHistoryDetail } from '@/shared/model/workflow/history-detail.model';

const baseApiUrl = 'process-api/history/historic-detail';
const baseQueryUrl = 'process-api/query/historic-detail';
type EntityResponseType = AxiosResponse<IHistoryDetail>;
type EntityArrayResponseType = AxiosResponse<IFlowableList<IHistoryDetail>>;

export default class HistoryDetailService {
  /**
   * 查询所有的历史细节
   * @param paginationQuery
   */
  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IFlowableList<IHistoryDetail>>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  /**
   * 复合查询所有的历史细节
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
