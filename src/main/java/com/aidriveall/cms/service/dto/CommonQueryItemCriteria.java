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
 * Criteria class for the {@link com.aidriveall.cms.domain.CommonQueryItem} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.CommonQueryItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /common-query-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommonQueryItemCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter prefix;

    private StringFilter fieldName;

    private StringFilter fieldType;

    private StringFilter operator;

    private StringFilter value;

    private StringFilter suffix;

    private IntegerFilter order;

    private LongFilter queryId;

    public CommonQueryItemCriteria(){
    }

    public CommonQueryItemCriteria(CommonQueryItemCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.prefix = other.prefix == null ? null : other.prefix.copy();
        this.fieldName = other.fieldName == null ? null : other.fieldName.copy();
        this.fieldType = other.fieldType == null ? null : other.fieldType.copy();
        this.operator = other.operator == null ? null : other.operator.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.suffix = other.suffix == null ? null : other.suffix.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.queryId = other.queryId == null ? null : other.queryId.copy();
    }

    @Override
    public CommonQueryItemCriteria copy() {
        return new CommonQueryItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPrefix() {
        return prefix;
    }

    public void setPrefix(StringFilter prefix) {
        this.prefix = prefix;
    }

    public StringFilter getFieldName() {
        return fieldName;
    }

    public void setFieldName(StringFilter fieldName) {
        this.fieldName = fieldName;
    }

    public StringFilter getFieldType() {
        return fieldType;
    }

    public void setFieldType(StringFilter fieldType) {
        this.fieldType = fieldType;
    }

    public StringFilter getOperator() {
        return operator;
    }

    public void setOperator(StringFilter operator) {
        this.operator = operator;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public StringFilter getSuffix() {
        return suffix;
    }

    public void setSuffix(StringFilter suffix) {
        this.suffix = suffix;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public LongFilter getQueryId() {
        return queryId;
    }

    public void setQueryId(LongFilter queryId) {
        this.queryId = queryId;
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
        final CommonQueryItemCriteria that = (CommonQueryItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(prefix, that.prefix) &&
            Objects.equals(fieldName, that.fieldName) &&
            Objects.equals(fieldType, that.fieldType) &&
            Objects.equals(operator, that.operator) &&
            Objects.equals(value, that.value) &&
            Objects.equals(suffix, that.suffix) &&
            Objects.equals(order, that.order) &&
            Objects.equals(queryId, that.queryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        prefix,
        fieldName,
        fieldType,
        operator,
        value,
        suffix,
        order,
        queryId
        );
    }

    @Override
    public String toString() {
        return "CommonQueryItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (prefix != null ? "prefix=" + prefix + ", " : "") +
                (fieldName != null ? "fieldName=" + fieldName + ", " : "") +
                (fieldType != null ? "fieldType=" + fieldType + ", " : "") +
                (operator != null ? "operator=" + operator + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (suffix != null ? "suffix=" + suffix + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (queryId != null ? "queryId=" + queryId + ", " : "") +
            "}";
    }

}
