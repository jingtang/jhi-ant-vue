package com.aidriveall.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.aidriveall.cms.domain.enumeration.FieldType;
import com.aidriveall.cms.domain.enumeration.FixedType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.aidriveall.cms.domain.CommonTableField} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.CommonTableFieldResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /common-table-fields?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommonTableFieldCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;
    /**
     * Class for filtering FieldType
     */
    public static class FieldTypeFilter extends Filter<FieldType> {

        public FieldTypeFilter() {
        }

        public FieldTypeFilter(FieldTypeFilter filter) {
            super(filter);
        }

        @Override
        public FieldTypeFilter copy() {
            return new FieldTypeFilter(this);
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

    private StringFilter title;

    private StringFilter entityFieldName;

    private FieldTypeFilter type;

    private StringFilter tableColumnName;

    private IntegerFilter columnWidth;

    private IntegerFilter order;

    private BooleanFilter editInList;

    private BooleanFilter hideInList;

    private BooleanFilter hideInForm;

    private BooleanFilter enableFilter;

    private StringFilter validateRules;

    private BooleanFilter showInFilterTree;

    private FixedTypeFilter fixed;

    private BooleanFilter sortable;

    private BooleanFilter treeIndicator;

    private BooleanFilter clientReadOnly;

    private StringFilter fieldValues;

    private BooleanFilter notNull;

    private BooleanFilter system;

    private StringFilter help;

    private StringFilter fontColor;

    private StringFilter backgroundColor;

    private LongFilter commonTableId;

    public CommonTableFieldCriteria(){
    }

    public CommonTableFieldCriteria(CommonTableFieldCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.entityFieldName = other.entityFieldName == null ? null : other.entityFieldName.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.tableColumnName = other.tableColumnName == null ? null : other.tableColumnName.copy();
        this.columnWidth = other.columnWidth == null ? null : other.columnWidth.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.editInList = other.editInList == null ? null : other.editInList.copy();
        this.hideInList = other.hideInList == null ? null : other.hideInList.copy();
        this.hideInForm = other.hideInForm == null ? null : other.hideInForm.copy();
        this.enableFilter = other.enableFilter == null ? null : other.enableFilter.copy();
        this.validateRules = other.validateRules == null ? null : other.validateRules.copy();
        this.showInFilterTree = other.showInFilterTree == null ? null : other.showInFilterTree.copy();
        this.fixed = other.fixed == null ? null : other.fixed.copy();
        this.sortable = other.sortable == null ? null : other.sortable.copy();
        this.treeIndicator = other.treeIndicator == null ? null : other.treeIndicator.copy();
        this.clientReadOnly = other.clientReadOnly == null ? null : other.clientReadOnly.copy();
        this.fieldValues = other.fieldValues == null ? null : other.fieldValues.copy();
        this.notNull = other.notNull == null ? null : other.notNull.copy();
        this.system = other.system == null ? null : other.system.copy();
        this.help = other.help == null ? null : other.help.copy();
        this.fontColor = other.fontColor == null ? null : other.fontColor.copy();
        this.backgroundColor = other.backgroundColor == null ? null : other.backgroundColor.copy();
        this.commonTableId = other.commonTableId == null ? null : other.commonTableId.copy();
    }

    @Override
    public CommonTableFieldCriteria copy() {
        return new CommonTableFieldCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getEntityFieldName() {
        return entityFieldName;
    }

    public void setEntityFieldName(StringFilter entityFieldName) {
        this.entityFieldName = entityFieldName;
    }

    public FieldTypeFilter getType() {
        return type;
    }

    public void setType(FieldTypeFilter type) {
        this.type = type;
    }

    public StringFilter getTableColumnName() {
        return tableColumnName;
    }

    public void setTableColumnName(StringFilter tableColumnName) {
        this.tableColumnName = tableColumnName;
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

    public BooleanFilter getEditInList() {
        return editInList;
    }

    public void setEditInList(BooleanFilter editInList) {
        this.editInList = editInList;
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

    public BooleanFilter getEnableFilter() {
        return enableFilter;
    }

    public void setEnableFilter(BooleanFilter enableFilter) {
        this.enableFilter = enableFilter;
    }

    public StringFilter getValidateRules() {
        return validateRules;
    }

    public void setValidateRules(StringFilter validateRules) {
        this.validateRules = validateRules;
    }

    public BooleanFilter getShowInFilterTree() {
        return showInFilterTree;
    }

    public void setShowInFilterTree(BooleanFilter showInFilterTree) {
        this.showInFilterTree = showInFilterTree;
    }

    public FixedTypeFilter getFixed() {
        return fixed;
    }

    public void setFixed(FixedTypeFilter fixed) {
        this.fixed = fixed;
    }

    public BooleanFilter getSortable() {
        return sortable;
    }

    public void setSortable(BooleanFilter sortable) {
        this.sortable = sortable;
    }

    public BooleanFilter getTreeIndicator() {
        return treeIndicator;
    }

    public void setTreeIndicator(BooleanFilter treeIndicator) {
        this.treeIndicator = treeIndicator;
    }

    public BooleanFilter getClientReadOnly() {
        return clientReadOnly;
    }

    public void setClientReadOnly(BooleanFilter clientReadOnly) {
        this.clientReadOnly = clientReadOnly;
    }

    public StringFilter getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(StringFilter fieldValues) {
        this.fieldValues = fieldValues;
    }

    public BooleanFilter getNotNull() {
        return notNull;
    }

    public void setNotNull(BooleanFilter notNull) {
        this.notNull = notNull;
    }

    public BooleanFilter getSystem() {
        return system;
    }

    public void setSystem(BooleanFilter system) {
        this.system = system;
    }

    public StringFilter getHelp() {
        return help;
    }

    public void setHelp(StringFilter help) {
        this.help = help;
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
        final CommonTableFieldCriteria that = (CommonTableFieldCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(entityFieldName, that.entityFieldName) &&
            Objects.equals(type, that.type) &&
            Objects.equals(tableColumnName, that.tableColumnName) &&
            Objects.equals(columnWidth, that.columnWidth) &&
            Objects.equals(order, that.order) &&
            Objects.equals(editInList, that.editInList) &&
            Objects.equals(hideInList, that.hideInList) &&
            Objects.equals(hideInForm, that.hideInForm) &&
            Objects.equals(enableFilter, that.enableFilter) &&
            Objects.equals(validateRules, that.validateRules) &&
            Objects.equals(showInFilterTree, that.showInFilterTree) &&
            Objects.equals(fixed, that.fixed) &&
            Objects.equals(sortable, that.sortable) &&
            Objects.equals(treeIndicator, that.treeIndicator) &&
            Objects.equals(clientReadOnly, that.clientReadOnly) &&
            Objects.equals(fieldValues, that.fieldValues) &&
            Objects.equals(notNull, that.notNull) &&
            Objects.equals(system, that.system) &&
            Objects.equals(help, that.help) &&
            Objects.equals(fontColor, that.fontColor) &&
            Objects.equals(backgroundColor, that.backgroundColor) &&
            Objects.equals(commonTableId, that.commonTableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        entityFieldName,
        type,
        tableColumnName,
        columnWidth,
        order,
        editInList,
        hideInList,
        hideInForm,
        enableFilter,
        validateRules,
        showInFilterTree,
        fixed,
        sortable,
        treeIndicator,
        clientReadOnly,
        fieldValues,
        notNull,
        system,
        help,
        fontColor,
        backgroundColor,
        commonTableId
        );
    }

    @Override
    public String toString() {
        return "CommonTableFieldCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (entityFieldName != null ? "entityFieldName=" + entityFieldName + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (tableColumnName != null ? "tableColumnName=" + tableColumnName + ", " : "") +
                (columnWidth != null ? "columnWidth=" + columnWidth + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (editInList != null ? "editInList=" + editInList + ", " : "") +
                (hideInList != null ? "hideInList=" + hideInList + ", " : "") +
                (hideInForm != null ? "hideInForm=" + hideInForm + ", " : "") +
                (enableFilter != null ? "enableFilter=" + enableFilter + ", " : "") +
                (validateRules != null ? "validateRules=" + validateRules + ", " : "") +
                (showInFilterTree != null ? "showInFilterTree=" + showInFilterTree + ", " : "") +
                (fixed != null ? "fixed=" + fixed + ", " : "") +
                (sortable != null ? "sortable=" + sortable + ", " : "") +
                (treeIndicator != null ? "treeIndicator=" + treeIndicator + ", " : "") +
                (clientReadOnly != null ? "clientReadOnly=" + clientReadOnly + ", " : "") +
                (fieldValues != null ? "fieldValues=" + fieldValues + ", " : "") +
                (notNull != null ? "notNull=" + notNull + ", " : "") +
                (system != null ? "system=" + system + ", " : "") +
                (help != null ? "help=" + help + ", " : "") +
                (fontColor != null ? "fontColor=" + fontColor + ", " : "") +
                (backgroundColor != null ? "backgroundColor=" + backgroundColor + ", " : "") +
                (commonTableId != null ? "commonTableId=" + commonTableId + ", " : "") +
            "}";
    }

}
