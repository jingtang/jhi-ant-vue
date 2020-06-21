import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProcessTableConfig } from '@/shared/model/workflow/process-table-config.model';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';

const baseApiUrl = 'api/process-table-configs';
type EntityResponseType = AxiosResponse<IProcessTableConfig>;
type EntityArrayResponseType = AxiosResponse<IProcessTableConfig[]>;

export default class ProcessTableConfigService {
  public find(id: number): Observable<EntityResponseType> {
    return Axios.get<IProcessTableConfig>(`${baseApiUrl}/${id}`);
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IProcessTableConfig[]>(baseApiUrl, {
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

  public create(entity: IProcessTableConfig): Observable<EntityResponseType> {
    return Axios.post(`${baseApiUrl}`, entity);
  }

  public update(entity: IProcessTableConfig): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}`, entity);
  }

  updateBySpecifiedFields(processTableConfig: IProcessTableConfig, specifiedFields: String[]): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}/specified-fields`, { processTableConfig, specifiedFields });
  }

  updateBySpecifiedField(
    processTableConfig: IProcessTableConfig,
    specifiedField: String,
    paginationQuery?: any
  ): Observable<EntityResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.put(`${baseApiUrl}/specified-field`, { processTableConfig, specifiedField });
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
