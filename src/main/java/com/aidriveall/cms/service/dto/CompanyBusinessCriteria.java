package com.aidriveall.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.aidriveall.cms.domain.enumeration.CompanyBusinessStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.aidriveall.cms.domain.CompanyBusiness} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.CompanyBusinessResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /company-businesses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyBusinessCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;
    /**
     * Class for filtering CompanyBusinessStatus
     */
    public static class CompanyBusinessStatusFilter extends Filter<CompanyBusinessStatus> {

        public CompanyBusinessStatusFilter() {
        }

        public CompanyBusinessStatusFilter(CompanyBusinessStatusFilter filter) {
            super(filter);
        }

        @Override
        public CompanyBusinessStatusFilter copy() {
            return new CompanyBusinessStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private CompanyBusinessStatusFilter status;

    private ZonedDateTimeFilter expirationTime;

    private ZonedDateTimeFilter startTime;

    private LongFilter operateUserId;

    private StringFilter groupId;

    private LongFilter businessTypeId;

    private LongFilter companyId;

    public CompanyBusinessCriteria(){
    }

    public CompanyBusinessCriteria(CompanyBusinessCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.expirationTime = other.expirationTime == null ? null : other.expirationTime.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.operateUserId = other.operateUserId == null ? null : other.operateUserId.copy();
        this.groupId = other.groupId == null ? null : other.groupId.copy();
        this.businessTypeId = other.businessTypeId == null ? null : other.businessTypeId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
    }

    @Override
    public CompanyBusinessCriteria copy() {
        return new CompanyBusinessCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public CompanyBusinessStatusFilter getStatus() {
        return status;
    }

    public void setStatus(CompanyBusinessStatusFilter status) {
        this.status = status;
    }

    public ZonedDateTimeFilter getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(ZonedDateTimeFilter expirationTime) {
        this.expirationTime = expirationTime;
    }

    public ZonedDateTimeFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTimeFilter startTime) {
        this.startTime = startTime;
    }

    public LongFilter getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(LongFilter operateUserId) {
        this.operateUserId = operateUserId;
    }

    public StringFilter getGroupId() {
        return groupId;
    }

    public void setGroupId(StringFilter groupId) {
        this.groupId = groupId;
    }

    public LongFilter getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(LongFilter businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
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
        final CompanyBusinessCriteria that = (CompanyBusinessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(expirationTime, that.expirationTime) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(operateUserId, that.operateUserId) &&
            Objects.equals(groupId, that.groupId) &&
            Objects.equals(businessTypeId, that.businessTypeId) &&
            Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        expirationTime,
        startTime,
        operateUserId,
        groupId,
        businessTypeId,
        companyId
        );
    }

    @Override
    public String toString() {
        return "CompanyBusinessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (expirationTime != null ? "expirationTime=" + expirationTime + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (operateUserId != null ? "operateUserId=" + operateUserId + ", " : "") +
                (groupId != null ? "groupId=" + groupId + ", " : "") +
                (businessTypeId != null ? "businessTypeId=" + businessTypeId + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
            "}";
    }

}
