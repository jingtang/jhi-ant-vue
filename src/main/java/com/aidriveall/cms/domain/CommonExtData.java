package com.aidriveall.cms.domain;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * 通用扩展数据
 */
@Entity
@Table(name = "common_ext_data")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommonExtData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 属性名
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "field_name", length = 100, nullable = false)
    private String fieldName;

    /**
     * 通用字段类型
     */
    @Column(name = "common_field_type", insertable = false, updatable = false)
    private String commonFieldType;

    /**
     * 通用数据Id
     */
    @Column(name = "common_field_id", insertable = false, updatable = false)
    private Long commonFieldId;

    /**
     * 宿主实体名称
     */
    @Column(name = "owner_type", insertable = false, updatable = false)
    private String ownerType;

    /**
     * 宿主实体Id
     */
    @Column(name = "owner_id", insertable = false, updatable = false)
    private Long ownerId;

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Any(metaDef = "ExtDataMetaDef",metaColumn = @Column(name = "common_field_type"), fetch = FetchType.EAGER)
    @JoinColumn(name = "common_field_id")
    private CommonField value;

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Any(metaDef = "OwnerMetaDef",metaColumn = @Column(name = "owner_type"), fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public CommonExtData fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getCommonFieldType() {
        return commonFieldType;
    }

    public CommonExtData commonFieldType(String commonFieldType) {
        this.commonFieldType = commonFieldType;
        return this;
    }

    public void setCommonFieldType(String commonFieldType) {
        this.commonFieldType = commonFieldType;
    }

    public Long getCommonFieldId() {
        return commonFieldId;
    }

    public CommonExtData commonFieldId(Long commonFieldId) {
        this.commonFieldId = commonFieldId;
        return this;
    }

    public void setCommonFieldId(Long commonFieldId) {
        this.commonFieldId = commonFieldId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public CommonExtData ownerType(String ownerType) {
        this.ownerType = ownerType;
        return this;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public CommonExtData ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public CommonField getValue() {
        return value;
    }

    public void setValue(CommonField value) {
        this.value = value;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonExtData)) {
            return false;
        }
        return id != null && id.equals(((CommonExtData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommonExtData{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            ", commonFieldType='" + getCommonFieldType() + "'" +
            ", commonFieldId=" + getCommonFieldId() +
            ", ownerType='" + getOwnerType() + "'" +
            ", ownerId=" + getOwnerId() +
            "}";
    }
}
