import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICommonQueryItem } from '@/shared/model/commonQuery/common-query-item.model';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';

const baseApiUrl = 'api/common-query-items';
type EntityResponseType = AxiosResponse<ICommonQueryItem>;
type EntityArrayResponseType = AxiosResponse<ICommonQueryItem[]>;

export default class CommonQueryItemService {
  public find(id: number): Observable<EntityResponseType> {
    return Axios.get<ICommonQueryItem>(`${baseApiUrl}/${id}`);
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<ICommonQueryItem[]>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  public delete(id: number): Observable<AxiosResponse> {
    return Axios.delete(`${baseApiUrl}/${id}`);
  }

  deleteByIds(ids: string[]): Observable<AxiosResponse> {
    return Axios.delete(`${baseApiUrl}`, qs.stringify({ ids }, { indices: false }));
  }

  public create(entity: ICommonQueryItem): Observable<EntityResponseType> {
    return Axios.post(`${baseApiUrl}`, entity);
  }

  public update(entity: ICommonQueryItem): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}`, entity);
  }

  updateBySpecifiedFields(commonQueryItem: ICommonQueryItem, specifiedFields: String[]): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}/specified-fields`, { commonQueryItem, specifiedFields });
  }

  updateBySpecifiedField(commonQueryItem: ICommonQueryItem, specifiedField: String, paginationQuery?: any): Observable<EntityResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.put(`${baseApiUrl}/specified-field`, { commonQueryItem, specifiedField });
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
