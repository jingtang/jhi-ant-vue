package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 角色
 * 采用自分组的形式,采用向下包含关系，选中本节点继承父层并包含本节点内容及其所有子节点内容。
 */

@Entity
@Table(name = "authority")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色代号
     */
    @Column(name = "code")
    private String code;

    /**
     * 信息
     */
    @Column(name = "info")
    private String info;

    /**
     * 排序
     */
    @Column(name = "jhi_order")
    private Integer order;

    /**
     * 展示
     */
    @Column(name = "display")
    private Boolean display;

    /**
     * 子节点
     */
    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Authority> children = new LinkedHashSet<>();

    /**
     * 用户
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "authority_users",
               joinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> users = new LinkedHashSet<>();

    /**
     * 可视权限
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "authority_view_permission",
               joinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "view_permission_id", referencedColumnName = "id"))
    private Set<ViewPermission> viewPermissions = new LinkedHashSet<>();

    /**
     * 上级
     */
    @ManyToOne
    @JsonIgnoreProperties("children")
    private Authority parent;
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

    public Authority name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Authority code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public Authority info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getOrder() {
        return order;
    }

    public Authority order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean isDisplay() {
        return display;
    }

    public Authority display(Boolean display) {
        this.display = display;
        return this;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public Set<Authority> getChildren() {
        return children;
    }

    public Authority children(Set<Authority> authorities) {
        this.children = authorities;
        return this;
    }

    public Authority addChildren(Authority authority) {
        this.children.add(authority);
        authority.setParent(this);
        return this;
    }

    public Authority removeChildren(Authority authority) {
        this.children.remove(authority);
        authority.setParent(null);
        return this;
    }

    public void setChildren(Set<Authority> authorities) {
        this.children = authorities;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Authority users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Authority addUsers(User user) {
        this.users.add(user);
        return this;
    }

    public Authority removeUsers(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<ViewPermission> getViewPermissions() {
        return viewPermissions;
    }

    public Authority viewPermissions(Set<ViewPermission> viewPermissions) {
        this.viewPermissions = viewPermissions;
        return this;
    }

    public Authority addViewPermission(ViewPermission viewPermission) {
        this.viewPermissions.add(viewPermission);
        viewPermission.getAuthorities().add(this);
        return this;
    }

    public Authority removeViewPermission(ViewPermission viewPermission) {
        this.viewPermissions.remove(viewPermission);
        viewPermission.getAuthorities().remove(this);
        return this;
    }

    public void setViewPermissions(Set<ViewPermission> viewPermissions) {
        this.viewPermissions = viewPermissions;
    }

    public Authority getParent() {
        return parent;
    }

    public Authority parent(Authority authority) {
        this.parent = authority;
        return this;
    }

    public void setParent(Authority authority) {
        this.parent = authority;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        return id != null && id.equals(((Authority) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Authority{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", info='" + getInfo() + "'" +
            ", order=" + getOrder() +
            ", display='" + isDisplay() + "'" +
            "}";
    }
}
