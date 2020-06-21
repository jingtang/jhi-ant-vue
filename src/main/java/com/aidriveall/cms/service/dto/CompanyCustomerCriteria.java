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
 * Criteria class for the {@link com.aidriveall.cms.domain.CompanyCustomer} entity. This class is used
 * in {@link com.aidriveall.cms.web.rest.CompanyCustomerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /company-customers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyCustomerCriteria implements Serializable, Criteria {
    private String jhiCommonSearchKeywords;

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter address;

    private StringFilter phoneNum;

    private StringFilter logo;

    private StringFilter contact;

    private LongFilter createUserId;

    private ZonedDateTimeFilter createTime;

    private LongFilter childrenId;

    private LongFilter companyUsersId;

    private LongFilter companyBusinessesId;

    private LongFilter parentId;

    public CompanyCustomerCriteria(){
    }

    public CompanyCustomerCriteria(CompanyCustomerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.phoneNum = other.phoneNum == null ? null : other.phoneNum.copy();
        this.logo = other.logo == null ? null : other.logo.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.createUserId = other.createUserId == null ? null : other.createUserId.copy();
        this.createTime = other.createTime == null ? null : other.createTime.copy();
        this.childrenId = other.childrenId == null ? null : other.childrenId.copy();
        this.companyUsersId = other.companyUsersId == null ? null : other.companyUsersId.copy();
        this.companyBusinessesId = other.companyBusinessesId == null ? null : other.companyBusinessesId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public CompanyCustomerCriteria copy() {
        return new CompanyCustomerCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(StringFilter phoneNum) {
        this.phoneNum = phoneNum;
    }

    public StringFilter getLogo() {
        return logo;
    }

    public void setLogo(StringFilter logo) {
        this.logo = logo;
    }

    public StringFilter getContact() {
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public LongFilter getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(LongFilter createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTimeFilter getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTimeFilter createTime) {
        this.createTime = createTime;
    }

    public LongFilter getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(LongFilter childrenId) {
        this.childrenId = childrenId;
    }

    public LongFilter getCompanyUsersId() {
        return companyUsersId;
    }

    public void setCompanyUsersId(LongFilter companyUsersId) {
        this.companyUsersId = companyUsersId;
    }

    public LongFilter getCompanyBusinessesId() {
        return companyBusinessesId;
    }

    public void setCompanyBusinessesId(LongFilter companyBusinessesId) {
        this.companyBusinessesId = companyBusinessesId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
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
        final CompanyCustomerCriteria that = (CompanyCustomerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(address, that.address) &&
            Objects.equals(phoneNum, that.phoneNum) &&
            Objects.equals(logo, that.logo) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(createUserId, that.createUserId) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(childrenId, that.childrenId) &&
            Objects.equals(companyUsersId, that.companyUsersId) &&
            Objects.equals(companyBusinessesId, that.companyBusinessesId) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        code,
        address,
        phoneNum,
        logo,
        contact,
        createUserId,
        createTime,
        childrenId,
        companyUsersId,
        companyBusinessesId,
        parentId
        );
    }

    @Override
    public String toString() {
        return "CompanyCustomerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (phoneNum != null ? "phoneNum=" + phoneNum + ", " : "") +
                (logo != null ? "logo=" + logo + ", " : "") +
                (contact != null ? "contact=" + contact + ", " : "") +
                (createUserId != null ? "createUserId=" + createUserId + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (childrenId != null ? "childrenId=" + childrenId + ", " : "") +
                (companyUsersId != null ? "companyUsersId=" + companyUsersId + ", " : "") +
                (companyBusinessesId != null ? "companyBusinessesId=" + companyBusinessesId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
