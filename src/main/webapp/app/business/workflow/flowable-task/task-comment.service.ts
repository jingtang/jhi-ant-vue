import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';
import { IDeploymentResource } from '@/shared/model/workflow/deployment-resource.model';
import { IInstanceVariable } from '@/shared/model/workflow/instance-variable.model';
import { ITaskComment } from '@/shared/model/workflow/task-comment.model';

const baseApiUrl = 'process-api/runtime/tasks';
const baseQueryUrl = 'process-api/query/tasks';
type EntityResponseType = AxiosResponse<ITaskComment>;
type EntityArrayResponseType = AxiosResponse<ITaskComment[]>;
type DeploymentResourceListType = AxiosResponse<IDeploymentResource[]>;
type DeploymentResourceType = AxiosResponse<IDeploymentResource>;
type VoidResponseType = AxiosResponse<void>;
type InstanceVariableArrayResponseType = AxiosResponse<IInstanceVariable[]>;
type InstanceVariableResponseType = AxiosResponse<IInstanceVariable>;

export default class TaskCommentService {
  /**
   * 创建一个评论
   * @param taskId
   * @param comment
   */
  public create(taskId: string, comment: any) {
    return Axios.post<ITaskComment>(`${baseApiUrl}/${taskId}/comments`, comment);
  }

  /**
   * 查询指定任务的所有评论
   * @param taskId
   */
  public findAllByTaskId(taskId: string): Observable<EntityArrayResponseType> {
    return Axios.get<ITaskComment[]>(`${baseApiUrl}/${taskId}/comments`);
  }

  /**
   * 查询指定任务的指定评论
   * @param taskId
   * @param commentId
   */
  public find(taskId: string, commentId: string): Observable<EntityResponseType> {
    return Axios.get<ITaskComment>(`${baseApiUrl}/${taskId}/comments/${commentId}`);
  }

  /**
   * 删除指定id的任务
   * @param taskId
   * @param cascadeHistory
   * @param deleteReason
   */
  public delete(taskId: string, cascadeHistory?: boolean, deleteReason?: string): Observable<VoidResponseType> {
    return Axios.delete<void>(`${baseApiUrl}/${taskId}?cascadeHistory=${cascadeHistory}&deleteReason=${deleteReason}`);
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
