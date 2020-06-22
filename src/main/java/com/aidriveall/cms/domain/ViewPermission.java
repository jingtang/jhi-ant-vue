package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import com.aidriveall.cms.domain.enumeration.TargetType;

import com.aidriveall.cms.domain.enumeration.ViewPermissionType;

/**
 * 可视权限
 * 权限分为菜单权限、按钮权限等
 */

@Entity
@Table(name = "view_permission")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ViewPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限名称
     */
    @Column(name = "text")
    private String text;

    /**
     * i18n主键
     */
    @Column(name = "i_18_n")
    private String i18n;

    /**
     * 显示分组名
     */
    @Column(name = "jhi_group")
    private Boolean group;

    /**
     * 路由
     */
    @Column(name = "link")
    private String link;

    /**
     * 外部链接
     */
    @Column(name = "external_link")
    private String externalLink;

    /**
     * 链接目标
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "target")
    private TargetType target;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 禁用菜单
     */
    @Column(name = "disabled")
    private Boolean disabled;

    /**
     * 隐藏菜单
     */
    @Column(name = "hide")
    private Boolean hide;

    /**
     * 隐藏面包屑
     */
    @Column(name = "hide_in_breadcrumb")
    private Boolean hideInBreadcrumb;

    /**
     * 快捷菜单项
     */
    @Column(name = "shortcut")
    private Boolean shortcut;

    /**
     * 菜单根节点
     */
    @Column(name = "shortcut_root")
    private Boolean shortcutRoot;

    /**
     * 允许复用
     */
    @Column(name = "reuse")
    private Boolean reuse;

    /**
     * 权限代码(ROLE_开头)
     */
    @Column(name = "code")
    private String code;

    /**
     * 权限描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 权限类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ViewPermissionType type;

    /**
     * 排序
     */
    @Column(name = "jhi_order")
    private Integer order;

    /**
     * api权限标识串
     */
    @Column(name = "api_permission_codes")
    private String apiPermissionCodes;

    /**
     * 子节点
     */
    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ViewPermission> children = new LinkedHashSet<>();

    /**
     * API权限
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "view_permission_api_permissions",
               joinColumns = @JoinColumn(name = "view_permission_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "api_permissions_id", referencedColumnName = "id"))
    private Set<ApiPermission> apiPermissions = new LinkedHashSet<>();

    /**
     * 上级
     */
    @ManyToOne
    @JsonIgnoreProperties("children")
    private ViewPermission parent;
    @ManyToMany(mappedBy = "viewPermissions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Authority> authorities = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public ViewPermission text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String geti18n() {
        return i18n;
    }

    public ViewPermission i18n(String i18n) {
        this.i18n = i18n;
        return this;
    }

    public void seti18n(String i18n) {
        this.i18n = i18n;
    }

    public Boolean isGroup() {
        return group;
    }

    public ViewPermission group(Boolean group) {
        this.group = group;
        return this;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public String getLink() {
        return link;
    }

    public ViewPermission link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public ViewPermission externalLink(String externalLink) {
        this.externalLink = externalLink;
        return this;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public TargetType getTarget() {
        return target;
    }

    public ViewPermission target(TargetType target) {
        this.target = target;
        return this;
    }

    public void setTarget(TargetType target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public ViewPermission icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public ViewPermission disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean isHide() {
        return hide;
    }

    public ViewPermission hide(Boolean hide) {
        this.hide = hide;
        return this;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Boolean isHideInBreadcrumb() {
        return hideInBreadcrumb;
    }

    public ViewPermission hideInBreadcrumb(Boolean hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
        return this;
    }

    public void setHideInBreadcrumb(Boolean hideInBreadcrumb) {
        this.hideInBreadcrumb = hideInBreadcrumb;
    }

    public Boolean isShortcut() {
        return shortcut;
    }

    public ViewPermission shortcut(Boolean shortcut) {
        this.shortcut = shortcut;
        return this;
    }

    public void setShortcut(Boolean shortcut) {
        this.shortcut = shortcut;
    }

    public Boolean isShortcutRoot() {
        return shortcutRoot;
    }

    public ViewPermission shortcutRoot(Boolean shortcutRoot) {
        this.shortcutRoot = shortcutRoot;
        return this;
    }

    public void setShortcutRoot(Boolean shortcutRoot) {
        this.shortcutRoot = shortcutRoot;
    }

    public Boolean isReuse() {
        return reuse;
    }

    public ViewPermission reuse(Boolean reuse) {
        this.reuse = reuse;
        return this;
    }

    public void setReuse(Boolean reuse) {
        this.reuse = reuse;
    }

    public String getCode() {
        return code;
    }

    public ViewPermission code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public ViewPermission description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ViewPermissionType getType() {
        return type;
    }

    public ViewPermission type(ViewPermissionType type) {
        this.type = type;
        return this;
    }

    public void setType(ViewPermissionType type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public ViewPermission order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getApiPermissionCodes() {
        return apiPermissionCodes;
    }

    public ViewPermission apiPermissionCodes(String apiPermissionCodes) {
        this.apiPermissionCodes = apiPermissionCodes;
        return this;
    }

    public void setApiPermissionCodes(String apiPermissionCodes) {
        this.apiPermissionCodes = apiPermissionCodes;
    }

    public Set<ViewPermission> getChildren() {
        return children;
    }

    public ViewPermission children(Set<ViewPermission> viewPermissions) {
        this.children = viewPermissions;
        return this;
    }

    public ViewPermission addChildren(ViewPermission viewPermission) {
        this.children.add(viewPermission);
        viewPermission.setParent(this);
        return this;
    }

    public ViewPermission removeChildren(ViewPermission viewPermission) {
        this.children.remove(viewPermission);
        viewPermission.setParent(null);
        return this;
    }

    public void setChildren(Set<ViewPermission> viewPermissions) {
        this.children = viewPermissions;
    }

    public Set<ApiPermission> getApiPermissions() {
        return apiPermissions;
    }

    public ViewPermission apiPermissions(Set<ApiPermission> apiPermissions) {
        this.apiPermissions = apiPermissions;
        return this;
    }

    public ViewPermission addApiPermissions(ApiPermission apiPermission) {
        this.apiPermissions.add(apiPermission);
        apiPermission.getViewPermissions().add(this);
        return this;
    }

    public ViewPermission removeApiPermissions(ApiPermission apiPermission) {
        this.apiPermissions.remove(apiPermission);
        apiPermission.getViewPermissions().remove(this);
        return this;
    }

    public void setApiPermissions(Set<ApiPermission> apiPermissions) {
        this.apiPermissions = apiPermissions;
    }

    public ViewPermission getParent() {
        return parent;
    }

    public ViewPermission parent(ViewPermission viewPermission) {
        this.parent = viewPermission;
        return this;
    }

    public void setParent(ViewPermission viewPermission) {
        this.parent = viewPermission;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public ViewPermission authorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public ViewPermission addAuthorities(Authority authority) {
        this.authorities.add(authority);
        authority.getViewPermissions().add(this);
        return this;
    }

    public ViewPermission removeAuthorities(Authority authority) {
        this.authorities.remove(authority);
        authority.getViewPermissions().remove(this);
        return this;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewPermission)) {
            return false;
        }
        return id != null && id.equals(((ViewPermission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ViewPermission{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", i18n='" + geti18n() + "'" +
            ", group='" + isGroup() + "'" +
            ", link='" + getLink() + "'" +
            ", externalLink='" + getExternalLink() + "'" +
            ", target='" + getTarget() + "'" +
            ", icon='" + getIcon() + "'" +
            ", disabled='" + isDisabled() + "'" +
            ", hide='" + isHide() + "'" +
            ", hideInBreadcrumb='" + isHideInBreadcrumb() + "'" +
            ", shortcut='" + isShortcut() + "'" +
            ", shortcutRoot='" + isShortcutRoot() + "'" +
            ", reuse='" + isReuse() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", order=" + getOrder() +
            ", apiPermissionCodes='" + getApiPermissionCodes() + "'" +
            "}";
    }
}
