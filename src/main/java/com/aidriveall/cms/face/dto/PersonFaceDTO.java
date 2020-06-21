package com.aidriveall.cms.face.dto;

import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

public class PersonFaceDTO implements Serializable {

    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 外部ID
     */
    private String uniqueId;

    /**
     * 身份证号码
     */
    private String cardNo;

    /**
     * 人脸特征
     */
    @Lob
    private byte[] feature;

    private String featureContentType;

    /**
     * 原始照片
     */
    private Long originalImageId;
    private String originalImageUrl;
    /**
     * 部门
     */
    private Long companyId;
    private String companyName;
    /**
     * 标注照片
     */
    private Long signImageId;
    private String signImageUrl;


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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public byte[] getFeature() {
        return feature;
    }

    public void setFeature(byte[] feature) {
        this.feature = feature;
    }

    public String getFeatureContentType() {
        return featureContentType;
    }

    public void setFeatureContentType(String featureContentType) {
        this.featureContentType = featureContentType;
    }

    public Long getOriginalImageId() {
        return originalImageId;
    }

    public void setOriginalImageId(Long uploadImageId) {
        this.originalImageId = uploadImageId;
    }


    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(String uploadImageUrl) {
        this.originalImageUrl = uploadImageUrl;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyCustomerId) {
        this.companyId = companyCustomerId;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyCustomerName) {
        this.companyName = companyCustomerName;
    }

    public Long getSignImageId() {
        return signImageId;
    }

    public void setSignImageId(Long uploadImageId) {
        this.signImageId = uploadImageId;
    }


    public String getSignImageUrl() {
        return signImageUrl;
    }

    public void setSignImageUrl(String uploadImageUrl) {
        this.signImageUrl = uploadImageUrl;
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

        PersonFaceDTO personFaceDTO = (PersonFaceDTO) o;
        if (personFaceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personFaceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonFaceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", uniqueId='" + getUniqueId() + "'" +
            ", cardNo='" + getCardNo() + "'" +
            ", originalImage=" + getOriginalImageId() +
            ", originalImage='" + getOriginalImageUrl() + "'" +
            ", company=" + getCompanyId() +
            ", company='" + getCompanyName() + "'" +
            ", signImage=" + getSignImageId() +
            ", signImage='" + getSignImageUrl() + "'" +
            "}";
    }
}
