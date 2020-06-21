package com.aidriveall.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.aidriveall.cms.domain.enumeration.TargetType;
import com.aidriveall.cms.domain.enumeration.ViewPermissionType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.aidriveall.cms.domain.ViewPermission} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.ViewPermissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /view-permissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ViewPermissionCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;
    /**
     * Class for filtering TargetType
     */
    public static class TargetTypeFilter extends Filter<TargetType> {

        public TargetTypeFilter() {
        }

        public TargetTypeFilter(TargetTypeFilter filter) {
            super(filter);
        }

        @Override
        public TargetTypeFilter copy() {
            return new TargetTypeFilter(this);
        }

    }
    /**
     * Class for filtering ViewPermissionType
     */
    public static class ViewPermissionTypeFilter extends Filter<ViewPermissionType> {

        public ViewPermissionTypeFilter() {
        }

        public ViewPermissionTypeFilter(ViewPermissionTypeFilter filter) {
            super(filter);
        }

        @Override
        public ViewPermissionTypeFilter copy() {
            return new ViewPermissionTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter text;

    private StringFilter i18n;

    private BooleanFilter group;

    private StringFilter link;

    private StringFilter externalLink;

    private TargetTypeFilter target;

    private StringFilter icon;

    private BooleanFilter disabled;

    private BooleanFilter hide;

    private BooleanFilter hideInBreadcrumb;

    private BooleanFilter shortcut;

    private BooleanFilter shortcutRoot;

    private BooleanFilter reuse;

    private StringFilter code;

    private StringFilter description;

    private ViewPermissionTypeFilter type;

    private IntegerFilter order;

    private StringFilter apiPermissionCodes;

    private LongFilter childrenId;

    private LongFilter apiPermissionsId;

    private LongFilter parentId;

    private LongFilter authoritiesId;

    public ViewPermissionCriteria(){
    }

    public ViewPermissionCriteria(ViewPermissionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.i18n = other.i18n == null ? null : other.i18n.copy();
        this.group = other.group == null ? null : other.group.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.externalLink = other.externalLink == null ? null : other.externalLink.copy();
        this.target = other.target == null ? null : other.target.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.disabled = other.disabled == null ? null : other.disabled.copy();
        this.hide = other.hide == null ? null : other.hide.copy();
        this.hideInBreadcrumb = other.hideInBreadcrumb == null ? null : other.hideInBreadcrumb.copy();
        this.shortcut = other.shortcut == null ? null : other.shortcut.copy();
        this.shortcutRoot = other.shortcutRoot == null ? null : other.shortcutRoot.copy();
        this.reuse = other.reuse == null ? null : other.reuse.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.apiPermissionCodes = other.apiPermissionCodes == null ? null : other.apiPermissionCodes.copy();
        this.childrenId = other.childrenId == null ? null : other.childrenId.copy();
        this.apiPermissionsId = other.apiPermissionsId == null ? null : other.apiPermissionsId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.authoritiesId = other.authoritiesId == null ? null : other.authoritiesId.copy();
    }

    @Override
    public ViewPermissionCriteria copy() {
        return new ViewPermissionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public StringFilter geti18n() {
        return i18n;
    }

    public void seti18n(StringFilter i18n) {
        this.i18n = i18n;
    }

    public BooleanFilter getGroup() {
        return group;
    }

    public void setGroup(BooleanFilter group) {
        this.group = group;
    }

    public StringFilter getLink() {
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public StringFilter getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(StringFilter externalLink) {
        this.externalLink = externalLink;
    }

    public TargetTypeFilter getTarget() {
        return target;
    }

    public void setTarget(TargetTypeFilter target) {
        this.target = target;
    }

    public StringFilter getIcon() {
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public BooleanFilter getDisabled() {
        return disabled;
    }

    public void setDisabled(BooleanFilter disabled) {
        this.disabled = disabled;
    }

    public BooleanFilter getHide() {
        return hide;
    }

    public void setHide(BooleanFilter hide) {
        this.hide = hide;
    }

    public BooleanFilter getHideInBreadcrumb() {
        return hideInBreadcrumb;
    }

    public void setHideInBreadcrumb(BooleanFilter hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
    }

    public BooleanFilter getShortcut() {
        return shortcut;
    }

    public void setShortcut(BooleanFilter shortcut) {
        this.shortcut = shortcut;
    }

    public BooleanFilter getShortcutRoot() {
        return shortcutRoot;
    }

    public void setShortcutRoot(BooleanFilter shortcutRoot) {
        this.shortcutRoot = shortcutRoot;
    }

    public BooleanFilter getReuse() {
        return reuse;
    }

    public void setReuse(BooleanFilter reuse) {
        this.reuse = reuse;
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

    public ViewPermissionTypeFilter getType() {
        return type;
    }

    public void setType(ViewPermissionTypeFilter type) {
        this.type = type;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public StringFilter getApiPermissionCodes() {
        return apiPermissionCodes;
    }

    public void setApiPermissionCodes(StringFilter apiPermissionCodes) {
        this.apiPermissionCodes = apiPermissionCodes;
    }

    public LongFilter getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(LongFilter childrenId) {
        this.childrenId = childrenId;
    }

    public LongFilter getApiPermissionsId() {
        return apiPermissionsId;
    }

    public void setApiPermissionsId(LongFilter apiPermissionsId) {
        this.apiPermissionsId = apiPermissionsId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getAuthoritiesId() {
        return authoritiesId;
    }

    public void setAuthoritiesId(LongFilter authoritiesId) {
        this.authoritiesId = authoritiesId;
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
        final ViewPermissionCriteria that = (ViewPermissionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(text, that.text) &&
            Objects.equals(i18n, that.i18n) &&
            Objects.equals(group, that.group) &&
            Objects.equals(link, that.link) &&
            Objects.equals(externalLink, that.externalLink) &&
            Objects.equals(target, that.target) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(disabled, that.disabled) &&
            Objects.equals(hide, that.hide) &&
            Objects.equals(hideInBreadcrumb, that.hideInBreadcrumb) &&
            Objects.equals(shortcut, that.shortcut) &&
            Objects.equals(shortcutRoot, that.shortcutRoot) &&
            Objects.equals(reuse, that.reuse) &&
            Objects.equals(code, that.code) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(order, that.order) &&
            Objects.equals(apiPermissionCodes, that.apiPermissionCodes) &&
            Objects.equals(childrenId, that.childrenId) &&
            Objects.equals(apiPermissionsId, that.apiPermissionsId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(authoritiesId, that.authoritiesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        text,
        i18n,
        group,
        link,
        externalLink,
        target,
        icon,
        disabled,
        hide,
        hideInBreadcrumb,
        shortcut,
        shortcutRoot,
        reuse,
        code,
        description,
        type,
        order,
        apiPermissionCodes,
        childrenId,
        apiPermissionsId,
        parentId,
        authoritiesId
        );
    }

    @Override
    public String toString() {
        return "ViewPermissionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (i18n != null ? "i18n=" + i18n + ", " : "") +
                (group != null ? "group=" + group + ", " : "") +
                (link != null ? "link=" + link + ", " : "") +
                (externalLink != null ? "externalLink=" + externalLink + ", " : "") +
                (target != null ? "target=" + target + ", " : "") +
                (icon != null ? "icon=" + icon + ", " : "") +
                (disabled != null ? "disabled=" + disabled + ", " : "") +
                (hide != null ? "hide=" + hide + ", " : "") +
                (hideInBreadcrumb != null ? "hideInBreadcrumb=" + hideInBreadcrumb + ", " : "") +
                (shortcut != null ? "shortcut=" + shortcut + ", " : "") +
                (shortcutRoot != null ? "shortcutRoot=" + shortcutRoot + ", " : "") +
                (reuse != null ? "reuse=" + reuse + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (apiPermissionCodes != null ? "apiPermissionCodes=" + apiPermissionCodes + ", " : "") +
                (childrenId != null ? "childrenId=" + childrenId + ", " : "") +
                (apiPermissionsId != null ? "apiPermissionsId=" + apiPermissionsId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (authoritiesId != null ? "authoritiesId=" + authoritiesId + ", " : "") +
            "}";
    }

}
