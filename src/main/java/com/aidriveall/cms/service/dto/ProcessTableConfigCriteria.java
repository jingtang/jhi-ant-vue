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
 * Criteria class for the {@link com.aidriveall.cms.domain.ProcessTableConfig} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.ProcessTableConfigResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /process-table-configs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProcessTableConfigCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter processDefinitionId;

    private StringFilter processDefinitionKey;

    private StringFilter processDefinitionName;

    private StringFilter description;

    private BooleanFilter deploied;

    private LongFilter commonTableId;

    private LongFilter creatorId;

    public ProcessTableConfigCriteria(){
    }

    public ProcessTableConfigCriteria(ProcessTableConfigCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.processDefinitionId = other.processDefinitionId == null ? null : other.processDefinitionId.copy();
        this.processDefinitionKey = other.processDefinitionKey == null ? null : other.processDefinitionKey.copy();
        this.processDefinitionName = other.processDefinitionName == null ? null : other.processDefinitionName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.deploied = other.deploied == null ? null : other.deploied.copy();
        this.commonTableId = other.commonTableId == null ? null : other.commonTableId.copy();
        this.creatorId = other.creatorId == null ? null : other.creatorId.copy();
    }

    @Override
    public ProcessTableConfigCriteria copy() {
        return new ProcessTableConfigCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(StringFilter processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public StringFilter getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(StringFilter processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public StringFilter getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(StringFilter processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getDeploied() {
        return deploied;
    }

    public void setDeploied(BooleanFilter deploied) {
        this.deploied = deploied;
    }

    public LongFilter getCommonTableId() {
        return commonTableId;
    }

    public void setCommonTableId(LongFilter commonTableId) {
        this.commonTableId = commonTableId;
    }

    public LongFilter getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(LongFilter creatorId) {
        this.creatorId = creatorId;
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
        final ProcessTableConfigCriteria that = (ProcessTableConfigCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(processDefinitionId, that.processDefinitionId) &&
            Objects.equals(processDefinitionKey, that.processDefinitionKey) &&
            Objects.equals(processDefinitionName, that.processDefinitionName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(deploied, that.deploied) &&
            Objects.equals(commonTableId, that.commonTableId) &&
            Objects.equals(creatorId, that.creatorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        processDefinitionId,
        processDefinitionKey,
        processDefinitionName,
        description,
        deploied,
        commonTableId,
        creatorId
        );
    }

    @Override
    public String toString() {
        return "ProcessTableConfigCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (processDefinitionId != null ? "processDefinitionId=" + processDefinitionId + ", " : "") +
                (processDefinitionKey != null ? "processDefinitionKey=" + processDefinitionKey + ", " : "") +
                (processDefinitionName != null ? "processDefinitionName=" + processDefinitionName + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (deploied != null ? "deploied=" + deploied + ", " : "") +
                (commonTableId != null ? "commonTableId=" + commonTableId + ", " : "") +
                (creatorId != null ? "creatorId=" + creatorId + ", " : "") +
            "}";
    }

}
