import Axios from 'axios-observable';
import qs from 'qs';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { AxiosResponse } from 'axios';
import { Observable } from 'rxjs';
import { IProcessDefinitionList } from '@/shared/model/workflow/process-definition-list.model';
import { IProcessDefinition } from '@/shared/model/workflow/process-definition.model';
import { IFlowableList } from '@/shared/model/workflow/flowable-list.model';

const baseApiUrl = 'process-api/repository/process-definitions';

type EntityResponseType = AxiosResponse<IProcessDefinition>;
type EntityArrayResponseType = AxiosResponse<IFlowableList<IProcessDefinition>>;

export default class ProcessDefinitionService {
  public find(processDefinitionId: string): Observable<EntityResponseType> {
    return Axios.get<IProcessDefinition>(`${baseApiUrl}/${processDefinitionId}`);
  }

  public retrieve(paginationQuery?: any): Observable<EntityArrayResponseType> {
    const options = buildPaginationQueryOpts(paginationQuery);
    return Axios.get<IProcessDefinitionList>(baseApiUrl, {
      params: options,
      paramsSerializer: function(params) {
        return qs.stringify(params, { arrayFormat: 'repeat' });
      }
    });
  }

  public update(processDefinition: IProcessDefinition): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}/${processDefinition.id}`, processDefinition);
  }

  // todo
  public suspend(processDefinition: IProcessDefinition): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}/${processDefinition.id}`, processDefinition);
  }

  // todo
  public activate(processDefinition: IProcessDefinition): Observable<EntityResponseType> {
    return Axios.put(`${baseApiUrl}/${processDefinition.id}`, processDefinition);
  }

  // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove
}
