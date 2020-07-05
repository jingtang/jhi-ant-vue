package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Objects;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.CommonQuery} entity.
 */
@ApiModel(description = "通用查询")
public class CommonQueryDTO implements Serializable {

    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    private ZonedDateTime lastModifiedTime;


    /**
     * 条件项目
     */
    @ApiModelProperty(value = "条件项目")
    private LinkedHashSet<CommonQueryItemDTO> items = new LinkedHashSet<>();
    /**
     * 编辑人
     */
    @ApiModelProperty(value = "编辑人")
    private Long modifierId;
    private String modifierImageUrl;
    private String modifierFirstName;
    /**
     * 所属模型
     */
    @ApiModelProperty(value = "所属模型")
    private Long commonTableId;
    private String commonTableName;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(ZonedDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public LinkedHashSet<CommonQueryItemDTO> getItems() {
        return this.items;
    }

    public void setItems(LinkedHashSet<CommonQueryItemDTO> items) {
        this.items = items;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long userId) {
        this.modifierId = userId;
    }
    public String getModifierImageUrl() {
        return modifierImageUrl;
    }

    public void setModifierImageUrl(String userImageUrl) {
        this.modifierImageUrl = userImageUrl;
    }
    
    public String getModifierFirstName() {
        return modifierFirstName;
    }

    public void setModifierFirstName(String userFirstName) {
        this.modifierFirstName = userFirstName;
    }

    public Long getCommonTableId() {
        return commonTableId;
    }

    public void setCommonTableId(Long commonTableId) {
        this.commonTableId = commonTableId;
    }
    
    public String getCommonTableName() {
        return commonTableName;
    }

    public void setCommonTableName(String commonTableName) {
        this.commonTableName = commonTableName;
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

        CommonQueryDTO commonQueryDTO = (CommonQueryDTO) o;
        if (commonQueryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commonQueryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommonQueryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", lastModifiedTime='" + getLastModifiedTime() + "'" +
            ", modifier=" + getModifierId() +
            ", modifierFirstName='" + getModifierFirstName() + "'" +
            ", commonTable=" + getCommonTableId() +
            ", commonTableName='" + getCommonTableName() + "'" +
            "}";
    }
}
