package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.CommonQueryItem} entity.
 */
@ApiModel(description = "通用查询条目")
public class CommonQueryItemDTO implements Serializable {

    private Long id;

    /**
     * 前置符号
     */
    @ApiModelProperty(value = "前置符号")
    private String prefix;

    /**
     * 字段名称
     */
    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    /**
     * 字段类型
     */
    @ApiModelProperty(value = "字段类型")
    private String fieldType;

    /**
     * 运算符号
     */
    @ApiModelProperty(value = "运算符号")
    private String operator;

    /**
     * 比较值
     */
    @ApiModelProperty(value = "比较值")
    private String value;

    /**
     * 后缀
     */
    @ApiModelProperty(value = "后缀")
    private String suffix;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer order;


    /**
     * 查询
     */
    @ApiModelProperty(value = "查询")
    private Long queryId;
    private String queryName;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getQueryId() {
        return queryId;
    }

    public void setQueryId(Long commonQueryId) {
        this.queryId = commonQueryId;
    }
    
    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String commonQueryName) {
        this.queryName = commonQueryName;
    }

// jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommonQueryItemDTO commonQueryItemDTO = (CommonQueryItemDTO) o;
        if (commonQueryItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commonQueryItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommonQueryItemDTO{" +
            "id=" + getId() +
            ", prefix='" + getPrefix() + "'" +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", operator='" + getOperator() + "'" +
            ", value='" + getValue() + "'" +
            ", suffix='" + getSuffix() + "'" +
            ", order=" + getOrder() +
            ", query=" + getQueryId() +
            ", queryName='" + getQueryName() + "'" +
            "}";
    }
}
