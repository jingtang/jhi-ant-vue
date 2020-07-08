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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.aidriveall.cms.domain.CommonQuery} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.CommonQueryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /common-queries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommonQueryCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private ZonedDateTimeFilter lastModifiedTime;

    private LongFilter itemsId;

    private LongFilter modifierId;

    private LongFilter commonTableId;

    private StringFilter commonTableClazzName;

    public CommonQueryCriteria(){
    }

    public CommonQueryCriteria(CommonQueryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.lastModifiedTime = other.lastModifiedTime == null ? null : other.lastModifiedTime.copy();
        this.itemsId = other.itemsId == null ? null : other.itemsId.copy();
        this.modifierId = other.modifierId == null ? null : other.modifierId.copy();
        this.commonTableId = other.commonTableId == null ? null : other.commonTableId.copy();
        this.commonTableClazzName = other.commonTableClazzName == null ? null : other.commonTableClazzName.copy();
    }

    @Override
    public CommonQueryCriteria copy() {
        return new CommonQueryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ZonedDateTimeFilter getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(ZonedDateTimeFilter lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public LongFilter getItemsId() {
        return itemsId;
    }

    public void setItemsId(LongFilter itemsId) {
        this.itemsId = itemsId;
    }

    public LongFilter getModifierId() {
        return modifierId;
    }

    public void setModifierId(LongFilter modifierId) {
        this.modifierId = modifierId;
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

    public StringFilter getCommonTableClazzName() {
        return commonTableClazzName;
    }

    public void setCommonTableClazzName(StringFilter commonTableClazzName) {
        this.commonTableClazzName = commonTableClazzName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommonQueryCriteria that = (CommonQueryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(lastModifiedTime, that.lastModifiedTime) &&
            Objects.equals(itemsId, that.itemsId) &&
            Objects.equals(modifierId, that.modifierId) &&
            Objects.equals(commonTableId, that.commonTableId) &&
            Objects.equals(commonTableClazzName, that.commonTableClazzName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        lastModifiedTime,
        itemsId,
        modifierId,
        commonTableId,
        commonTableClazzName
        );
    }

    @Override
    public String toString() {
        return "CommonQueryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (lastModifiedTime != null ? "lastModifiedTime=" + lastModifiedTime + ", " : "") +
                (itemsId != null ? "itemsId=" + itemsId + ", " : "") +
                (modifierId != null ? "modifierId=" + modifierId + ", " : "") +
                (commonTableId != null ? "commonTableId=" + commonTableId + ", " : "") +
                (commonTableClazzName != null ? "commonTableClazzName=" + commonTableClazzName + ", " : "") +
            "}";
    }

}
