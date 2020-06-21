import Axios from 'axios-observable';
import qs from 'qs';
import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';
import buildPaginationQueryOpts from '@/shared/sort/sorts';
import { IUser } from '@/shared/model/user.model';
const baseApiUrl = 'api/users';
type EntityResponseType = AxiosResponse<IUser>;
type EntityArrayResponseType = AxiosResponse<IUser[]>;

export default class UserService {
  public get(userId: number): Observable<EntityResponseType> {
    return Axios.get<IUser>(`${baseApiUrl}/${userId}`);
  }

  public create(user): Observable<EntityResponseType> {
    return Axios.post<IUser>(`${baseApiUrl}`, user);
  }

  public update(user): Observable<EntityResponseType> {
    return Axios.put('api/users', user);
  }

  public remove(userId: number): Observable<AxiosResponse> {
    return Axios.delete(`api/users/${userId}`);
  }

  deleteByIds(ids: string[]): Observable<AxiosResponse> {
    return Axios.delete(`${baseApiUrl}`, qs.stringify({ ids }, { indices: false }));
  }

  public retrieve(req?: any): Observable<EntityArrayResponseType> {
    return Axios.get(`api/users?${buildPaginationQueryOpts(req)}`);
  }

  public retrieveAuthorities(): Observable<AxiosResponse> {
    return Axios.get('api/users/authorities');
  }
}
