package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import com.aidriveall.cms.domain.enumeration.ProcessEntityStatus;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.ProcessEntityRelation} entity.
 */
@ApiModel(description = "流程与实体关联")
public class ProcessEntityRelationDTO implements Serializable {

    private Long id;

    /**
     * 宿主实体名称
     */
    @ApiModelProperty(value = "宿主实体名称")
    private String entityType;

    /**
     * 宿主实体Id
     */
    @ApiModelProperty(value = "宿主实体Id")
    private Long entityId;

    /**
     * 流程实例Id
     */
    @ApiModelProperty(value = "流程实例Id")
    private String processInstanceId;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private ProcessEntityStatus status;



    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public ProcessEntityStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessEntityStatus status) {
        this.status = status;
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

        ProcessEntityRelationDTO processEntityRelationDTO = (ProcessEntityRelationDTO) o;
        if (processEntityRelationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), processEntityRelationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcessEntityRelationDTO{" +
            "id=" + getId() +
            ", entityType='" + getEntityType() + "'" +
            ", entityId=" + getEntityId() +
            ", processInstanceId='" + getProcessInstanceId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
