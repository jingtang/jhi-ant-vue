package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 通用查询
 */

@Entity
@Table(name = "common_query")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
public class CommonQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 最后更新时间
     */
    @UpdateTimestamp
    @Column(name = "last_modified_time")
    private ZonedDateTime lastModifiedTime = ZonedDateTime.now();

    /**
     * 条件项目
     */
    @OneToMany(mappedBy = "query")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommonQueryItem> items = new LinkedHashSet<>();

    /**
     * 编辑人
     */
    @LastModifiedBy
    @ManyToOne
    @JsonIgnoreProperties("commonQueries")
    private User modifier;
    /**
     * 所属模型
     */
    @ManyToOne
    @JsonIgnoreProperties("commonQueries")
    private CommonTable commonTable;
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

    public CommonQuery name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public CommonQuery description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public CommonQuery lastModifiedTime(ZonedDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public void setLastModifiedTime(ZonedDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Set<CommonQueryItem> getItems() {
        return items;
    }

    public CommonQuery items(Set<CommonQueryItem> commonQueryItems) {
        this.items = commonQueryItems;
        return this;
    }

    public CommonQuery addItems(CommonQueryItem commonQueryItem) {
        this.items.add(commonQueryItem);
        commonQueryItem.setQuery(this);
        return this;
    }

    public CommonQuery removeItems(CommonQueryItem commonQueryItem) {
        this.items.remove(commonQueryItem);
        commonQueryItem.setQuery(null);
        return this;
    }

    public void setItems(Set<CommonQueryItem> commonQueryItems) {
        this.items = commonQueryItems;
    }

    public User getModifier() {
        return modifier;
    }

    public CommonQuery modifier(User user) {
        this.modifier = user;
        return this;
    }

    public void setModifier(User user) {
        this.modifier = user;
    }

    public CommonTable getCommonTable() {
        return commonTable;
    }

    public CommonQuery commonTable(CommonTable commonTable) {
        this.commonTable = commonTable;
        return this;
    }

    public void setCommonTable(CommonTable commonTable) {
        this.commonTable = commonTable;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonQuery)) {
            return false;
        }
        return id != null && id.equals(((CommonQuery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommonQuery{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", lastModifiedTime='" + getLastModifiedTime() + "'" +
            "}";
    }
}
