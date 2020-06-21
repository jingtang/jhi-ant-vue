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
 * Criteria class for the {@link com.aidriveall.cms.domain.CommonExtData} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.CommonExtDataResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /common-ext-data?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommonExtDataCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fieldName;

    private StringFilter commonFieldType;

    private LongFilter commonFieldId;

    private StringFilter ownerType;

    private LongFilter ownerId;

    public CommonExtDataCriteria(){
    }

    public CommonExtDataCriteria(CommonExtDataCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fieldName = other.fieldName == null ? null : other.fieldName.copy();
        this.commonFieldType = other.commonFieldType == null ? null : other.commonFieldType.copy();
        this.commonFieldId = other.commonFieldId == null ? null : other.commonFieldId.copy();
        this.ownerType = other.ownerType == null ? null : other.ownerType.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
    }

    @Override
    public CommonExtDataCriteria copy() {
        return new CommonExtDataCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFieldName() {
        return fieldName;
    }

    public void setFieldName(StringFilter fieldName) {
        this.fieldName = fieldName;
    }

    public StringFilter getCommonFieldType() {
        return commonFieldType;
    }

    public void setCommonFieldType(StringFilter commonFieldType) {
        this.commonFieldType = commonFieldType;
    }

    public LongFilter getCommonFieldId() {
        return commonFieldId;
    }

    public void setCommonFieldId(LongFilter commonFieldId) {
        this.commonFieldId = commonFieldId;
    }

    public StringFilter getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(StringFilter ownerType) {
        this.ownerType = ownerType;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
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
        final CommonExtDataCriteria that = (CommonExtDataCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fieldName, that.fieldName) &&
            Objects.equals(commonFieldType, that.commonFieldType) &&
            Objects.equals(commonFieldId, that.commonFieldId) &&
            Objects.equals(ownerType, that.ownerType) &&
            Objects.equals(ownerId, that.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fieldName,
        commonFieldType,
        commonFieldId,
        ownerType,
        ownerId
        );
    }

    @Override
    public String toString() {
        return "CommonExtDataCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fieldName != null ? "fieldName=" + fieldName + ", " : "") +
                (commonFieldType != null ? "commonFieldType=" + commonFieldType + ", " : "") +
                (commonFieldId != null ? "commonFieldId=" + commonFieldId + ", " : "") +
                (ownerType != null ? "ownerType=" + ownerType + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
            "}";
    }

}
