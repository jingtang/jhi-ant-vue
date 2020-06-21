package com.aidriveall.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.aidriveall.cms.domain.enumeration.RelationshipType;
import com.aidriveall.cms.domain.enumeration.SourceType;
import com.aidriveall.cms.domain.enumeration.FixedType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.aidriveall.cms.domain.CommonTableRelationship} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.CommonTableRelationshipResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /common-table-relationships?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommonTableRelationshipCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;
    /**
     * Class for filtering RelationshipType
     */
    public static class RelationshipTypeFilter extends Filter<RelationshipType> {

        public RelationshipTypeFilter() {
        }

        public RelationshipTypeFilter(RelationshipTypeFilter filter) {
            super(filter);
        }

        @Override
        public RelationshipTypeFilter copy() {
            return new RelationshipTypeFilter(this);
        }

    }
    /**
     * Class for filtering SourceType
     */
    public static class SourceTypeFilter extends Filter<SourceType> {

        public SourceTypeFilter() {
        }

        public SourceTypeFilter(SourceTypeFilter filter) {
            super(filter);
        }

        @Override
        public SourceTypeFilter copy() {
            return new SourceTypeFilter(this);
        }

    }
    /**
     * Class for filtering FixedType
     */
    public static class FixedTypeFilter extends Filter<FixedType> {

        public FixedTypeFilter() {
        }

        public FixedTypeFilter(FixedTypeFilter filter) {
            super(filter);
        }

        @Override
        public FixedTypeFilter copy() {
            return new FixedTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private RelationshipTypeFilter relationshipType;

    private SourceTypeFilter sourceType;

    private StringFilter otherEntityField;

    private StringFilter otherEntityName;

    private StringFilter relationshipName;

    private StringFilter otherEntityRelationshipName;

    private IntegerFilter columnWidth;

    private IntegerFilter order;

    private FixedTypeFilter fixed;

    private BooleanFilter editInList;

    private BooleanFilter enableFilter;

    private BooleanFilter hideInList;

    private BooleanFilter hideInForm;

    private StringFilter fontColor;

    private StringFilter backgroundColor;

    private StringFilter help;

    private BooleanFilter ownerSide;

    private StringFilter dataName;

    private StringFilter webComponentType;

    private BooleanFilter otherEntityIsTree;

    private BooleanFilter showInFilterTree;

    private StringFilter dataDictionaryCode;

    private BooleanFilter clientReadOnly;

    private LongFilter relationEntityId;

    private LongFilter dataDictionaryNodeId;

    private LongFilter commonTableId;

    public CommonTableRelationshipCriteria(){
    }

    public CommonTableRelationshipCriteria(CommonTableRelationshipCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.relationshipType = other.relationshipType == null ? null : other.relationshipType.copy();
        this.sourceType = other.sourceType == null ? null : other.sourceType.copy();
        this.otherEntityField = other.otherEntityField == null ? null : other.otherEntityField.copy();
        this.otherEntityName = other.otherEntityName == null ? null : other.otherEntityName.copy();
        this.relationshipName = other.relationshipName == null ? null : other.relationshipName.copy();
        this.otherEntityRelationshipName = other.otherEntityRelationshipName == null ? null : other.otherEntityRelationshipName.copy();
        this.columnWidth = other.columnWidth == null ? null : other.columnWidth.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.fixed = other.fixed == null ? null : other.fixed.copy();
        this.editInList = other.editInList == null ? null : other.editInList.copy();
        this.enableFilter = other.enableFilter == null ? null : other.enableFilter.copy();
        this.hideInList = other.hideInList == null ? null : other.hideInList.copy();
        this.hideInForm = other.hideInForm == null ? null : other.hideInForm.copy();
        this.fontColor = other.fontColor == null ? null : other.fontColor.copy();
        this.backgroundColor = other.backgroundColor == null ? null : other.backgroundColor.copy();
        this.help = other.help == null ? null : other.help.copy();
        this.ownerSide = other.ownerSide == null ? null : other.ownerSide.copy();
        this.dataName = other.dataName == null ? null : other.dataName.copy();
        this.webComponentType = other.webComponentType == null ? null : other.webComponentType.copy();
        this.otherEntityIsTree = other.otherEntityIsTree == null ? null : other.otherEntityIsTree.copy();
        this.showInFilterTree = other.showInFilterTree == null ? null : other.showInFilterTree.copy();
        this.dataDictionaryCode = other.dataDictionaryCode == null ? null : other.dataDictionaryCode.copy();
        this.clientReadOnly = other.clientReadOnly == null ? null : other.clientReadOnly.copy();
        this.relationEntityId = other.relationEntityId == null ? null : other.relationEntityId.copy();
        this.dataDictionaryNodeId = other.dataDictionaryNodeId == null ? null : other.dataDictionaryNodeId.copy();
        this.commonTableId = other.commonTableId == null ? null : other.commonTableId.copy();
    }

    @Override
    public CommonTableRelationshipCriteria copy() {
        return new CommonTableRelationshipCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public RelationshipTypeFilter getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipTypeFilter relationshipType) {
        this.relationshipType = relationshipType;
    }

    public SourceTypeFilter getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeFilter sourceType) {
        this.sourceType = sourceType;
    }

    public StringFilter getOtherEntityField() {
        return otherEntityField;
    }

    public void setOtherEntityField(StringFilter otherEntityField) {
        this.otherEntityField = otherEntityField;
    }

    public StringFilter getOtherEntityName() {
        return otherEntityName;
    }

    public void setOtherEntityName(StringFilter otherEntityName) {
        this.otherEntityName = otherEntityName;
    }

    public StringFilter getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(StringFilter relationshipName) {
        this.relationshipName = relationshipName;
    }

    public StringFilter getOtherEntityRelationshipName() {
        return otherEntityRelationshipName;
    }

    public void setOtherEntityRelationshipName(StringFilter otherEntityRelationshipName) {
        this.otherEntityRelationshipName = otherEntityRelationshipName;
    }

    public IntegerFilter getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(IntegerFilter columnWidth) {
        this.columnWidth = columnWidth;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public FixedTypeFilter getFixed() {
        return fixed;
    }

    public void setFixed(FixedTypeFilter fixed) {
        this.fixed = fixed;
    }

    public BooleanFilter getEditInList() {
        return editInList;
    }

    public void setEditInList(BooleanFilter editInList) {
        this.editInList = editInList;
    }

    public BooleanFilter getEnableFilter() {
        return enableFilter;
    }

    public void setEnableFilter(BooleanFilter enableFilter) {
        this.enableFilter = enableFilter;
    }

    public BooleanFilter getHideInList() {
        return hideInList;
    }

    public void setHideInList(BooleanFilter hideInList) {
        this.hideInList = hideInList;
    }

    public BooleanFilter getHideInForm() {
        return hideInForm;
    }

    public void setHideInForm(BooleanFilter hideInForm) {
        this.hideInForm = hideInForm;
    }

    public StringFilter getFontColor() {
        return fontColor;
    }

    public void setFontColor(StringFilter fontColor) {
        this.fontColor = fontColor;
    }

    public StringFilter getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(StringFilter backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public StringFilter getHelp() {
        return help;
    }

    public void setHelp(StringFilter help) {
        this.help = help;
    }

    public BooleanFilter getOwnerSide() {
        return ownerSide;
    }

    public void setOwnerSide(BooleanFilter ownerSide) {
        this.ownerSide = ownerSide;
    }

    public StringFilter getDataName() {
        return dataName;
    }

    public void setDataName(StringFilter dataName) {
        this.dataName = dataName;
    }

    public StringFilter getWebComponentType() {
        return webComponentType;
    }

    public void setWebComponentType(StringFilter webComponentType) {
        this.webComponentType = webComponentType;
    }

    public BooleanFilter getOtherEntityIsTree() {
        return otherEntityIsTree;
    }

    public void setOtherEntityIsTree(BooleanFilter otherEntityIsTree) {
        this.otherEntityIsTree = otherEntityIsTree;
    }

    public BooleanFilter getShowInFilterTree() {
        return showInFilterTree;
    }

    public void setShowInFilterTree(BooleanFilter showInFilterTree) {
        this.showInFilterTree = showInFilterTree;
    }

    public StringFilter getDataDictionaryCode() {
        return dataDictionaryCode;
    }

    public void setDataDictionaryCode(StringFilter dataDictionaryCode) {
        this.dataDictionaryCode = dataDictionaryCode;
    }

    public BooleanFilter getClientReadOnly() {
        return clientReadOnly;
    }

    public void setClientReadOnly(BooleanFilter clientReadOnly) {
        this.clientReadOnly = clientReadOnly;
    }

    public LongFilter getRelationEntityId() {
        return relationEntityId;
    }

    public void setRelationEntityId(LongFilter relationEntityId) {
        this.relationEntityId = relationEntityId;
    }

    public LongFilter getDataDictionaryNodeId() {
        return dataDictionaryNodeId;
    }

    public void setDataDictionaryNodeId(LongFilter dataDictionaryNodeId) {
        this.dataDictionaryNodeId = dataDictionaryNodeId;
    }

    public LongFilter getCommonTableId() {
        return commonTableId;
    }

    public void setCommonTableId(LongFilter commonTableId) {
        this.commonTableId = commonTableId;
    }

    public String getJhiCommonSearchKeywords() {
        return jhiCommonSearchKeywords;
    }

    public void setJhiCommonSearchKeywords(String jhiCommonSearchKeywords) {
        this.jhiCommonSearchKeywords = jhiCommonSearchKeywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommonTableRelationshipCriteria that = (CommonTableRelationshipCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(relationshipType, that.relationshipType) &&
            Objects.equals(sourceType, that.sourceType) &&
            Objects.equals(otherEntityField, that.otherEntityField) &&
            Objects.equals(otherEntityName, that.otherEntityName) &&
            Objects.equals(relationshipName, that.relationshipName) &&
            Objects.equals(otherEntityRelationshipName, that.otherEntityRelationshipName) &&
            Objects.equals(columnWidth, that.columnWidth) &&
            Objects.equals(order, that.order) &&
            Objects.equals(fixed, that.fixed) &&
            Objects.equals(editInList, that.editInList) &&
            Objects.equals(enableFilter, that.enableFilter) &&
            Objects.equals(hideInList, that.hideInList) &&
            Objects.equals(hideInForm, that.hideInForm) &&
            Objects.equals(fontColor, that.fontColor) &&
            Objects.equals(backgroundColor, that.backgroundColor) &&
            Objects.equals(help, that.help) &&
            Objects.equals(ownerSide, that.ownerSide) &&
            Objects.equals(dataName, that.dataName) &&
            Objects.equals(webComponentType, that.webComponentType) &&
            Objects.equals(otherEntityIsTree, that.otherEntityIsTree) &&
            Objects.equals(showInFilterTree, that.showInFilterTree) &&
            Objects.equals(dataDictionaryCode, that.dataDictionaryCode) &&
            Objects.equals(clientReadOnly, that.clientReadOnly) &&
            Objects.equals(relationEntityId, that.relationEntityId) &&
            Objects.equals(dataDictionaryNodeId, that.dataDictionaryNodeId) &&
            Objects.equals(commonTableId, that.commonTableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        relationshipType,
        sourceType,
        otherEntityField,
        otherEntityName,
        relationshipName,
        otherEntityRelationshipName,
        columnWidth,
        order,
        fixed,
        editInList,
        enableFilter,
        hideInList,
        hideInForm,
        fontColor,
        backgroundColor,
        help,
        ownerSide,
        dataName,
        webComponentType,
        otherEntityIsTree,
        showInFilterTree,
        dataDictionaryCode,
        clientReadOnly,
        relationEntityId,
        dataDictionaryNodeId,
        commonTableId
        );
    }

    @Override
    public String toString() {
        return "CommonTableRelationshipCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (relationshipType != null ? "relationshipType=" + relationshipType + ", " : "") +
                (sourceType != null ? "sourceType=" + sourceType + ", " : "") +
                (otherEntityField != null ? "otherEntityField=" + otherEntityField + ", " : "") +
                (otherEntityName != null ? "otherEntityName=" + otherEntityName + ", " : "") +
                (relationshipName != null ? "relationshipName=" + relationshipName + ", " : "") +
                (otherEntityRelationshipName != null ? "otherEntityRelationshipName=" + otherEntityRelationshipName + ", " : "") +
                (columnWidth != null ? "columnWidth=" + columnWidth + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (fixed != null ? "fixed=" + fixed + ", " : "") +
                (editInList != null ? "editInList=" + editInList + ", " : "") +
                (enableFilter != null ? "enableFilter=" + enableFilter + ", " : "") +
                (hideInList != null ? "hideInList=" + hideInList + ", " : "") +
                (hideInForm != null ? "hideInForm=" + hideInForm + ", " : "") +
                (fontColor != null ? "fontColor=" + fontColor + ", " : "") +
                (backgroundColor != null ? "backgroundColor=" + backgroundColor + ", " : "") +
                (help != null ? "help=" + help + ", " : "") +
                (ownerSide != null ? "ownerSide=" + ownerSide + ", " : "") +
                (dataName != null ? "dataName=" + dataName + ", " : "") +
                (webComponentType != null ? "webComponentType=" + webComponentType + ", " : "") +
                (otherEntityIsTree != null ? "otherEntityIsTree=" + otherEntityIsTree + ", " : "") +
                (showInFilterTree != null ? "showInFilterTree=" + showInFilterTree + ", " : "") +
                (dataDictionaryCode != null ? "dataDictionaryCode=" + dataDictionaryCode + ", " : "") +
                (clientReadOnly != null ? "clientReadOnly=" + clientReadOnly + ", " : "") +
                (relationEntityId != null ? "relationEntityId=" + relationEntityId + ", " : "") +
                (dataDictionaryNodeId != null ? "dataDictionaryNodeId=" + dataDictionaryNodeId + ", " : "") +
                (commonTableId != null ? "commonTableId=" + commonTableId + ", " : "") +
            "}";
    }

}
