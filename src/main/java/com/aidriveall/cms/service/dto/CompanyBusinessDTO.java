package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import com.aidriveall.cms.domain.enumeration.CompanyBusinessStatus;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.CompanyBusiness} entity.
 */
@ApiModel(description = "客户业务\n客户公司开通业务")
public class CompanyBusinessDTO implements Serializable {

    private Long id;

    /**
     * 开通状态
     */
    @ApiModelProperty(value = "开通状态")
    private CompanyBusinessStatus status;

    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间")
    private ZonedDateTime expirationTime;

    /**
     * 开通时间或试用开始时间
     */
    @ApiModelProperty(value = "开通时间或试用开始时间")
    private ZonedDateTime startTime;

    /**
     * 操作用户Id
     */
    @ApiModelProperty(value = "操作用户Id")
    private Long operateUserId;

    /**
     * 百度人员组id
     */
    @ApiModelProperty(value = "百度人员组id")
    private String groupId;


    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型")
    private Long businessTypeId;
    private String businessTypeName;
    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;
    private String companyName;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyBusinessStatus getStatus() {
        return status;
    }

    public void setStatus(CompanyBusinessStatus status) {
        this.status = status;
    }

    public ZonedDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(ZonedDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

// jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyBusinessDTO companyBusinessDTO = (CompanyBusinessDTO) o;
        if (companyBusinessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyBusinessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyBusinessDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", expirationTime='" + getExpirationTime() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", operateUserId=" + getOperateUserId() +
            ", groupId='" + getGroupId() + "'" +
            ", businessType=" + getBusinessTypeId() +
            ", businessTypeName='" + getBusinessTypeName() + "'" +
            ", company=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
