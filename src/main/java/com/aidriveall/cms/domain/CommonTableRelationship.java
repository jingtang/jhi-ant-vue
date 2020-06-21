package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.aidriveall.cms.domain.enumeration.RelationshipType;

import com.aidriveall.cms.domain.enumeration.SourceType;

import com.aidriveall.cms.domain.enumeration.FixedType;

/**
 * 模型关系
 */

@Entity
@Table(name = "common_table_relationship")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommonTableRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * 关系类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_type", nullable = false)
    private RelationshipType relationshipType;

    /**
     * 来源类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "source_type")
    private SourceType sourceType;

    /**
     * 关联表显示字段
     */
    @Size(max = 100)
    @Column(name = "other_entity_field", length = 100)
    private String otherEntityField;

    /**
     * 关联实体名称
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "other_entity_name", length = 100, nullable = false)
    private String otherEntityName;

    /**
     * 关系属性名称
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "relationship_name", length = 100, nullable = false)
    private String relationshipName;

    /**
     * 对方属性名称
     */
    @Size(max = 100)
    @Column(name = "other_entity_relationship_name", length = 100)
    private String otherEntityRelationshipName;

    /**
     * 列宽
     */
    @Column(name = "column_width")
    private Integer columnWidth;

    /**
     * 显示顺序
     */
    @Column(name = "jhi_order")
    private Integer order;

    /**
     * 列固定
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "fixed")
    private FixedType fixed;

    /**
     * 行内编辑
     */
    @Column(name = "edit_in_list")
    private Boolean editInList;

    /**
     * 可过滤
     */
    @Column(name = "enable_filter")
    private Boolean enableFilter;

    /**
     * 列表隐藏
     */
    @Column(name = "hide_in_list")
    private Boolean hideInList;

    /**
     * 表单隐藏
     */
    @Column(name = "hide_in_form")
    private Boolean hideInForm;

    /**
     * 字体颜色
     */
    @Size(max = 80)
    @Column(name = "font_color", length = 80)
    private String fontColor;

    /**
     * 列背景色
     */
    @Size(max = 80)
    @Column(name = "background_color", length = 80)
    private String backgroundColor;

    /**
     * 详细字段说明
     */
    @Size(max = 200)
    @Column(name = "help", length = 200)
    private String help;

    /**
     * 是否维护端
     */
    @Column(name = "owner_side")
    private Boolean ownerSide;

    /**
     * 数据源名称
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "data_name", length = 100, nullable = false)
    private String dataName;

    /**
     * Web控件类型
     */
    @Size(max = 100)
    @Column(name = "web_component_type", length = 100)
    private String webComponentType;

    /**
     * 是否树形实体
     */
    @Column(name = "other_entity_is_tree")
    private Boolean otherEntityIsTree;

    /**
     * 显示在过滤树
     */
    @Column(name = "show_in_filter_tree")
    private Boolean showInFilterTree;

    /**
     * 字典表代码
     */
    @Size(max = 100)
    @Column(name = "data_dictionary_code", length = 100)
    private String dataDictionaryCode;

    /**
     * 前端只读
     */
    @Column(name = "client_read_only")
    private Boolean clientReadOnly;

    /**
     * 关联实体
     */
    @ManyToOne
    @JsonIgnoreProperties("commonTableRelationships")
    private CommonTable relationEntity;
    /**
     * 字典表节点
     */
    @ManyToOne
    @JsonIgnoreProperties("commonTableRelationships")
    private DataDictionary dataDictionaryNode;
    /**
     * 所属表
     */
    @ManyToOne
    @JsonIgnoreProperties("relationships")
    private CommonTable commonTable;
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

    public CommonTableRelationship name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public CommonTableRelationship relationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
        return this;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public CommonTableRelationship sourceType(SourceType sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getOtherEntityField() {
        return otherEntityField;
    }

    public CommonTableRelationship otherEntityField(String otherEntityField) {
        this.otherEntityField = otherEntityField;
        return this;
    }

    public void setOtherEntityField(String otherEntityField) {
        this.otherEntityField = otherEntityField;
    }

    public String getOtherEntityName() {
        return otherEntityName;
    }

    public CommonTableRelationship otherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
        return this;
    }

    public void setOtherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public CommonTableRelationship relationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
        return this;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public String getOtherEntityRelationshipName() {
        return otherEntityRelationshipName;
    }

    public CommonTableRelationship otherEntityRelationshipName(String otherEntityRelationshipName) {
        this.otherEntityRelationshipName = otherEntityRelationshipName;
        return this;
    }

    public void setOtherEntityRelationshipName(String otherEntityRelationshipName) {
        this.otherEntityRelationshipName = otherEntityRelationshipName;
    }

    public Integer getColumnWidth() {
        return columnWidth;
    }

    public CommonTableRelationship columnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
        return this;
    }

    public void setColumnWidth(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    public Integer getOrder() {
        return order;
    }

    public CommonTableRelationship order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public FixedType getFixed() {
        return fixed;
    }

    public CommonTableRelationship fixed(FixedType fixed) {
        this.fixed = fixed;
        return this;
    }

    public void setFixed(FixedType fixed) {
        this.fixed = fixed;
    }

    public Boolean isEditInList() {
        return editInList;
    }

    public CommonTableRelationship editInList(Boolean editInList) {
        this.editInList = editInList;
        return this;
    }

    public void setEditInList(Boolean editInList) {
        this.editInList = editInList;
    }

    public Boolean isEnableFilter() {
        return enableFilter;
    }

    public CommonTableRelationship enableFilter(Boolean enableFilter) {
        this.enableFilter = enableFilter;
        return this;
    }

    public void setEnableFilter(Boolean enableFilter) {
        this.enableFilter = enableFilter;
    }

    public Boolean isHideInList() {
        return hideInList;
    }

    public CommonTableRelationship hideInList(Boolean hideInList) {
        this.hideInList = hideInList;
        return this;
    }

    public void setHideInList(Boolean hideInList) {
        this.hideInList = hideInList;
    }

    public Boolean isHideInForm() {
        return hideInForm;
    }

    public CommonTableRelationship hideInForm(Boolean hideInForm) {
        this.hideInForm = hideInForm;
        return this;
    }

    public void setHideInForm(Boolean hideInForm) {
        this.hideInForm = hideInForm;
    }

    public String getFontColor() {
        return fontColor;
    }

    public CommonTableRelationship fontColor(String fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public CommonTableRelationship backgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getHelp() {
        return help;
    }

    public CommonTableRelationship help(String help) {
        this.help = help;
        return this;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public Boolean isOwnerSide() {
        return ownerSide;
    }

    public CommonTableRelationship ownerSide(Boolean ownerSide) {
        this.ownerSide = ownerSide;
        return this;
    }

    public void setOwnerSide(Boolean ownerSide) {
        this.ownerSide = ownerSide;
    }

    public String getDataName() {
        return dataName;
    }

    public CommonTableRelationship dataName(String dataName) {
        this.dataName = dataName;
        return this;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getWebComponentType() {
        return webComponentType;
    }

    public CommonTableRelationship webComponentType(String webComponentType) {
        this.webComponentType = webComponentType;
        return this;
    }

    public void setWebComponentType(String webComponentType) {
        this.webComponentType = webComponentType;
    }

    public Boolean isOtherEntityIsTree() {
        return otherEntityIsTree;
    }

    public CommonTableRelationship otherEntityIsTree(Boolean otherEntityIsTree) {
        this.otherEntityIsTree = otherEntityIsTree;
        return this;
    }

    public void setOtherEntityIsTree(Boolean otherEntityIsTree) {
        this.otherEntityIsTree = otherEntityIsTree;
    }

    public Boolean isShowInFilterTree() {
        return showInFilterTree;
    }

    public CommonTableRelationship showInFilterTree(Boolean showInFilterTree) {
        this.showInFilterTree = showInFilterTree;
        return this;
    }

    public void setShowInFilterTree(Boolean showInFilterTree) {
        this.showInFilterTree = showInFilterTree;
    }

    public String getDataDictionaryCode() {
        return dataDictionaryCode;
    }

    public CommonTableRelationship dataDictionaryCode(String dataDictionaryCode) {
        this.dataDictionaryCode = dataDictionaryCode;
        return this;
    }

    public void setDataDictionaryCode(String dataDictionaryCode) {
        this.dataDictionaryCode = dataDictionaryCode;
    }

    public Boolean isClientReadOnly() {
        return clientReadOnly;
    }

    public CommonTableRelationship clientReadOnly(Boolean clientReadOnly) {
        this.clientReadOnly = clientReadOnly;
        return this;
    }

    public void setClientReadOnly(Boolean clientReadOnly) {
        this.clientReadOnly = clientReadOnly;
    }

    public CommonTable getRelationEntity() {
        return relationEntity;
    }

    public CommonTableRelationship relationEntity(CommonTable commonTable) {
        this.relationEntity = commonTable;
        return this;
    }

    public void setRelationEntity(CommonTable commonTable) {
        this.relationEntity = commonTable;
    }

    public DataDictionary getDataDictionaryNode() {
        return dataDictionaryNode;
    }

    public CommonTableRelationship dataDictionaryNode(DataDictionary dataDictionary) {
        this.dataDictionaryNode = dataDictionary;
        return this;
    }

    public void setDataDictionaryNode(DataDictionary dataDictionary) {
        this.dataDictionaryNode = dataDictionary;
    }

    public CommonTable getCommonTable() {
        return commonTable;
    }

    public CommonTableRelationship commonTable(CommonTable commonTable) {
        this.commonTable = commonTable;
        return this;
    }

    public void setCommonTable(CommonTable commonTable) {
        this.commonTable = commonTable;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonTableRelationship)) {
            return false;
        }
        return id != null && id.equals(((CommonTableRelationship) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommonTableRelationship{" +
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
            "}";
    }
}
