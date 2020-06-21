package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.aidriveall.cms.domain.enumeration.RelationshipType;
import com.aidriveall.cms.domain.enumeration.SourceType;
import com.aidriveall.cms.domain.enumeration.FixedType;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.CommonTableRelationship} entity.
 */
@ApiModel(description = "模型关系")
public class CommonTableRelationshipDTO implements Serializable {

    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 关系类型
     */
    @NotNull
    @ApiModelProperty(value = "关系类型", required = true)
    private RelationshipType relationshipType;

    /**
     * 来源类型
     */
    @ApiModelProperty(value = "来源类型")
    private SourceType sourceType;

    /**
     * 关联表显示字段
     */
    @Size(max = 100)
    @ApiModelProperty(value = "关联表显示字段")
    private String otherEntityField;

    /**
     * 关联实体名称
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "关联实体名称", required = true)
    private String otherEntityName;

    /**
     * 关系属性名称
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "关系属性名称", required = true)
    private String relationshipName;

    /**
     * 对方属性名称
     */
    @Size(max = 100)
    @ApiModelProperty(value = "对方属性名称")
    private String otherEntityRelationshipName;

    /**
     * 列宽
     */
    @ApiModelProperty(value = "列宽")
    private Integer columnWidth;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private Integer order;

    /**
     * 列固定
     */
    @ApiModelProperty(value = "列固定")
    private FixedType fixed;

    /**
     * 行内编辑
     */
    @ApiModelProperty(value = "行内编辑")
    private Boolean editInList;

    /**
     * 可过滤
     */
    @ApiModelProperty(value = "可过滤")
    private Boolean enableFilter;

    /**
     * 列表隐藏
     */
    @ApiModelProperty(value = "列表隐藏")
    private Boolean hideInList;

    /**
     * 表单隐藏
     */
    @ApiModelProperty(value = "表单隐藏")
    private Boolean hideInForm;

    /**
     * 字体颜色
     */
    @Size(max = 80)
    @ApiModelProperty(value = "字体颜色")
    private String fontColor;

    /**
     * 列背景色
     */
    @Size(max = 80)
    @ApiModelProperty(value = "列背景色")
    private String backgroundColor;

    /**
     * 详细字段说明
     */
    @Size(max = 200)
    @ApiModelProperty(value = "详细字段说明")
    private String help;

    /**
     * 是否维护端
     */
    @ApiModelProperty(value = "是否维护端")
    private Boolean ownerSide;

    /**
     * 数据源名称
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "数据源名称", required = true)
    private String dataName;

    /**
     * Web控件类型
     */
    @Size(max = 100)
    @ApiModelProperty(value = "Web控件类型")
    private String webComponentType;

    /**
     * 是否树形实体
     */
    @ApiModelProperty(value = "是否树形实体")
    private Boolean otherEntityIsTree;

    /**
     * 显示在过滤树
     */
    @ApiModelProperty(value = "显示在过滤树")
    private Boolean showInFilterTree;

    /**
     * 字典表代码
     */
    @Size(max = 100)
    @ApiModelProperty(value = "字典表代码")
    private String dataDictionaryCode;

    /**
     * 前端只读
     */
    @ApiModelProperty(value = "前端只读")
    private Boolean clientReadOnly;


    /**
     * 关联实体
     */
    @ApiModelProperty(value = "关联实体")
    private Long relationEntityId;
    private String relationEntityName;
    /**
     * 字典表节点
     */
    @ApiModelProperty(value = "字典表节点")
    private Long dataDictionaryNodeId;
    private String dataDictionaryNodeName;
    /**
     * 所属表
     */
    @ApiModelProperty(value = "所属表")
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

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getOtherEntityField() {
        return otherEntityField;
    }

    public void setOtherEntityField(String otherEntityField) {
        this.otherEntityField = otherEntityField;
    }

    public String getOtherEntityName() {
        return otherEntityName;
    }

    public void setOtherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public String getOtherEntityRelationshipName() {
        return otherEntityRelationshipName;
    }

    public void setOtherEntityRelationshipName(String otherEntityRelationshipName) {
        this.otherEntityRelationshipName = otherEntityRelationshipName;
    }

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public FixedType getFixed() {
        return fixed;
    }

    public void setFixed(FixedType fixed) {
        this.fixed = fixed;
    }

    public Boolean isEditInList() {
        return editInList;
    }

    public void setEditInList(Boolean editInList) {
        this.editInList = editInList;
    }

    public Boolean isEnableFilter() {
        return enableFilter;
    }

    public void setEnableFilter(Boolean enableFilter) {
        this.enableFilter = enableFilter;
    }

    public Boolean isHideInList() {
        return hideInList;
    }

    public void setHideInList(Boolean hideInList) {
        this.hideInList = hideInList;
    }

    public Boolean isHideInForm() {
        return hideInForm;
    }

    public void setHideInForm(Boolean hideInForm) {
        this.hideInForm = hideInForm;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public Boolean isOwnerSide() {
        return ownerSide;
    }

    public void setOwnerSide(Boolean ownerSide) {
        this.ownerSide = ownerSide;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getWebComponentType() {
        return webComponentType;
    }

    public void setWebComponentType(String webComponentType) {
        this.webComponentType = webComponentType;
    }

    public Boolean isOtherEntityIsTree() {
        return otherEntityIsTree;
    }

    public void setOtherEntityIsTree(Boolean otherEntityIsTree) {
        this.otherEntityIsTree = otherEntityIsTree;
    }

    public Boolean isShowInFilterTree() {
        return showInFilterTree;
    }

    public void setShowInFilterTree(Boolean showInFilterTree) {
        this.showInFilterTree = showInFilterTree;
    }

    public String getDataDictionaryCode() {
        return dataDictionaryCode;
    }

    public void setDataDictionaryCode(String dataDictionaryCode) {
        this.dataDictionaryCode = dataDictionaryCode;
    }

    public Boolean isClientReadOnly() {
        return clientReadOnly;
    }

    public void setClientReadOnly(Boolean clientReadOnly) {
        this.clientReadOnly = clientReadOnly;
    }

    public Long getRelationEntityId() {
        return relationEntityId;
    }

    public void setRelationEntityId(Long commonTableId) {
        this.relationEntityId = commonTableId;
    }
    
    public String getRelationEntityName() {
        return relationEntityName;
    }

    public void setRelationEntityName(String commonTableName) {
        this.relationEntityName = commonTableName;
    }

    public Long getDataDictionaryNodeId() {
        return dataDictionaryNodeId;
    }

    public void setDataDictionaryNodeId(Long dataDictionaryId) {
        this.dataDictionaryNodeId = dataDictionaryId;
    }
    
    public String getDataDictionaryNodeName() {
        return dataDictionaryNodeName;
    }

    public void setDataDictionaryNodeName(String dataDictionaryName) {
        this.dataDictionaryNodeName = dataDictionaryName;
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

        CommonTableRelationshipDTO commonTableRelationshipDTO = (CommonTableRelationshipDTO) o;
        if (commonTableRelationshipDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commonTableRelationshipDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommonTableRelationshipDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", relationshipType='" + getRelationshipType() + "'" +
            ", sourceType='" + getSourceType() + "'" +
            ", otherEntityField='" + getOtherEntityField() + "'" +
            ", otherEntityName='" + getOtherEntityName() + "'" +
            ", relationshipName='" + getRelationshipName() + "'" +
            ", otherEntityRelationshipName='" + getOtherEntityRelationshipName() + "'" +
            ", columnWidth=" + getColumnWidth() +
            ", order=" + getOrder() +
            ", fixed='" + getFixed() + "'" +
            ", editInList='" + isEditInList() + "'" +
            ", enableFilter='" + isEnableFilter() + "'" +
            ", hideInList='" + isHideInList() + "'" +
            ", hideInForm='" + isHideInForm() + "'" +
            ", fontColor='" + getFontColor() + "'" +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", help='" + getHelp() + "'" +
            ", ownerSide='" + isOwnerSide() + "'" +
            ", dataName='" + getDataName() + "'" +
            ", webComponentType='" + getWebComponentType() + "'" +
            ", otherEntityIsTree='" + isOtherEntityIsTree() + "'" +
            ", showInFilterTree='" + isShowInFilterTree() + "'" +
            ", dataDictionaryCode='" + getDataDictionaryCode() + "'" +
            ", clientReadOnly='" + isClientReadOnly() + "'" +
            ", relationEntity=" + getRelationEntityId() +
            ", relationEntityName='" + getRelationEntityName() + "'" +
            ", dataDictionaryNode=" + getDataDictionaryNodeId() +
            ", dataDictionaryNodeName='" + getDataDictionaryNodeName() + "'" +
            ", commonTable=" + getCommonTableId() +
            ", commonTableName='" + getCommonTableName() + "'" +
            "}";
    }
}
