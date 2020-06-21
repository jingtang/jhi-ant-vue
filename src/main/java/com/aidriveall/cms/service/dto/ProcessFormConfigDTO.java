package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.ProcessFormConfig} entity.
 */
@ApiModel(description = "流程表单")
public class ProcessFormConfigDTO implements Serializable {

    private Long id;

    /**
     * 流程Id
     */
    @ApiModelProperty(value = "流程Id")
    private String processDefinitionKey;

    /**
     * 节点Id
     */
    @ApiModelProperty(value = "节点Id")
    private String taskNodeId;

    /**
     * 任务节点名称
     */
    @ApiModelProperty(value = "任务节点名称")
    private String taskNodeName;

    /**
     * 业务功能Id
     */
    @ApiModelProperty(value = "业务功能Id")
    private Long commonTableId;

    /**
     * 表单配置
     */
    @ApiModelProperty(value = "表单配置")
    @Lob
    private String formData;



    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getTaskNodeId() {
        return taskNodeId;
    }

    public void setTaskNodeId(String taskNodeId) {
        this.taskNodeId = taskNodeId;
    }

    public String getTaskNodeName() {
        return taskNodeName;
    }

    public void setTaskNodeName(String taskNodeName) {
        this.taskNodeName = taskNodeName;
    }

    public Long getCommonTableId() {
        return commonTableId;
    }

    public void setCommonTableId(Long commonTableId) {
        this.commonTableId = commonTableId;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
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

        ProcessFormConfigDTO processFormConfigDTO = (ProcessFormConfigDTO) o;
        if (processFormConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), processFormConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcessFormConfigDTO{" +
            "id=" + getId() +
            ", processDefinitionKey='" + getProcessDefinitionKey() + "'" +
            ", taskNodeId='" + getTaskNodeId() + "'" +
            ", taskNodeName='" + getTaskNodeName() + "'" +
            ", commonTableId=" + getCommonTableId() +
            ", formData='" + getFormData() + "'" +
            "}";
    }
}
