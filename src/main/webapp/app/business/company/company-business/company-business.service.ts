import Axios from 'axios-observable';
import qs from 'qs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICompanyBusiness } from '@/shared/model/company/company-business.model';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';

const baseApiUrl = 'api/company-businesses';
type EntityResponseType = AxiosResponse<ICompanyBusiness>;
type EntityArrayResponseType = AxiosResponse<ICompanyBusiness[]>;

export default class CompanyBusinessService {
  public find(id: number): Observable<EntityResponseType> {
    return Axios.get<ICompanyBusiness>(`${baseApiUrl}/${id}`).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<ICompanyBusiness[]>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    }).pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  public delete(id: number): Observable<AxiosResponse> {
    return Axios.delete(`${baseApiUrl}/${id}`);
  }

  deleteByIds(ids: string[]): Observable<AxiosResponse> {
    return Axios.delete(`${baseApiUrl}`, qs.stringify({ ids }, { indices: false }));
  }

  public create(entity: ICompanyBusiness): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entity);
    return Axios.post(`${baseApiUrl}`, copy).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  public update(entity: ICompanyBusiness): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entity);
    return Axios.put(`${baseApiUrl}`, copy).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updateBySpecifiedFields(companyBusiness: ICompanyBusiness, specifiedFields: String[]): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(companyBusiness);
    return Axios.put(`${baseApiUrl}/specified-fields`, { companyBusiness: copy, specifiedFields }).pipe(
      map((res: EntityResponseType) => this.convertDateFromServer(res))
    );
  }

  updateBySpecifiedField(companyBusiness: ICompanyBusiness, specifiedField: String, paginationQuery?: any): Observable<EntityResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    const copy = this.convertDateFromClient(companyBusiness);
    return Axios.put(`${baseApiUrl}/specified-field`, { companyBusiness: copy, specifiedField });
  }

  protected convertDateFromClient(companyBusiness: ICompanyBusiness): ICompanyBusiness {
    const copy: ICompanyBusiness = Object.assign({}, companyBusiness, {
      expirationTime:
        companyBusiness.expirationTime != null && companyBusiness.expirationTime.isValid() ? companyBusiness.expirationTime.toJSON() : null,
      startTime: companyBusiness.startTime != null && companyBusiness.startTime.isValid() ? companyBusiness.startTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.data) {
      res.data.expirationTime = res.data.expirationTime != null ? moment(res.data.expirationTime) : null;
      res.data.startTime = res.data.startTime != null ? moment(res.data.startTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.data) {
      res.data.forEach((companyBusiness: ICompanyBusiness) => {
        companyBusiness.expirationTime = companyBusiness.expirationTime != null ? moment(companyBusiness.expirationTime) : null;
        companyBusiness.startTime = companyBusiness.startTime != null ? moment(companyBusiness.startTime) : null;
      });
    }
    return res;
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
