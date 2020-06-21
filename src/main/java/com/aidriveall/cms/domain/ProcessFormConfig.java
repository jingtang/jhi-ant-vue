package com.aidriveall.cms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

import java.io.Serializable;

/**
 * 流程表单
 */

@Entity
@Table(name = "process_form_config")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessFormConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 流程Id
     */
    @Column(name = "process_definition_key")
    private String processDefinitionKey;

    /**
     * 节点Id
     */
    @Column(name = "task_node_id")
    private String taskNodeId;

    /**
     * 任务节点名称
     */
    @Column(name = "task_node_name")
    private String taskNodeName;

    /**
     * 业务功能Id
     */
    @Column(name = "common_table_id")
    private Long commonTableId;

    /**
     * 表单配置
     */
    @Lob
    @Column(name = "form_data")
    private String formData;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public ProcessFormConfig processDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
        return this;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getTaskNodeId() {
        return taskNodeId;
    }

    public ProcessFormConfig taskNodeId(String taskNodeId) {
        this.taskNodeId = taskNodeId;
        return this;
    }

    public void setTaskNodeId(String taskNodeId) {
        this.taskNodeId = taskNodeId;
    }

    public String getTaskNodeName() {
        return taskNodeName;
    }

    public ProcessFormConfig taskNodeName(String taskNodeName) {
        this.taskNodeName = taskNodeName;
        return this;
    }

    public void setTaskNodeName(String taskNodeName) {
        this.taskNodeName = taskNodeName;
    }

    public Long getCommonTableId() {
        return commonTableId;
    }

    public ProcessFormConfig commonTableId(Long commonTableId) {
        this.commonTableId = commonTableId;
        return this;
    }

    public void setCommonTableId(Long commonTableId) {
        this.commonTableId = commonTableId;
    }

    public String getFormData() {
        return formData;
    }

    public ProcessFormConfig formData(String formData) {
        this.formData = formData;
        return this;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessFormConfig)) {
            return false;
        }
        return id != null && id.equals(((ProcessFormConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProcessFormConfig{" +
            "id=" + getId() +
            ", processDefinitionKey='" + getProcessDefinitionKey() + "'" +
            ", taskNodeId='" + getTaskNodeId() + "'" +
            ", taskNodeName='" + getTaskNodeName() + "'" +
            ", commonTableId=" + getCommonTableId() +
            ", formData='" + getFormData() + "'" +
            "}";
    }
}
