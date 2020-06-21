package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Objects;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.DataDictionary} entity.
 */
@ApiModel(description = "数据字典")
public class DataDictionaryDTO implements Serializable {

    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 代码
     */
    @ApiModelProperty(value = "代码")
    private String code;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 字体颜色
     */
    @ApiModelProperty(value = "字体颜色")
    private String fontColor;

    /**
     * 背景颜色
     */
    @ApiModelProperty(value = "背景颜色")
    private String backgroundColor;


    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private LinkedHashSet<DataDictionaryDTO> children = new LinkedHashSet<>();
    /**
     * 上级节点
     */
    @ApiModelProperty(value = "上级节点")
    private Long parentId;
    private String parentName;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public LinkedHashSet<DataDictionaryDTO> getChildren() {
        return this.children;
    }

    public void setChildren(LinkedHashSet<DataDictionaryDTO> children) {
        this.children = children;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long dataDictionaryId) {
        this.parentId = dataDictionaryId;
    }
    
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String dataDictionaryName) {
        this.parentName = dataDictionaryName;
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

        DataDictionaryDTO dataDictionaryDTO = (DataDictionaryDTO) o;
        if (dataDictionaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataDictionaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataDictionaryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", fontColor='" + getFontColor() + "'" +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", parent=" + getParentId() +
            ", parentName='" + getParentName() + "'" +
            "}";
    }
}
