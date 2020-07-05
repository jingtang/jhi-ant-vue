import Axios from 'axios-observable';
import qs from 'qs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ICommonQuery } from '@/shared/model/commonQuery/common-query.model';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';

const baseApiUrl = 'api/common-queries';
type EntityResponseType = AxiosResponse<ICommonQuery>;
type EntityArrayResponseType = AxiosResponse<ICommonQuery[]>;

export default class CommonQueryService {
  public find(id: number): Observable<EntityResponseType> {
    return Axios.get<ICommonQuery>(`${baseApiUrl}/${id}`).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<ICommonQuery[]>(baseApiUrl, {
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

  public create(entity: ICommonQuery): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entity);
    return Axios.post(`${baseApiUrl}`, copy).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  public update(entity: ICommonQuery): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entity);
    return Axios.put(`${baseApiUrl}`, copy).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updateBySpecifiedFields(commonQuery: ICommonQuery, specifiedFields: String[]): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commonQuery);
    return Axios.put(`${baseApiUrl}/specified-fields`, { commonQuery: copy, specifiedFields }).pipe(
      map((res: EntityResponseType) => this.convertDateFromServer(res))
    );
  }

  updateBySpecifiedField(commonQuery: ICommonQuery, specifiedField: String, paginationQuery?: any): Observable<EntityResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    const copy = this.convertDateFromClient(commonQuery);
    return Axios.put(`${baseApiUrl}/specified-field`, { commonQuery: copy, specifiedField });
  }

  protected convertDateFromClient(commonQuery: ICommonQuery): ICommonQuery {
    const copy: ICommonQuery = Object.assign({}, commonQuery, {
      lastModifiedTime:
        commonQuery.lastModifiedTime != null && commonQuery.lastModifiedTime.isValid() ? commonQuery.lastModifiedTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.data) {
      res.data.lastModifiedTime = res.data.lastModifiedTime != null ? moment(res.data.lastModifiedTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.data) {
      res.data.forEach((commonQuery: ICommonQuery) => {
        commonQuery.lastModifiedTime = commonQuery.lastModifiedTime != null ? moment(commonQuery.lastModifiedTime) : null;
      });
    }
    return res;
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
