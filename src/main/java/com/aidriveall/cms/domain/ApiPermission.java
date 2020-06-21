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

import com.aidriveall.cms.domain.enumeration.ApiPermissionType;

/**
 * API权限
 * 菜单或按钮下有相关的api权限
 */

@Entity
@Table(name = "api_permission")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApiPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限名称
     */
    @Column(name = "name")
    private String name;

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
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ApiPermissionType type;

    /**
     * 子节点
     */
    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApiPermission> children = new LinkedHashSet<>();

    /**
     * 上级
     */
    @ManyToOne
    @JsonIgnoreProperties("children")
    private ApiPermission parent;
    @ManyToMany(mappedBy = "apiPermissions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ViewPermission> viewPermissions = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ApiPermission name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public ApiPermission code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public ApiPermission description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApiPermissionType getType() {
        return type;
    }

    public ApiPermission type(ApiPermissionType type) {
        this.type = type;
        return this;
    }

    public void setType(ApiPermissionType type) {
        this.type = type;
    }

    public Set<ApiPermission> getChildren() {
        return children;
    }

    public ApiPermission children(Set<ApiPermission> apiPermissions) {
        this.children = apiPermissions;
        return this;
    }

    public ApiPermission addChildren(ApiPermission apiPermission) {
        this.children.add(apiPermission);
        apiPermission.setParent(this);
        return this;
    }

    public ApiPermission removeChildren(ApiPermission apiPermission) {
        this.children.remove(apiPermission);
        apiPermission.setParent(null);
        return this;
    }

    public void setChildren(Set<ApiPermission> apiPermissions) {
        this.children = apiPermissions;
    }

    public ApiPermission getParent() {
        return parent;
    }

    public ApiPermission parent(ApiPermission apiPermission) {
        this.parent = apiPermission;
        return this;
    }

    public void setParent(ApiPermission apiPermission) {
        this.parent = apiPermission;
    }

    public Set<ViewPermission> getViewPermissions() {
        return viewPermissions;
    }

    public ApiPermission viewPermissions(Set<ViewPermission> viewPermissions) {
        this.viewPermissions = viewPermissions;
        return this;
    }

    public ApiPermission addViewPermissions(ViewPermission viewPermission) {
        this.viewPermissions.add(viewPermission);
        viewPermission.getApiPermissions().add(this);
        return this;
    }

    public ApiPermission removeViewPermissions(ViewPermission viewPermission) {
        this.viewPermissions.remove(viewPermission);
        viewPermission.getApiPermissions().remove(this);
        return this;
    }

    public void setViewPermissions(Set<ViewPermission> viewPermissions) {
        this.viewPermissions = viewPermissions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiPermission)) {
            return false;
        }
        return id != null && id.equals(((ApiPermission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApiPermission{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
