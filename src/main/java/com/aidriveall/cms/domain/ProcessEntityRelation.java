package com.aidriveall.cms.domain;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;

import com.honmtech.cms.domain.enumeration.ProcessEntityStatus;
import org.hibernate.annotations.Cache;

/**
 * 流程与实体关联
 */

@Entity
@Table(name = "process_entity_relation")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessEntityRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 宿主实体名称
     */
    @Column(name = "entity_type", insertable = false, updatable = false)
    private String entityType;

    /**
     * 宿主实体Id
     */
    @Column(name = "entity_id", insertable = false, updatable = false)
    private Long entityId;

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Any(metaDef = "OwnerMetaDef",metaColumn = @Column(name = "owner_type"), fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Owner entity;
    /**
     * 流程实例Id
     */
    @Column(name = "process_instance_id")
    private String processInstanceId;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProcessEntityStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public ProcessEntityRelation entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public ProcessEntityRelation entityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public ProcessEntityRelation processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public ProcessEntityStatus getStatus() {
        return status;
    }

    public ProcessEntityRelation status(ProcessEntityStatus status) {
        this.status = status;
        return this;
    }

    public Owner getEntity() {
        return entity;
    }

    public void setEntity(Owner entity) {
        this.entity = entity;
    }

    public ProcessEntityRelation entity(Owner entity) {
        this.entity = entity;
        return this;
    }

    public void setStatus(ProcessEntityStatus status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessEntityRelation)) {
            return false;
        }
        return id != null && id.equals(((ProcessEntityRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProcessEntityRelation{" +
            "id=" + getId() +
            ", entityType='" + getEntityType() + "'" +
            ", entityId=" + getEntityId() +
            ", processInstanceId='" + getProcessInstanceId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
