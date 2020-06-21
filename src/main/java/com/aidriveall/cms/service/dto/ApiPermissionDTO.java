package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Objects;
import com.aidriveall.cms.domain.enumeration.ApiPermissionType;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.ApiPermission} entity.
 */
@ApiModel(description = "API权限\n菜单或按钮下有相关的api权限")
public class ApiPermissionDTO implements Serializable {

    private Long id;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String name;

    /**
     * 权限代码(ROLE_开头)
     */
    @ApiModelProperty(value = "权限代码(ROLE_开头)")
    private String code;

    /**
     * 权限描述
     */
    @ApiModelProperty(value = "权限描述")
    private String description;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private ApiPermissionType type;


    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private LinkedHashSet<ApiPermissionDTO> children = new LinkedHashSet<>();
    /**
     * 上级
     */
    @ApiModelProperty(value = "上级")
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

    public ApiPermissionType getType() {
        return type;
    }

    public void setType(ApiPermissionType type) {
        this.type = type;
    }

    public LinkedHashSet<ApiPermissionDTO> getChildren() {
        return this.children;
    }

    public void setChildren(LinkedHashSet<ApiPermissionDTO> children) {
        this.children = children;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long apiPermissionId) {
        this.parentId = apiPermissionId;
    }
    
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String apiPermissionName) {
        this.parentName = apiPermissionName;
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

        ApiPermissionDTO apiPermissionDTO = (ApiPermissionDTO) o;
        if (apiPermissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apiPermissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApiPermissionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", parent=" + getParentId() +
            ", parentName='" + getParentName() + "'" +
            "}";
    }
}
