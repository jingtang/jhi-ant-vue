package com.aidriveall.cms.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove


/**
 * A DTO for the {@link com.aidriveall.cms.domain.Leave} entity.
 */
@ApiModel(description = "请假")
public class LeaveDTO implements Serializable {

    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private ZonedDateTime createTime;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 天数
     */
    @ApiModelProperty(value = "天数")
    private Integer days;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private ZonedDateTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private ZonedDateTime endTime;

    /**
     * 原因
     */
    @ApiModelProperty(value = "原因")
    private String reason;


    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请人")
    private Long creatorId;
    private String creatorImageUrl;
    private String creatorLogin;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long userId) {
        this.creatorId = userId;
    }
    public String getCreatorImageUrl() {
        return creatorImageUrl;
    }

    public void setCreatorImageUrl(String userImageUrl) {
        this.creatorImageUrl = userImageUrl;
    }
    
    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String userLogin) {
        this.creatorLogin = userLogin;
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

        LeaveDTO leaveDTO = (LeaveDTO) o;
        if (leaveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveDTO{" +
            "id=" + getId() +
            ", createTime='" + getCreateTime() + "'" +
            ", name='" + getName() + "'" +
            ", days=" + getDays() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", reason='" + getReason() + "'" +
            ", creator=" + getCreatorId() +
            ", creatorLogin='" + getCreatorLogin() + "'" +
            "}";
    }
}
