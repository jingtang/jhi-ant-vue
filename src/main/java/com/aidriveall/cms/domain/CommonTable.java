package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 模型
 */

@Entity
@Table(name = "common_table")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommonTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 80)
    @Column(name = "name", length = 80, nullable = false)
    private String name;

    /**
     * 实体名称
     */
    @NotNull
    @Size(max = 80)
    @Column(name = "entity_name", length = 80, nullable = false)
    private String entityName;

    /**
     * 数据库表名
     */
    @NotNull
    @Size(max = 80)
    @Column(name = "table_name", length = 80, nullable = false)
    private String tableName;

    /**
     * 系统表
     */
    @Column(name = "jhi_system")
    private Boolean system;

    /**
     * 类名
     */
    @NotNull
    @Size(max = 80)
    @Column(name = "clazz_name", length = 80, nullable = false)
    private String clazzName;

    /**
     * 是否生成
     */
    @Column(name = "[generated]")
    private Boolean generated;

    /**
     * 创建时间
     */
    @Column(name = "creat_at")
    private ZonedDateTime creatAt;

    /**
     * 生成表时间
     */
    @Column(name = "generate_at")
    private ZonedDateTime generateAt;

    /**
     * 编译时间
     */
    @Column(name = "generate_class_at")
    private ZonedDateTime generateClassAt;

    /**
     * 表说明
     */
    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    /**
     * 树形表
     */
    @Column(name = "tree_table")
    private Boolean treeTable;

    /**
     * 来源Id
     */
    @Column(name = "base_table_id")
    private Long baseTableId;

    /**
     * 操作栏宽度
     */
    @Column(name = "record_action_width")
    private Integer recordActionWidth;

    /**
     * 前端列表配置
     */
    @Lob
    @Column(name = "list_config")
    private String listConfig;

    /**
     * 前端表单配置
     */
    @Lob
    @Column(name = "form_config")
    private String formConfig;

    /**
     * 字段
     */
    @OneToMany(mappedBy = "commonTable")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @org.hibernate.annotations.OrderBy(clause = "order asc")
    private Set<CommonTableField> commonTableFields = new LinkedHashSet<>();

    /**
     * 关系
     */
    @OneToMany(mappedBy = "commonTable")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @org.hibernate.annotations.OrderBy(clause = "order asc")
    private Set<CommonTableRelationship> relationships = new LinkedHashSet<>();

    /**
     * 创建人
     */
    @ManyToOne
    @JsonIgnoreProperties("commonTables")
    private User creator;
    /**
     * 业务类型
     */
    @ManyToOne
    @JsonIgnoreProperties("commonTables")
    private BusinessType businessType;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CommonTable name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntityName() {
        return entityName;
    }

    public CommonTable entityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public CommonTable tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Boolean isSystem() {
        return system;
    }

    public CommonTable system(Boolean system) {
        this.system = system;
        return this;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }


    public String getClazzName() {
        return clazzName;
    }

    public CommonTable clazzName(String clazzName) {
        this.clazzName = clazzName;
        return this;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public Boolean isGenerated() {
        return generated;
    }

    public CommonTable generated(Boolean generated) {
        this.generated = generated;
        return this;
    }

    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }

    public ZonedDateTime getCreatAt() {
        return creatAt;
    }

    public CommonTable creatAt(ZonedDateTime creatAt) {
        this.creatAt = creatAt;
        return this;
    }

    public void setCreatAt(ZonedDateTime creatAt) {
        this.creatAt = creatAt;
    }

    public ZonedDateTime getGenerateAt() {
        return generateAt;
    }

    public CommonTable generateAt(ZonedDateTime generateAt) {
        this.generateAt = generateAt;
        return this;
    }

    public void setGenerateAt(ZonedDateTime generateAt) {
        this.generateAt = generateAt;
    }

    public ZonedDateTime getGenerateClassAt() {
        return generateClassAt;
    }

    public CommonTable generateClassAt(ZonedDateTime generateClassAt) {
        this.generateClassAt = generateClassAt;
        return this;
    }

    public void setGenerateClassAt(ZonedDateTime generateClassAt) {
        this.generateClassAt = generateClassAt;
    }

    public String getDescription() {
        return description;
    }

    public CommonTable description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isTreeTable() {
        return treeTable;
    }

    public CommonTable treeTable(Boolean treeTable) {
        this.treeTable = treeTable;
        return this;
    }

    public void setTreeTable(Boolean treeTable) {
        this.treeTable = treeTable;
    }

    public Long getBaseTableId() {
        return baseTableId;
    }

    public CommonTable baseTableId(Long baseTableId) {
        this.baseTableId = baseTableId;
        return this;
    }

    public void setBaseTableId(Long baseTableId) {
        this.baseTableId = baseTableId;
    }

    public Integer getRecordActionWidth() {
        return recordActionWidth;
    }

    public CommonTable recordActionWidth(Integer recordActionWidth) {
        this.recordActionWidth = recordActionWidth;
        return this;
    }

    public void setRecordActionWidth(Integer recordActionWidth) {
        this.recordActionWidth = recordActionWidth;
    }

    public String getListConfig() {
        return listConfig;
    }

    public CommonTable listConfig(String listConfig) {
        this.listConfig = listConfig;
        return this;
    }

    public void setListConfig(String listConfig) {
        this.listConfig = listConfig;
    }

    public String getFormConfig() {
        return formConfig;
    }

    public CommonTable formConfig(String formConfig) {
        this.formConfig = formConfig;
        return this;
    }

    public void setFormConfig(String formConfig) {
        this.formConfig = formConfig;
    }

    public Set<CommonTableField> getCommonTableFields() {
        return commonTableFields;
    }

    public CommonTable commonTableFields(Set<CommonTableField> commonTableFields) {
        this.commonTableFields = commonTableFields;
        return this;
    }

    public CommonTable addCommonTableFields(CommonTableField commonTableField) {
        this.commonTableFields.add(commonTableField);
        commonTableField.setCommonTable(this);
        return this;
    }

    public CommonTable removeCommonTableFields(CommonTableField commonTableField) {
        this.commonTableFields.remove(commonTableField);
        commonTableField.setCommonTable(null);
        return this;
    }

    public void setCommonTableFields(Set<CommonTableField> commonTableFields) {
        this.commonTableFields = commonTableFields;
    }

    public Set<CommonTableRelationship> getRelationships() {
        return relationships;
    }

    public CommonTable relationships(Set<CommonTableRelationship> commonTableRelationships) {
        this.relationships = commonTableRelationships;
        return this;
    }

    public CommonTable addRelationships(CommonTableRelationship commonTableRelationship) {
        this.relationships.add(commonTableRelationship);
        commonTableRelationship.setCommonTable(this);
        return this;
    }

    public CommonTable removeRelationships(CommonTableRelationship commonTableRelationship) {
        this.relationships.remove(commonTableRelationship);
        commonTableRelationship.setCommonTable(null);
        return this;
    }

    public void setRelationships(Set<CommonTableRelationship> commonTableRelationships) {
        this.relationships = commonTableRelationships;
    }

    public User getCreator() {
        return creator;
    }

    public CommonTable creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public CommonTable businessType(BusinessType businessType) {
        this.businessType = businessType;
        return this;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonTable)) {
            return false;
        }
        return id != null && id.equals(((CommonTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommonTable{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", entityName='" + getEntityName() + "'" +
            ", tableName='" + getTableName() + "'" +
            ", system='" + isSystem() + "'" +
            ", clazzName='" + getClazzName() + "'" +
            ", generated='" + isGenerated() + "'" +
            ", creatAt='" + getCreatAt() + "'" +
            ", generateAt='" + getGenerateAt() + "'" +
            ", generateClassAt='" + getGenerateClassAt() + "'" +
            ", description='" + getDescription() + "'" +
            ", treeTable='" + isTreeTable() + "'" +
            ", baseTableId=" + getBaseTableId() +
            ", recordActionWidth=" + getRecordActionWidth() +
            ", listConfig='" + getListConfig() + "'" +
            ", formConfig='" + getFormConfig() + "'" +
            "}";
    }
}
