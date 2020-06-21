import Axios from 'axios-observable';
import qs from 'qs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICompanyCustomer } from '@/shared/model/company/company-customer.model';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';

const baseApiUrl = 'api/company-customers';
type EntityResponseType = AxiosResponse<ICompanyCustomer>;
type EntityArrayResponseType = AxiosResponse<ICompanyCustomer[]>;

export default class CompanyCustomerService {
  public find(id: number): Observable<EntityResponseType> {
    return Axios.get<ICompanyCustomer>(`${baseApiUrl}/${id}`).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<ICompanyCustomer[]>(baseApiUrl, {
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

  tree(): Observable<EntityArrayResponseType> {
    return Axios.get(`${baseApiUrl}/tree`).pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  public create(entity: ICompanyCustomer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entity);
    return Axios.post(`${baseApiUrl}`, copy).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  public update(entity: ICompanyCustomer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entity);
    return Axios.put(`${baseApiUrl}`, copy).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updateBySpecifiedFields(companyCustomer: ICompanyCustomer, specifiedFields: String[]): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(companyCustomer);
    return Axios.put(`${baseApiUrl}/specified-fields`, { companyCustomer: copy, specifiedFields }).pipe(
      map((res: EntityResponseType) => this.convertDateFromServer(res))
    );
  }

  updateBySpecifiedField(companyCustomer: ICompanyCustomer, specifiedField: String, paginationQuery?: any): Observable<EntityResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    const copy = this.convertDateFromClient(companyCustomer);
    return Axios.put(`${baseApiUrl}/specified-field`, { companyCustomer: copy, specifiedField });
  }

  protected convertDateFromClient(companyCustomer: ICompanyCustomer): ICompanyCustomer {
    const copy: ICompanyCustomer = Object.assign({}, companyCustomer, {
      createTime: companyCustomer.createTime != null && companyCustomer.createTime.isValid() ? companyCustomer.createTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.data) {
      res.data.createTime = res.data.createTime != null ? moment(res.data.createTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.data) {
      res.data.forEach((companyCustomer: ICompanyCustomer) => {
        companyCustomer.createTime = companyCustomer.createTime != null ? moment(companyCustomer.createTime) : null;
        if (companyCustomer.children && companyCustomer.children.length > 0) {
          const children = Object.assign({}, res, { data: companyCustomer.children });
          companyCustomer.children = this.convertDateArrayFromServer(children).data;
        }
      });
    }
    return res;
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
