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
 * Criteria class for the {@link com.aidriveall.cms.domain.Leave} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.LeaveResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leaves?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeaveCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter createTime;

    private StringFilter name;

    private IntegerFilter days;

    private ZonedDateTimeFilter startTime;

    private ZonedDateTimeFilter endTime;

    private StringFilter reason;

    private LongFilter imagesId;

    private LongFilter creatorId;

    public LeaveCriteria(){
    }

    public LeaveCriteria(LeaveCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.createTime = other.createTime == null ? null : other.createTime.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.days = other.days == null ? null : other.days.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.reason = other.reason == null ? null : other.reason.copy();
        this.imagesId = other.imagesId == null ? null : other.imagesId.copy();
        this.creatorId = other.creatorId == null ? null : other.creatorId.copy();
    }

    @Override
    public LeaveCriteria copy() {
        return new LeaveCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTimeFilter createTime) {
        this.createTime = createTime;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getDays() {
        return days;
    }

    public void setDays(IntegerFilter days) {
        this.days = days;
    }

    public ZonedDateTimeFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTimeFilter startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTimeFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTimeFilter endTime) {
        this.endTime = endTime;
    }

    public StringFilter getReason() {
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public LongFilter getImagesId() {
        return imagesId;
    }

    public void setImagesId(LongFilter imagesId) {
        this.imagesId = imagesId;
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
        final LeaveCriteria that = (LeaveCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(name, that.name) &&
            Objects.equals(days, that.days) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(imagesId, that.imagesId) &&
            Objects.equals(creatorId, that.creatorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createTime,
        name,
        days,
        startTime,
        endTime,
        reason,
        imagesId,
        creatorId
        );
    }

    @Override
    public String toString() {
        return "LeaveCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (days != null ? "days=" + days + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (imagesId != null ? "imagesId=" + imagesId + ", " : "") +
                (creatorId != null ? "creatorId=" + creatorId + ", " : "") +
            "}";
    }

}
