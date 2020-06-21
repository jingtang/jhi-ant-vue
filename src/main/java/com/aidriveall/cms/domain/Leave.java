package com.aidriveall.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
 * 请假
 */

@Entity
@Table(name = "jhi_leave")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Leave implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private ZonedDateTime createTime;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 天数
     */
    @Column(name = "days")
    private Integer days;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private ZonedDateTime startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private ZonedDateTime endTime;

    /**
     * 原因
     */
    @Column(name = "reason")
    private String reason;

    /**
     * 附件列表
     */
    @OneToMany()
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(
        name ="jhi_leave_images",
        joinColumns = @JoinColumn(name ="leave_id"),
        inverseJoinColumns = @JoinColumn(name ="images_id", referencedColumnName = "id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<UploadImage> images = new LinkedHashSet<>();

    /**
     * 申请人
     */
    @ManyToOne
    @JsonIgnoreProperties("leaves")
    private User creator;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Leave createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public Leave name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDays() {
        return days;
    }

    public Leave days(Integer days) {
        this.days = days;
        return this;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public Leave startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public Leave endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public Leave reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Set<UploadImage> getImages() {
        return images;
    }

    public Leave images(Set<UploadImage> uploadImages) {
        this.images = uploadImages;
        return this;
    }

    public Leave addImages(UploadImage uploadImage) {
        this.images.add(uploadImage);
        return this;
    }

    public Leave removeImages(UploadImage uploadImage) {
        this.images.remove(uploadImage);
        return this;
    }

    public void setImages(Set<UploadImage> uploadImages) {
        this.images = uploadImages;
    }

    public User getCreator() {
        return creator;
    }

    public Leave creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leave)) {
            return false;
        }
        return id != null && id.equals(((Leave) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Leave{" +
            "id=" + getId() +
            ", createTime='" + getCreateTime() + "'" +
            ", name='" + getName() + "'" +
            ", days=" + getDays() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
