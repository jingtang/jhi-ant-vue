package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

import java.io.Serializable;

/**
 * 通用查询条目
 */

@Entity
@Table(name = "common_query_item")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommonQueryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 前置符号
     */
    @Column(name = "prefix")
    private String prefix;

    /**
     * 字段名称
     */
    @Column(name = "field_name")
    private String fieldName;

    /**
     * 字段类型
     */
    @Column(name = "field_type")
    private String fieldType;

    /**
     * 运算符号
     */
    @Column(name = "operator")
    private String operator;

    /**
     * 比较值
     */
    @Column(name = "value")
    private String value;

    /**
     * 后缀
     */
    @Column(name = "suffix")
    private String suffix;

    /**
     * 顺序
     */
    @Column(name = "jhi_order")
    private Integer order;

    /**
     * 查询
     */
    @ManyToOne
    @JsonIgnoreProperties("items")
    private CommonQuery query;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public CommonQueryItem prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFieldName() {
        return fieldName;
    }

    public CommonQueryItem fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public CommonQueryItem fieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getOperator() {
        return operator;
    }

    public CommonQueryItem operator(String operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public CommonQueryItem value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuffix() {
        return suffix;
    }

    public CommonQueryItem suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getOrder() {
        return order;
    }

    public CommonQueryItem order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public CommonQuery getQuery() {
        return query;
    }

    public CommonQueryItem query(CommonQuery commonQuery) {
        this.query = commonQuery;
        return this;
    }

    public void setQuery(CommonQuery commonQuery) {
        this.query = commonQuery;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonQueryItem)) {
            return false;
        }
        return id != null && id.equals(((CommonQueryItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommonQueryItem{" +
            "id=" + getId() +
            ", prefix='" + getPrefix() + "'" +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", operator='" + getOperator() + "'" +
            ", value='" + getValue() + "'" +
            ", suffix='" + getSuffix() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
