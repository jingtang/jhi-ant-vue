package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

import java.io.Serializable;

/**
 * 流程业务配置
 */

@Entity
@Table(name = "process_table_config")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessTableConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 流程Id
     */
    @Column(name = "process_definition_id")
    private String processDefinitionId;

    /**
     * 流程Key
     */
    @Column(name = "process_definition_key")
    private String processDefinitionKey;

    /**
     * 流程名称
     */
    @Column(name = "process_definition_name")
    private String processDefinitionName;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 流程数据
     */
    @Lob
    @Column(name = "process_bpmn_data")
    private String processBpmnData;

    /**
     * 是否部署
     */
    @Column(name = "deploied")
    private Boolean deploied;

    /**
     * 关联表
     */
    @ManyToOne
    @JsonIgnoreProperties("processTableConfigs")
    private CommonTable commonTable;
    /**
     * 创建人
     */
    @ManyToOne
    @JsonIgnoreProperties("processTableConfigs")
    private User creator;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public ProcessTableConfig processDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public ProcessTableConfig processDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public ProcessTableConfig processDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
        return this;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public String getDescription() {
        return description;
    }

    public ProcessTableConfig description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcessBpmnData() {
        return processBpmnData;
    }

    public ProcessTableConfig processBpmnData(String processBpmnData) {
        this.processBpmnData = processBpmnData;
        return this;
    }

    public void setProcessBpmnData(String processBpmnData) {
        this.processBpmnData = processBpmnData;
    }

    public Boolean isDeploied() {
        return deploied;
    }

    public ProcessTableConfig deploied(Boolean deploied) {
        this.deploied = deploied;
        return this;
    }

    public void setDeploied(Boolean deploied) {
        this.deploied = deploied;
    }

    public CommonTable getCommonTable() {
        return commonTable;
    }

    public ProcessTableConfig commonTable(CommonTable commonTable) {
        this.commonTable = commonTable;
        return this;
    }

    public void setCommonTable(CommonTable commonTable) {
        this.commonTable = commonTable;
    }

    public User getCreator() {
        return creator;
    }

    public ProcessTableConfig creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessTableConfig)) {
            return false;
        }
        return id != null && id.equals(((ProcessTableConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProcessTableConfig{" +
            "id=" + getId() +
            ", processDefinitionId='" + getProcessDefinitionId() + "'" +
            ", processDefinitionKey='" + getProcessDefinitionKey() + "'" +
            ", processDefinitionName='" + getProcessDefinitionName() + "'" +
            ", description='" + getDescription() + "'" +
            ", processBpmnData='" + getProcessBpmnData() + "'" +
            ", deploied='" + isDeploied() + "'" +
            "}";
    }
}
