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
 * Criteria class for the {@link com.aidriveall.cms.domain.Authority} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.AuthorityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /authorities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AuthorityCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter info;

    private IntegerFilter order;

    private BooleanFilter display;

    private LongFilter childrenId;

    private LongFilter usersId;

    private LongFilter viewPermissionId;

    private LongFilter parentId;

    public AuthorityCriteria(){
    }

    public AuthorityCriteria(AuthorityCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.info = other.info == null ? null : other.info.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.display = other.display == null ? null : other.display.copy();
        this.childrenId = other.childrenId == null ? null : other.childrenId.copy();
        this.usersId = other.usersId == null ? null : other.usersId.copy();
        this.viewPermissionId = other.viewPermissionId == null ? null : other.viewPermissionId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public AuthorityCriteria copy() {
        return new AuthorityCriteria(this);
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

    public StringFilter getInfo() {
        return info;
    }

    public void setInfo(StringFilter info) {
        this.info = info;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public BooleanFilter getDisplay() {
        return display;
    }

    public void setDisplay(BooleanFilter display) {
        this.display = display;
    }

    public LongFilter getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(LongFilter childrenId) {
        this.childrenId = childrenId;
    }

    public LongFilter getUsersId() {
        return usersId;
    }

    public void setUsersId(LongFilter usersId) {
        this.usersId = usersId;
    }

    public LongFilter getViewPermissionId() {
        return viewPermissionId;
    }

    public void setViewPermissionId(LongFilter viewPermissionId) {
        this.viewPermissionId = viewPermissionId;
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
        final AuthorityCriteria that = (AuthorityCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(info, that.info) &&
            Objects.equals(order, that.order) &&
            Objects.equals(display, that.display) &&
            Objects.equals(childrenId, that.childrenId) &&
            Objects.equals(usersId, that.usersId) &&
            Objects.equals(viewPermissionId, that.viewPermissionId) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        info,
        order,
        display,
        childrenId,
        usersId,
        viewPermissionId,
        parentId
        );
    }

    @Override
    public String toString() {
        return "AuthorityCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (info != null ? "info=" + info + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (display != null ? "display=" + display + ", " : "") +
                (childrenId != null ? "childrenId=" + childrenId + ", " : "") +
                (usersId != null ? "usersId=" + usersId + ", " : "") +
                (viewPermissionId != null ? "viewPermissionId=" + viewPermissionId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
