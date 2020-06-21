package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.CompanyUser} entity.
 */
@ApiModel(description = "人员账号\n客户方人员账号")
public class CompanyUserDTO implements Serializable {

    private Long id;


    /**
     * 关联账户
     */
    @ApiModelProperty(value = "关联账户")
    private Long userId;
    private String userImageUrl;
    private String userFirstName;
    /**
     * 所属单位
     */
    @ApiModelProperty(value = "所属单位")
    private Long companyId;
    private String companyName;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }
    
    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
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

        CompanyUserDTO companyUserDTO = (CompanyUserDTO) o;
        if (companyUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyUserDTO{" +
            "id=" + getId() +
            ", user=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            ", company=" + getCompanyId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
