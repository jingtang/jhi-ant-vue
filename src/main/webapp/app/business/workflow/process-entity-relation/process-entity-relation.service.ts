import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IProcessEntityRelation } from '@/shared/model/workflow/process-entity-relation.model';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';

const baseApiUrl = 'api/process-entity-relations';
type EntityResponseType = AxiosResponse<IProcessEntityRelation>;
type EntityArrayResponseType = AxiosResponse<IProcessEntityRelation[]>;

export default class ProcessEntityRelationService {
  public find(id: number): Observable<EntityResponseType> {
    return Axios.get<IProcessEntityRelation>(`${baseApiUrl}/${id}`);
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IProcessEntityRelation[]>(baseApiUrl, {
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

  public create(entity: IProcessEntityRelation): Observable<EntityResponseType> {
    return Axios.post(`${baseApiUrl}`, entity);
  }

  public update(entity: IProcessEntityRelation): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}`, entity);
  }

  updateBySpecifiedFields(processEntityRelation: IProcessEntityRelation, specifiedFields: String[]): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}/specified-fields`, { processEntityRelation, specifiedFields });
  }

  updateBySpecifiedField(
    processEntityRelation: IProcessEntityRelation,
    specifiedField: String,
    paginationQuery?: any
  ): Observable<EntityResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.put(`${baseApiUrl}/specified-field`, { processEntityRelation, specifiedField });
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
