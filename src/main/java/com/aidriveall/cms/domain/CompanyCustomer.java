package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 客户单位
 * 安装设备的客户公司
 */

@Entity
@Table(name = "company_customer")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 代码
     */
    @Column(name = "code")
    private String code;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 联系电话
     */
    @Column(name = "phone_num")
    private String phoneNum;

    /**
     * logo地址
     */
    @Column(name = "logo")
    private String logo;

    /**
     * 联系人
     */
    @Column(name = "contact")
    private String contact;

    /**
     * 创建用户 Id
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private ZonedDateTime createTime;

    /**
     * 子节点
     */
    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyCustomer> children = new LinkedHashSet<>();

    /**
     * 员工
     */
    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyUser> companyUsers = new LinkedHashSet<>();

    /**
     * 开通业务
     */
    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyBusiness> companyBusinesses = new LinkedHashSet<>();

    /**
     * 上级
     */
    @ManyToOne
    @JsonIgnoreProperties("children")
    private CompanyCustomer parent;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CompanyCustomer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public CompanyCustomer code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public CompanyCustomer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public CompanyCustomer phoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLogo() {
        return logo;
    }

    public CompanyCustomer logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContact() {
        return contact;
    }

    public CompanyCustomer contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public CompanyCustomer createUserId(Long createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public CompanyCustomer createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Set<CompanyCustomer> getChildren() {
        return children;
    }

    public CompanyCustomer children(Set<CompanyCustomer> companyCustomers) {
        this.children = companyCustomers;
        return this;
    }

    public CompanyCustomer addChildren(CompanyCustomer companyCustomer) {
        this.children.add(companyCustomer);
        companyCustomer.setParent(this);
        return this;
    }

    public CompanyCustomer removeChildren(CompanyCustomer companyCustomer) {
        this.children.remove(companyCustomer);
        companyCustomer.setParent(null);
        return this;
    }

    public void setChildren(Set<CompanyCustomer> companyCustomers) {
        this.children = companyCustomers;
    }

    public Set<CompanyUser> getCompanyUsers() {
        return companyUsers;
    }

    public CompanyCustomer companyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
        return this;
    }

    public CompanyCustomer addCompanyUsers(CompanyUser companyUser) {
        this.companyUsers.add(companyUser);
        companyUser.setCompany(this);
        return this;
    }

    public CompanyCustomer removeCompanyUsers(CompanyUser companyUser) {
        this.companyUsers.remove(companyUser);
        companyUser.setCompany(null);
        return this;
    }

    public void setCompanyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
    }

    public Set<CompanyBusiness> getCompanyBusinesses() {
        return companyBusinesses;
    }

    public CompanyCustomer companyBusinesses(Set<CompanyBusiness> companyBusinesses) {
        this.companyBusinesses = companyBusinesses;
        return this;
    }

    public CompanyCustomer addCompanyBusinesses(CompanyBusiness companyBusiness) {
        this.companyBusinesses.add(companyBusiness);
        companyBusiness.setCompany(this);
        return this;
    }

    public CompanyCustomer removeCompanyBusinesses(CompanyBusiness companyBusiness) {
        this.companyBusinesses.remove(companyBusiness);
        companyBusiness.setCompany(null);
        return this;
    }

    public void setCompanyBusinesses(Set<CompanyBusiness> companyBusinesses) {
        this.companyBusinesses = companyBusinesses;
    }

    public CompanyCustomer getParent() {
        return parent;
    }

    public CompanyCustomer parent(CompanyCustomer companyCustomer) {
        this.parent = companyCustomer;
        return this;
    }

    public void setParent(CompanyCustomer companyCustomer) {
        this.parent = companyCustomer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyCustomer)) {
            return false;
        }
        return id != null && id.equals(((CompanyCustomer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompanyCustomer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", address='" + getAddress() + "'" +
            ", phoneNum='" + getPhoneNum() + "'" +
            ", logo='" + getLogo() + "'" +
            ", contact='" + getContact() + "'" +
            ", createUserId=" + getCreateUserId() +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
