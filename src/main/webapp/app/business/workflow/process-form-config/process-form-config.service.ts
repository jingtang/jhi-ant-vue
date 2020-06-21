import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProcessFormConfig } from '@/shared/model/workflow/process-form-config.model';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';

const baseApiUrl = 'api/process-form-configs';
type EntityResponseType = AxiosResponse<IProcessFormConfig>;
type EntityArrayResponseType = AxiosResponse<IProcessFormConfig[]>;

export default class ProcessFormConfigService {
  public find(id: number): Observable<EntityResponseType> {
    return Axios.get<IProcessFormConfig>(`${baseApiUrl}/${id}`);
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IProcessFormConfig[]>(baseApiUrl, {
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

  public create(entity: IProcessFormConfig): Observable<EntityResponseType> {
    return Axios.post(`${baseApiUrl}`, entity);
  }

  public update(entity: IProcessFormConfig): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}`, entity);
  }

  updateBySpecifiedFields(processFormConfig: IProcessFormConfig, specifiedFields: String[]): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}/specified-fields`, { processFormConfig, specifiedFields });
  }

  updateBySpecifiedField(
    processFormConfig: IProcessFormConfig,
    specifiedField: String,
    paginationQuery?: any
  ): Observable<EntityResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.put(`${baseApiUrl}/specified-field`, { processFormConfig, specifiedField });
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
