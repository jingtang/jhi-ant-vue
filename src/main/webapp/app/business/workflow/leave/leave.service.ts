import Axios from 'axios-observable';
import qs from 'qs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
// import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { ILeave } from '@/shared/model/workflow/leave.model';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';

const baseApiUrl = 'api/leaves';
type EntityResponseType = AxiosResponse<ILeave>;
type EntityArrayResponseType = AxiosResponse<ILeave[]>;

export default class LeaveService {
  public find(id: number): Observable<EntityResponseType> {
    return Axios.get<ILeave>(`${baseApiUrl}/${id}`).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<ILeave[]>(baseApiUrl, {
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

  public create(entity: ILeave): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entity);
    return Axios.post(`${baseApiUrl}`, copy).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  public update(entity: ILeave): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entity);
    return Axios.put(`${baseApiUrl}`, copy).pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updateBySpecifiedFields(leave: ILeave, specifiedFields: String[]): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leave);
    return Axios.put(`${baseApiUrl}/specified-fields`, { leave: copy, specifiedFields }).pipe(
      map((res: EntityResponseType) => this.convertDateFromServer(res))
    );
  }

  updateBySpecifiedField(leave: ILeave, specifiedField: String, paginationQuery?: any): Observable<EntityResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    const copy = this.convertDateFromClient(leave);
    return Axios.put(`${baseApiUrl}/specified-field`, { leave: copy, specifiedField });
  }

  protected convertDateFromClient(leave: ILeave): ILeave {
    const copy: ILeave = Object.assign({}, leave, {
      createTime: leave.createTime != null && leave.createTime.isValid() ? leave.createTime.toJSON() : null,
      startTime: leave.startTime != null && leave.startTime.isValid() ? leave.startTime.toJSON() : null,
      endTime: leave.endTime != null && leave.endTime.isValid() ? leave.endTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.data) {
      res.data.createTime = res.data.createTime != null ? moment(res.data.createTime) : null;
      res.data.startTime = res.data.startTime != null ? moment(res.data.startTime) : null;
      res.data.endTime = res.data.endTime != null ? moment(res.data.endTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.data) {
      res.data.forEach((leave: ILeave) => {
        leave.createTime = leave.createTime != null ? moment(leave.createTime) : null;
        leave.startTime = leave.startTime != null ? moment(leave.startTime) : null;
        leave.endTime = leave.endTime != null ? moment(leave.endTime) : null;
      });
    }
    return res;
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
