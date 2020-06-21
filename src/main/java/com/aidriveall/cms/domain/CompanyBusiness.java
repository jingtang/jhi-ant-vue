package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.aidriveall.cms.domain.enumeration.CompanyBusinessStatus;

/**
 * 客户业务
 * 客户公司开通业务
 */

@Entity
@Table(name = "company_business")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 开通状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CompanyBusinessStatus status;

    /**
     * 到期时间
     */
    @Column(name = "expiration_time")
    private ZonedDateTime expirationTime;

    /**
     * 开通时间或试用开始时间
     */
    @Column(name = "start_time")
    private ZonedDateTime startTime;

    /**
     * 操作用户Id
     */
    @Column(name = "operate_user_id")
    private Long operateUserId;

    /**
     * 百度人员组id
     */
    @Column(name = "group_id")
    private String groupId;

    /**
     * 业务类型
     */
    @ManyToOne
    @JsonIgnoreProperties("companyBusinesses")
    private BusinessType businessType;
    /**
     * 公司
     */
    @ManyToOne
    @JsonIgnoreProperties("companyBusinesses")
    private CompanyCustomer company;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyBusinessStatus getStatus() {
        return status;
    }

    public CompanyBusiness status(CompanyBusinessStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CompanyBusinessStatus status) {
        this.status = status;
    }

    public ZonedDateTime getExpirationTime() {
        return expirationTime;
    }

    public CompanyBusiness expirationTime(ZonedDateTime expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    public void setExpirationTime(ZonedDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public CompanyBusiness startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getOperateUserId() {
        return operateUserId;
    }

    public CompanyBusiness operateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
        return this;
    }

    public void setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getGroupId() {
        return groupId;
    }

    public CompanyBusiness groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public CompanyBusiness businessType(BusinessType businessType) {
        this.businessType = businessType;
        return this;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public CompanyCustomer getCompany() {
        return company;
    }

    public CompanyBusiness company(CompanyCustomer companyCustomer) {
        this.company = companyCustomer;
        return this;
    }

    public void setCompany(CompanyCustomer companyCustomer) {
        this.company = companyCustomer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyBusiness)) {
            return false;
        }
        return id != null && id.equals(((CompanyBusiness) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompanyBusiness{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", expirationTime='" + getExpirationTime() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", operateUserId=" + getOperateUserId() +
            ", groupId='" + getGroupId() + "'" +
            "}";
    }
}
