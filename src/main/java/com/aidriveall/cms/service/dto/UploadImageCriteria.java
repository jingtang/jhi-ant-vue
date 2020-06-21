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
 * Criteria class for the {@link com.aidriveall.cms.domain.UploadImage} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.UploadImageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /upload-images?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UploadImageCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter name;

    private StringFilter ext;

    private StringFilter type;

    private StringFilter url;

    private StringFilter path;

    private StringFilter folder;

    private StringFilter entityName;

    private ZonedDateTimeFilter createAt;

    private LongFilter fileSize;

    private StringFilter smartUrl;

    private StringFilter mediumUrl;

    private LongFilter referenceCount;

    private LongFilter userId;

    public UploadImageCriteria(){
    }

    public UploadImageCriteria(UploadImageCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.ext = other.ext == null ? null : other.ext.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.path = other.path == null ? null : other.path.copy();
        this.folder = other.folder == null ? null : other.folder.copy();
        this.entityName = other.entityName == null ? null : other.entityName.copy();
        this.createAt = other.createAt == null ? null : other.createAt.copy();
        this.fileSize = other.fileSize == null ? null : other.fileSize.copy();
        this.smartUrl = other.smartUrl == null ? null : other.smartUrl.copy();
        this.mediumUrl = other.mediumUrl == null ? null : other.mediumUrl.copy();
        this.referenceCount = other.referenceCount == null ? null : other.referenceCount.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public UploadImageCriteria copy() {
        return new UploadImageCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getExt() {
        return ext;
    }

    public void setExt(StringFilter ext) {
        this.ext = ext;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getPath() {
        return path;
    }

    public void setPath(StringFilter path) {
        this.path = path;
    }

    public StringFilter getFolder() {
        return folder;
    }

    public void setFolder(StringFilter folder) {
        this.folder = folder;
    }

    public StringFilter getEntityName() {
        return entityName;
    }

    public void setEntityName(StringFilter entityName) {
        this.entityName = entityName;
    }

    public ZonedDateTimeFilter getCreateAt() {
        return createAt;
    }

    public void setCreateAt(ZonedDateTimeFilter createAt) {
        this.createAt = createAt;
    }

    public LongFilter getFileSize() {
        return fileSize;
    }

    public void setFileSize(LongFilter fileSize) {
        this.fileSize = fileSize;
    }

    public StringFilter getSmartUrl() {
        return smartUrl;
    }

    public void setSmartUrl(StringFilter smartUrl) {
        this.smartUrl = smartUrl;
    }

    public StringFilter getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(StringFilter mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public LongFilter getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(LongFilter referenceCount) {
        this.referenceCount = referenceCount;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final UploadImageCriteria that = (UploadImageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(name, that.name) &&
            Objects.equals(ext, that.ext) &&
            Objects.equals(type, that.type) &&
            Objects.equals(url, that.url) &&
            Objects.equals(path, that.path) &&
            Objects.equals(folder, that.folder) &&
            Objects.equals(entityName, that.entityName) &&
            Objects.equals(createAt, that.createAt) &&
            Objects.equals(fileSize, that.fileSize) &&
            Objects.equals(smartUrl, that.smartUrl) &&
            Objects.equals(mediumUrl, that.mediumUrl) &&
            Objects.equals(referenceCount, that.referenceCount) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullName,
        name,
        ext,
        type,
        url,
        path,
        folder,
        entityName,
        createAt,
        fileSize,
        smartUrl,
        mediumUrl,
        referenceCount,
        userId
        );
    }

    @Override
    public String toString() {
        return "UploadImageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (ext != null ? "ext=" + ext + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (path != null ? "path=" + path + ", " : "") +
                (folder != null ? "folder=" + folder + ", " : "") +
                (entityName != null ? "entityName=" + entityName + ", " : "") +
                (createAt != null ? "createAt=" + createAt + ", " : "") +
                (fileSize != null ? "fileSize=" + fileSize + ", " : "") +
                (smartUrl != null ? "smartUrl=" + smartUrl + ", " : "") +
                (mediumUrl != null ? "mediumUrl=" + mediumUrl + ", " : "") +
                (referenceCount != null ? "referenceCount=" + referenceCount + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
