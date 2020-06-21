package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.ProcessTableConfig} entity.
 */
@ApiModel(description = "流程业务配置")
public class ProcessTableConfigDTO implements Serializable {

    private Long id;

    /**
     * 流程Id
     */
    @ApiModelProperty(value = "流程Id")
    private String processDefinitionId;

    /**
     * 流程Key
     */
    @ApiModelProperty(value = "流程Key")
    private String processDefinitionKey;

    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称")
    private String processDefinitionName;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 流程数据
     */
    @ApiModelProperty(value = "流程数据")
    @Lob
    private String processBpmnData;

    /**
     * 是否部署
     */
    @ApiModelProperty(value = "是否部署")
    private Boolean deploied;


    /**
     * 关联表
     */
    @ApiModelProperty(value = "关联表")
    private Long commonTableId;
    private String commonTableName;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long creatorId;
    private String creatorImageUrl;
    private String creatorLogin;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcessBpmnData() {
        return processBpmnData;
    }

    public void setProcessBpmnData(String processBpmnData) {
        this.processBpmnData = processBpmnData;
    }

    public Boolean isDeploied() {
        return deploied;
    }

    public void setDeploied(Boolean deploied) {
        this.deploied = deploied;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long userId) {
        this.creatorId = userId;
    }
    public String getCreatorImageUrl() {
        return creatorImageUrl;
    }

    public void setCreatorImageUrl(String userImageUrl) {
        this.creatorImageUrl = userImageUrl;
    }
    
    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String userLogin) {
        this.creatorLogin = userLogin;
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

        ProcessTableConfigDTO processTableConfigDTO = (ProcessTableConfigDTO) o;
        if (processTableConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), processTableConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcessTableConfigDTO{" +
            "id=" + getId() +
            ", processDefinitionId='" + getProcessDefinitionId() + "'" +
            ", processDefinitionKey='" + getProcessDefinitionKey() + "'" +
            ", processDefinitionName='" + getProcessDefinitionName() + "'" +
            ", description='" + getDescription() + "'" +
            ", processBpmnData='" + getProcessBpmnData() + "'" +
            ", deploied='" + isDeploied() + "'" +
            ", commonTable=" + getCommonTableId() +
            ", commonTableName='" + getCommonTableName() + "'" +
            ", creator=" + getCreatorId() +
            ", creatorLogin='" + getCreatorLogin() + "'" +
            "}";
    }
}
