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
 * Criteria class for the {@link com.aidriveall.cms.domain.AdministrativeDivision} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.AdministrativeDivisionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /administrative-divisions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdministrativeDivisionCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter areaCode;

    private StringFilter cityCode;

    private StringFilter mergerName;

    private StringFilter shortName;

    private StringFilter zipCode;

    private IntegerFilter level;

    private DoubleFilter lng;

    private DoubleFilter lat;

    private LongFilter childrenId;

    private LongFilter parentId;

    public AdministrativeDivisionCriteria(){
    }

    public AdministrativeDivisionCriteria(AdministrativeDivisionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.areaCode = other.areaCode == null ? null : other.areaCode.copy();
        this.cityCode = other.cityCode == null ? null : other.cityCode.copy();
        this.mergerName = other.mergerName == null ? null : other.mergerName.copy();
        this.shortName = other.shortName == null ? null : other.shortName.copy();
        this.zipCode = other.zipCode == null ? null : other.zipCode.copy();
        this.level = other.level == null ? null : other.level.copy();
        this.lng = other.lng == null ? null : other.lng.copy();
        this.lat = other.lat == null ? null : other.lat.copy();
        this.childrenId = other.childrenId == null ? null : other.childrenId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public AdministrativeDivisionCriteria copy() {
        return new AdministrativeDivisionCriteria(this);
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

    public StringFilter getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(StringFilter areaCode) {
        this.areaCode = areaCode;
    }

    public StringFilter getCityCode() {
        return cityCode;
    }

    public void setCityCode(StringFilter cityCode) {
        this.cityCode = cityCode;
    }

    public StringFilter getMergerName() {
        return mergerName;
    }

    public void setMergerName(StringFilter mergerName) {
        this.mergerName = mergerName;
    }

    public StringFilter getShortName() {
        return shortName;
    }

    public void setShortName(StringFilter shortName) {
        this.shortName = shortName;
    }

    public StringFilter getZipCode() {
        return zipCode;
    }

    public void setZipCode(StringFilter zipCode) {
        this.zipCode = zipCode;
    }

    public IntegerFilter getLevel() {
        return level;
    }

    public void setLevel(IntegerFilter level) {
        this.level = level;
    }

    public DoubleFilter getLng() {
        return lng;
    }

    public void setLng(DoubleFilter lng) {
        this.lng = lng;
    }

    public DoubleFilter getLat() {
        return lat;
    }

    public void setLat(DoubleFilter lat) {
        this.lat = lat;
    }

    public LongFilter getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(LongFilter childrenId) {
        this.childrenId = childrenId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
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
        final AdministrativeDivisionCriteria that = (AdministrativeDivisionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(areaCode, that.areaCode) &&
            Objects.equals(cityCode, that.cityCode) &&
            Objects.equals(mergerName, that.mergerName) &&
            Objects.equals(shortName, that.shortName) &&
            Objects.equals(zipCode, that.zipCode) &&
            Objects.equals(level, that.level) &&
            Objects.equals(lng, that.lng) &&
            Objects.equals(lat, that.lat) &&
            Objects.equals(childrenId, that.childrenId) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        areaCode,
        cityCode,
        mergerName,
        shortName,
        zipCode,
        level,
        lng,
        lat,
        childrenId,
        parentId
        );
    }

    @Override
    public String toString() {
        return "AdministrativeDivisionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (areaCode != null ? "areaCode=" + areaCode + ", " : "") +
                (cityCode != null ? "cityCode=" + cityCode + ", " : "") +
                (mergerName != null ? "mergerName=" + mergerName + ", " : "") +
                (shortName != null ? "shortName=" + shortName + ", " : "") +
                (zipCode != null ? "zipCode=" + zipCode + ", " : "") +
                (level != null ? "level=" + level + ", " : "") +
                (lng != null ? "lng=" + lng + ", " : "") +
                (lat != null ? "lat=" + lat + ", " : "") +
                (childrenId != null ? "childrenId=" + childrenId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
