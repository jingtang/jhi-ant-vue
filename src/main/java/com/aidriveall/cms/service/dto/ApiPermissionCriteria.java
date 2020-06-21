package com.aidriveall.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.aidriveall.cms.domain.enumeration.ApiPermissionType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.aidriveall.cms.domain.ApiPermission} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.ApiPermissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /api-permissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApiPermissionCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;
    /**
     * Class for filtering ApiPermissionType
     */
    public static class ApiPermissionTypeFilter extends Filter<ApiPermissionType> {

        public ApiPermissionTypeFilter() {
        }

        public ApiPermissionTypeFilter(ApiPermissionTypeFilter filter) {
            super(filter);
        }

        @Override
        public ApiPermissionTypeFilter copy() {
            return new ApiPermissionTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter description;

    private ApiPermissionTypeFilter type;

    private LongFilter childrenId;

    private LongFilter parentId;

    private LongFilter viewPermissionsId;

    public ApiPermissionCriteria(){
    }

    public ApiPermissionCriteria(ApiPermissionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.childrenId = other.childrenId == null ? null : other.childrenId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.viewPermissionsId = other.viewPermissionsId == null ? null : other.viewPermissionsId.copy();
    }

    @Override
    public ApiPermissionCriteria copy() {
        return new ApiPermissionCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ApiPermissionTypeFilter getType() {
        return type;
    }

    public void setType(ApiPermissionTypeFilter type) {
        this.type = type;
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

    public LongFilter getViewPermissionsId() {
        return viewPermissionsId;
    }

    public void setViewPermissionsId(LongFilter viewPermissionsId) {
        this.viewPermissionsId = viewPermissionsId;
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
        final ApiPermissionCriteria that = (ApiPermissionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(childrenId, that.childrenId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(viewPermissionsId, that.viewPermissionsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        description,
        type,
        childrenId,
        parentId,
        viewPermissionsId
        );
    }

    @Override
    public String toString() {
        return "ApiPermissionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (childrenId != null ? "childrenId=" + childrenId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (viewPermissionsId != null ? "viewPermissionsId=" + viewPermissionsId + ", " : "") +
            "}";
    }

}
