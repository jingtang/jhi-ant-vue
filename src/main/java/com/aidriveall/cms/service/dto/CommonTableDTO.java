package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.CommonTable} entity.
 */
@ApiModel(description = "模型")
public class CommonTableDTO implements Serializable {

    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 80)
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 实体名称
     */
    @NotNull
    @Size(max = 80)
    @ApiModelProperty(value = "实体名称", required = true)
    private String entityName;

    /**
     * 数据库表名
     */
    @NotNull
    @Size(max = 80)
    @ApiModelProperty(value = "数据库表名", required = true)
    private String tableName;

    /**
     * 系统表
     */
    @ApiModelProperty(value = "系统表")
    private Boolean system;

    /**
     * 类名
     */
    @NotNull
    @Size(max = 80)
    @ApiModelProperty(value = "类名", required = true)
    private String clazzName;

    /**
     * 是否生成
     */
    @ApiModelProperty(value = "是否生成")
    private Boolean generated;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private ZonedDateTime creatAt;

    /**
     * 生成表时间
     */
    @ApiModelProperty(value = "生成表时间")
    private ZonedDateTime generateAt;

    /**
     * 编译时间
     */
    @ApiModelProperty(value = "编译时间")
    private ZonedDateTime generateClassAt;

    /**
     * 表说明
     */
    @Size(max = 200)
    @ApiModelProperty(value = "表说明")
    private String description;

    /**
     * 树形表
     */
    @ApiModelProperty(value = "树形表")
    private Boolean treeTable;

    /**
     * 来源Id
     */
    @ApiModelProperty(value = "来源Id")
    private Long baseTableId;

    /**
     * 操作栏宽度
     */
    @ApiModelProperty(value = "操作栏宽度")
    private Integer recordActionWidth;

    /**
     * 前端列表配置
     */
    @ApiModelProperty(value = "前端列表配置")
    @Lob
    private String listConfig;

    /**
     * 前端表单配置
     */
    @ApiModelProperty(value = "前端表单配置")
    @Lob
    private String formConfig;


    /**
     * 字段
     */
    @ApiModelProperty(value = "字段")
    private LinkedHashSet<CommonTableFieldDTO> commonTableFields = new LinkedHashSet<>();
    /**
     * 关系
     */
    @ApiModelProperty(value = "关系")
    private LinkedHashSet<CommonTableRelationshipDTO> relationships = new LinkedHashSet<>();
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long creatorId;
    private String creatorImageUrl;
    private String creatorLogin;
    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型")
    private Long businessTypeId;
    private String businessTypeName;

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

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Boolean isSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public Boolean isGenerated() {
        return generated;
    }

    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }

    public ZonedDateTime getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(ZonedDateTime creatAt) {
        this.creatAt = creatAt;
    }

    public ZonedDateTime getGenerateAt() {
        return generateAt;
    }

    public void setGenerateAt(ZonedDateTime generateAt) {
        this.generateAt = generateAt;
    }

    public ZonedDateTime getGenerateClassAt() {
        return generateClassAt;
    }

    public void setGenerateClassAt(ZonedDateTime generateClassAt) {
        this.generateClassAt = generateClassAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isTreeTable() {
        return treeTable;
    }

    public void setTreeTable(Boolean treeTable) {
        this.treeTable = treeTable;
    }

    public Long getBaseTableId() {
        return baseTableId;
    }

    public void setBaseTableId(Long baseTableId) {
        this.baseTableId = baseTableId;
    }

    public Integer getRecordActionWidth() {
        return recordActionWidth;
    }

    public void setRecordActionWidth(Integer recordActionWidth) {
        this.recordActionWidth = recordActionWidth;
    }

    public String getListConfig() {
        return listConfig;
    }

    public void setListConfig(String listConfig) {
        this.listConfig = listConfig;
    }

    public String getFormConfig() {
        return formConfig;
    }

    public void setFormConfig(String formConfig) {
        this.formConfig = formConfig;
    }

    public LinkedHashSet<CommonTableFieldDTO> getCommonTableFields() {
        return this.commonTableFields;
    }

    public void setCommonTableFields(LinkedHashSet<CommonTableFieldDTO> commonTableFields) {
        this.commonTableFields = commonTableFields;
    }

    public LinkedHashSet<CommonTableRelationshipDTO> getRelationships() {
        return this.relationships;
    }

    public void setRelationships(LinkedHashSet<CommonTableRelationshipDTO> relationships) {
        this.relationships = relationships;
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

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }
    
    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
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

        CommonTableDTO commonTableDTO = (CommonTableDTO) o;
        if (commonTableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commonTableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommonTableDTO{" +
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
            ", creator=" + getCreatorId() +
            ", creatorLogin='" + getCreatorLogin() + "'" +
            ", businessType=" + getBusinessTypeId() +
            ", businessTypeName='" + getBusinessTypeName() + "'" +
            "}";
    }
}
