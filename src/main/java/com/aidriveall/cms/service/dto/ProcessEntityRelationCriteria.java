package com.aidriveall.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.aidriveall.cms.domain.enumeration.ProcessEntityStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.aidriveall.cms.domain.ProcessEntityRelation} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.ProcessEntityRelationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /process-entity-relations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessEntityRelationCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;
    /**
     * Class for filtering ProcessEntityStatus
     */
    public static class ProcessEntityStatusFilter extends Filter<ProcessEntityStatus> {

        public ProcessEntityStatusFilter() {
        }

        public ProcessEntityStatusFilter(ProcessEntityStatusFilter filter) {
            super(filter);
        }

        @Override
        public ProcessEntityStatusFilter copy() {
            return new ProcessEntityStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter entityType;

    private LongFilter entityId;

    private StringFilter processInstanceId;

    private ProcessEntityStatusFilter status;

    public ProcessEntityRelationCriteria(){
    }

    public ProcessEntityRelationCriteria(ProcessEntityRelationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.entityType = other.entityType == null ? null : other.entityType.copy();
        this.entityId = other.entityId == null ? null : other.entityId.copy();
        this.processInstanceId = other.processInstanceId == null ? null : other.processInstanceId.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public ProcessEntityRelationCriteria copy() {
        return new ProcessEntityRelationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEntityType() {
        return entityType;
    }

    public void setEntityType(StringFilter entityType) {
        this.entityType = entityType;
    }

    public LongFilter getEntityId() {
        return entityId;
    }

    public void setEntityId(LongFilter entityId) {
        this.entityId = entityId;
    }

    public StringFilter getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(StringFilter processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public ProcessEntityStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ProcessEntityStatusFilter status) {
        this.status = status;
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
        final ProcessEntityRelationCriteria that = (ProcessEntityRelationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(entityType, that.entityType) &&
            Objects.equals(entityId, that.entityId) &&
            Objects.equals(processInstanceId, that.processInstanceId) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        entityType,
        entityId,
        processInstanceId,
        status
        );
    }

    @Override
    public String toString() {
        return "ProcessEntityRelationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (entityType != null ? "entityType=" + entityType + ", " : "") +
                (entityId != null ? "entityId=" + entityId + ", " : "") +
                (processInstanceId != null ? "processInstanceId=" + processInstanceId + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
