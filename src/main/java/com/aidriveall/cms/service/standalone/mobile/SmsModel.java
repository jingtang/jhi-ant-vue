package com.aidriveall.cms.service.standalone.mobile;


import com.google.common.base.MoreObjects;

import java.util.Objects;

/**
 * 短信模型
 * Created by liamjung on 2018/1/19.
 */
public class SmsModel {

    /**
     * 手机号码
     */
    private String phoneNo;
    /**
     * 短信验证码
     */
    private String smsCode;
    /**
     * 短信验证码过期时间
     */
    private Long smsCodeExpiration;
    /**
     * 短信验证码重发时间
     */
    private Long smsCodeResendingTime;
    /**
     * 图片验证码
     */
    private String imageCode;
    /**
     * 图片验证码过期时间
     */
    private Long imageCodeExpiration;
    /**
     * 已发次数
     */
    private int sendingCount = 0;
    /**
     * 已验次数
     */
    private int verifyingCount = 0;
    /**
     * 业务标志
     */
    private String flag;

    public String id() {

        return this.flag + ":" + this.phoneNo;
    }

    public static String id(String phoneNo, String flag) {

        return flag + ":" + phoneNo;
    }

// The code below has been generated by BoB the Builder of Beans based on the class' fields.
// Everything after this comment will be regenerated if you invoke BoB again.
// If you don't know who BoB is, you can find him here: https://bitbucket.org/atlassianlabs/bob-the-builder-of-beans

    protected SmsModel(String phoneNo, String smsCode, Long smsCodeExpiration, Long smsCodeResendingTime, String imageCode, Long imageCodeExpiration, int sendingCount, int verifyingCount, String flag) {
        this.phoneNo = Objects.requireNonNull(phoneNo);
        this.smsCode = Objects.requireNonNull(smsCode);
        this.smsCodeExpiration = Objects.requireNonNull(smsCodeExpiration);
        this.smsCodeResendingTime = Objects.requireNonNull(smsCodeResendingTime);
        this.imageCode = imageCode;
        this.imageCodeExpiration = imageCodeExpiration;
        this.sendingCount = sendingCount;
        this.verifyingCount = verifyingCount;
        this.flag = Objects.requireNonNull(flag);
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = Objects.requireNonNull(phoneNo);
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = Objects.requireNonNull(smsCode);
    }

    public Long getSmsCodeExpiration() {
        return smsCodeExpiration;
    }

    public void setSmsCodeExpiration(Long smsCodeExpiration) {
        this.smsCodeExpiration = Objects.requireNonNull(smsCodeExpiration);
    }

    public Long getSmsCodeResendingTime() {
        return smsCodeResendingTime;
    }

    public void setSmsCodeResendingTime(Long smsCodeResendingTime) {
        this.smsCodeResendingTime = Objects.requireNonNull(smsCodeResendingTime);
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = Objects.requireNonNull(imageCode);
    }

    public Long getImageCodeExpiration() {
        return imageCodeExpiration;
    }

    public void setImageCodeExpiration(Long imageCodeExpiration) {
        this.imageCodeExpiration = Objects.requireNonNull(imageCodeExpiration);
    }

    public int getSendingCount() {
        return sendingCount;
    }

    public void setSendingCount(int sendingCount) {
        this.sendingCount = sendingCount;
    }

    public int getVerifyingCount() {
        return verifyingCount;
    }

    public void setVerifyingCount(int verifyingCount) {
        this.verifyingCount = verifyingCount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = Objects.requireNonNull(flag);
    }

    public static SmsModel.Builder builder() {
        return new SmsModel.Builder();
    }

    public static SmsModel.Builder builder(SmsModel data) {
        return new SmsModel.Builder(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SmsModel that = (SmsModel) o;

        return Objects.equals(this.getPhoneNo(), that.getPhoneNo()) && Objects.equals(this.getSmsCode(), that.getSmsCode()) && Objects.equals(this.getSmsCodeExpiration(), that.getSmsCodeExpiration()) && Objects.equals(this.getSmsCodeResendingTime(), that.getSmsCodeResendingTime()) && Objects.equals(this.getImageCode(), that.getImageCode()) && Objects.equals(this.getImageCodeExpiration(), that.getImageCodeExpiration()) && Objects.equals(this.getSendingCount(), that.getSendingCount()) && Objects.equals(this.getVerifyingCount(), that.getVerifyingCount()) && Objects.equals(this.getFlag(), that.getFlag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneNo(), getSmsCode(), getSmsCodeExpiration(), getSmsCodeResendingTime(), getImageCode(), getImageCodeExpiration(), getSendingCount(), getVerifyingCount(), getFlag());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("phoneNo", getPhoneNo())
            .add("smsCode", getSmsCode())
            .add("smsCodeExpiration", getSmsCodeExpiration())
            .add("smsCodeResendingTime", getSmsCodeResendingTime())
            .add("imageCode", getImageCode())
            .add("imageCodeExpiration", getImageCodeExpiration())
            .add("sendingCount", getSendingCount())
            .add("verifyingCount", getVerifyingCount())
            .add("flag", getFlag())
            .toString();
    }

    public static final class Builder {

        private String phoneNo;
        private String smsCode;
        private Long smsCodeExpiration;
        private Long smsCodeResendingTime;
        private String imageCode;
        private Long imageCodeExpiration;
        private int sendingCount;
        private int verifyingCount;
        private String flag;

        private Builder() {
        }

        private Builder(SmsModel initialData) {
            this.phoneNo = initialData.getPhoneNo();
            this.smsCode = initialData.getSmsCode();
            this.smsCodeExpiration = initialData.getSmsCodeExpiration();
            this.smsCodeResendingTime = initialData.getSmsCodeResendingTime();
            this.imageCode = initialData.getImageCode();
            this.imageCodeExpiration = initialData.getImageCodeExpiration();
            this.sendingCount = initialData.getSendingCount();
            this.verifyingCount = initialData.getVerifyingCount();
            this.flag = initialData.getFlag();
        }

        public Builder setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
            return this;
        }

        public Builder setSmsCode(String smsCode) {
            this.smsCode = smsCode;
            return this;
        }

        public Builder setSmsCodeExpiration(Long smsCodeExpiration) {
            this.smsCodeExpiration = smsCodeExpiration;
            return this;
        }

        public Builder setSmsCodeResendingTime(Long smsCodeResendingTime) {
            this.smsCodeResendingTime = smsCodeResendingTime;
            return this;
        }

        public Builder setImageCode(String imageCode) {
            this.imageCode = imageCode;
            return this;
        }

        public Builder setImageCodeExpiration(Long imageCodeExpiration) {
            this.imageCodeExpiration = imageCodeExpiration;
            return this;
        }

        public Builder setSendingCount(int sendingCount) {
            this.sendingCount = sendingCount;
            return this;
        }

        public Builder setVerifyingCount(int verifyingCount) {
            this.verifyingCount = verifyingCount;
            return this;
        }

        public Builder setFlag(String flag) {
            this.flag = flag;
            return this;
        }

        public SmsModel build() {
            return new SmsModel(phoneNo, smsCode, smsCodeExpiration, smsCodeResendingTime, imageCode, imageCodeExpiration, sendingCount, verifyingCount, flag);
        }
    }
}
