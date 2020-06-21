package com.aidriveall.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.aidriveall.cms.domain.ProcessFormConfig} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.ProcessFormConfigResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /process-form-configs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessFormConfigCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter processDefinitionKey;

    private StringFilter taskNodeId;

    private StringFilter taskNodeName;

    private LongFilter commonTableId;

    public ProcessFormConfigCriteria(){
    }

    public ProcessFormConfigCriteria(ProcessFormConfigCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.processDefinitionKey = other.processDefinitionKey == null ? null : other.processDefinitionKey.copy();
        this.taskNodeId = other.taskNodeId == null ? null : other.taskNodeId.copy();
        this.taskNodeName = other.taskNodeName == null ? null : other.taskNodeName.copy();
        this.commonTableId = other.commonTableId == null ? null : other.commonTableId.copy();
    }

    @Override
    public ProcessFormConfigCriteria copy() {
        return new ProcessFormConfigCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(StringFilter processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public StringFilter getTaskNodeId() {
        return taskNodeId;
    }

    public void setTaskNodeId(StringFilter taskNodeId) {
        this.taskNodeId = taskNodeId;
    }

    public StringFilter getTaskNodeName() {
        return taskNodeName;
    }

    public void setTaskNodeName(StringFilter taskNodeName) {
        this.taskNodeName = taskNodeName;
    }

    public LongFilter getCommonTableId() {
        return commonTableId;
    }

    public void setCommonTableId(LongFilter commonTableId) {
        this.commonTableId = commonTableId;
    }

    public String getJhiCommonSearchKeywords() {
        return jhiCommonSearchKeywords;
    }

    public void setJhiCommonSearchKeywords(String jhiCommonSearchKeywords) {
        this.jhiCommonSearchKeywords = jhiCommonSearchKeywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProcessFormConfigCriteria that = (ProcessFormConfigCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(processDefinitionKey, that.processDefinitionKey) &&
            Objects.equals(taskNodeId, that.taskNodeId) &&
            Objects.equals(taskNodeName, that.taskNodeName) &&
            Objects.equals(commonTableId, that.commonTableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        processDefinitionKey,
        taskNodeId,
        taskNodeName,
        commonTableId
        );
    }

    @Override
    public String toString() {
        return "ProcessFormConfigCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (processDefinitionKey != null ? "processDefinitionKey=" + processDefinitionKey + ", " : "") +
                (taskNodeId != null ? "taskNodeId=" + taskNodeId + ", " : "") +
                (taskNodeName != null ? "taskNodeName=" + taskNodeName + ", " : "") +
                (commonTableId != null ? "commonTableId=" + commonTableId + ", " : "") +
            "}";
    }

}
