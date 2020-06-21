import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICompanyUser } from '@/shared/model/company/company-user.model';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';

const baseApiUrl = 'api/company-users';
type EntityResponseType = AxiosResponse<ICompanyUser>;
type EntityArrayResponseType = AxiosResponse<ICompanyUser[]>;

export default class CompanyUserService {
  public find(id: number): Observable<EntityResponseType> {
    return Axios.get<ICompanyUser>(`${baseApiUrl}/${id}`);
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<ICompanyUser[]>(baseApiUrl, {
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

  public create(entity: ICompanyUser): Observable<EntityResponseType> {
    return Axios.post(`${baseApiUrl}`, entity);
  }

  public update(entity: ICompanyUser): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}`, entity);
  }

  updateBySpecifiedFields(companyUser: ICompanyUser, specifiedFields: String[]): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}/specified-fields`, { companyUser, specifiedFields });
  }

  updateBySpecifiedField(companyUser: ICompanyUser, specifiedField: String, paginationQuery?: any): Observable<EntityResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.put(`${baseApiUrl}/specified-field`, { companyUser, specifiedField });
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
