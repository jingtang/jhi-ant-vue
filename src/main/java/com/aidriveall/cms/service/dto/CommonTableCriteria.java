package com.aidriveall.cms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.aidriveall.cms.domain.CommonTable} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.CommonTableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /common-tables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommonTableCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter entityName;

    private StringFilter tableName;

    private BooleanFilter system;

    private StringFilter clazzName;

    private BooleanFilter generated;

    private ZonedDateTimeFilter creatAt;

    private ZonedDateTimeFilter generateAt;

    private ZonedDateTimeFilter generateClassAt;

    private StringFilter description;

    private BooleanFilter treeTable;

    private LongFilter baseTableId;

    private IntegerFilter recordActionWidth;

    private LongFilter commonTableFieldsId;

    private LongFilter relationshipsId;

    private LongFilter creatorId;

    private LongFilter businessTypeId;

    public CommonTableCriteria(){
    }

    public CommonTableCriteria(CommonTableCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.entityName = other.entityName == null ? null : other.entityName.copy();
        this.tableName = other.tableName == null ? null : other.tableName.copy();
        this.system = other.system == null ? null : other.system.copy();
        this.clazzName = other.clazzName == null ? null : other.clazzName.copy();
        this.generated = other.generated == null ? null : other.generated.copy();
        this.creatAt = other.creatAt == null ? null : other.creatAt.copy();
        this.generateAt = other.generateAt == null ? null : other.generateAt.copy();
        this.generateClassAt = other.generateClassAt == null ? null : other.generateClassAt.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.treeTable = other.treeTable == null ? null : other.treeTable.copy();
        this.baseTableId = other.baseTableId == null ? null : other.baseTableId.copy();
        this.recordActionWidth = other.recordActionWidth == null ? null : other.recordActionWidth.copy();
        this.commonTableFieldsId = other.commonTableFieldsId == null ? null : other.commonTableFieldsId.copy();
        this.relationshipsId = other.relationshipsId == null ? null : other.relationshipsId.copy();
        this.creatorId = other.creatorId == null ? null : other.creatorId.copy();
        this.businessTypeId = other.businessTypeId == null ? null : other.businessTypeId.copy();
    }

    @Override
    public CommonTableCriteria copy() {
        return new CommonTableCriteria(this);
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

    public StringFilter getEntityName() {
        return entityName;
    }

    public void setEntityName(StringFilter entityName) {
        this.entityName = entityName;
    }

    public StringFilter getTableName() {
        return tableName;
    }

    public void setTableName(StringFilter tableName) {
        this.tableName = tableName;
    }

    public BooleanFilter getSystem() {
        return system;
    }

    public void setSystem(BooleanFilter system) {
        this.system = system;
    }

    public StringFilter getClazzName() {
        return clazzName;
    }

    public void setClazzName(StringFilter clazzName) {
        this.clazzName = clazzName;
    }

    public BooleanFilter getGenerated() {
        return generated;
    }

    public void setGenerated(BooleanFilter generated) {
        this.generated = generated;
    }

    public ZonedDateTimeFilter getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(ZonedDateTimeFilter creatAt) {
        this.creatAt = creatAt;
    }

    public ZonedDateTimeFilter getGenerateAt() {
        return generateAt;
    }

    public void setGenerateAt(ZonedDateTimeFilter generateAt) {
        this.generateAt = generateAt;
    }

    public ZonedDateTimeFilter getGenerateClassAt() {
        return generateClassAt;
    }

    public void setGenerateClassAt(ZonedDateTimeFilter generateClassAt) {
        this.generateClassAt = generateClassAt;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getTreeTable() {
        return treeTable;
    }

    public void setTreeTable(BooleanFilter treeTable) {
        this.treeTable = treeTable;
    }

    public LongFilter getBaseTableId() {
        return baseTableId;
    }

    public void setBaseTableId(LongFilter baseTableId) {
        this.baseTableId = baseTableId;
    }

    public IntegerFilter getRecordActionWidth() {
        return recordActionWidth;
    }

    public void setRecordActionWidth(IntegerFilter recordActionWidth) {
        this.recordActionWidth = recordActionWidth;
    }

    public LongFilter getCommonTableFieldsId() {
        return commonTableFieldsId;
    }

    public void setCommonTableFieldsId(LongFilter commonTableFieldsId) {
        this.commonTableFieldsId = commonTableFieldsId;
    }

    public LongFilter getRelationshipsId() {
        return relationshipsId;
    }

    public void setRelationshipsId(LongFilter relationshipsId) {
        this.relationshipsId = relationshipsId;
    }

    public LongFilter getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(LongFilter creatorId) {
        this.creatorId = creatorId;
    }

    public LongFilter getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(LongFilter businessTypeId) {
        this.businessTypeId = businessTypeId;
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
        final CommonTableCriteria that = (CommonTableCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(entityName, that.entityName) &&
            Objects.equals(tableName, that.tableName) &&
            Objects.equals(system, that.system) &&
            Objects.equals(clazzName, that.clazzName) &&
            Objects.equals(generated, that.generated) &&
            Objects.equals(creatAt, that.creatAt) &&
            Objects.equals(generateAt, that.generateAt) &&
            Objects.equals(generateClassAt, that.generateClassAt) &&
            Objects.equals(description, that.description) &&
            Objects.equals(treeTable, that.treeTable) &&
            Objects.equals(baseTableId, that.baseTableId) &&
            Objects.equals(recordActionWidth, that.recordActionWidth) &&
            Objects.equals(commonTableFieldsId, that.commonTableFieldsId) &&
            Objects.equals(relationshipsId, that.relationshipsId) &&
            Objects.equals(creatorId, that.creatorId) &&
            Objects.equals(businessTypeId, that.businessTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        entityName,
        tableName,
        system,
        clazzName,
        generated,
        creatAt,
        generateAt,
        generateClassAt,
        description,
        treeTable,
        baseTableId,
        recordActionWidth,
        commonTableFieldsId,
        relationshipsId,
        creatorId,
        businessTypeId
        );
    }

    @Override
    public String toString() {
        return "CommonTableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (entityName != null ? "entityName=" + entityName + ", " : "") +
                (tableName != null ? "tableName=" + tableName + ", " : "") +
                (system != null ? "system=" + system + ", " : "") +
                (clazzName != null ? "clazzName=" + clazzName + ", " : "") +
                (generated != null ? "generated=" + generated + ", " : "") +
                (creatAt != null ? "creatAt=" + creatAt + ", " : "") +
                (generateAt != null ? "generateAt=" + generateAt + ", " : "") +
                (generateClassAt != null ? "generateClassAt=" + generateClassAt + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (treeTable != null ? "treeTable=" + treeTable + ", " : "") +
                (baseTableId != null ? "baseTableId=" + baseTableId + ", " : "") +
                (recordActionWidth != null ? "recordActionWidth=" + recordActionWidth + ", " : "") +
                (commonTableFieldsId != null ? "commonTableFieldsId=" + commonTableFieldsId + ", " : "") +
                (relationshipsId != null ? "relationshipsId=" + relationshipsId + ", " : "") +
                (creatorId != null ? "creatorId=" + creatorId + ", " : "") +
                (businessTypeId != null ? "businessTypeId=" + businessTypeId + ", " : "") +
            "}";
    }

}
