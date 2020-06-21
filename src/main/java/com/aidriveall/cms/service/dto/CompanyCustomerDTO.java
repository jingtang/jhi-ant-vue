package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Objects;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.CompanyCustomer} entity.
 */
@ApiModel(description = "客户单位\n安装设备的客户公司")
public class CompanyCustomerDTO implements Serializable {

    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 代码
     */
    @ApiModelProperty(value = "代码")
    private String code;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phoneNum;

    /**
     * logo地址
     */
    @ApiModelProperty(value = "logo地址")
    private String logo;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contact;

    /**
     * 创建用户 Id
     */
    @ApiModelProperty(value = "创建用户 Id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private ZonedDateTime createTime;


    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private LinkedHashSet<CompanyCustomerDTO> children = new LinkedHashSet<>();
    /**
     * 上级
     */
    @ApiModelProperty(value = "上级")
    private Long parentId;
    private String parentName;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public LinkedHashSet<CompanyCustomerDTO> getChildren() {
        return this.children;
    }

    public void setChildren(LinkedHashSet<CompanyCustomerDTO> children) {
        this.children = children;
    }



    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long companyCustomerId) {
        this.parentId = companyCustomerId;
    }
    
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String companyCustomerName) {
        this.parentName = companyCustomerName;
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

        CompanyCustomerDTO companyCustomerDTO = (CompanyCustomerDTO) o;
        if (companyCustomerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyCustomerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyCustomerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", address='" + getAddress() + "'" +
            ", phoneNum='" + getPhoneNum() + "'" +
            ", logo='" + getLogo() + "'" +
            ", contact='" + getContact() + "'" +
            ", createUserId=" + getCreateUserId() +
            ", createTime='" + getCreateTime() + "'" +
            ", parent=" + getParentId() +
            ", parentName='" + getParentName() + "'" +
            "}";
    }
}
