package com.aidriveall.cms.service.standalone.mobile.service.impl;

import com.aidriveall.cms.service.standalone.mobile.service.BaseConfig;

public class DaYuConfig extends BaseConfig {
    private String regionId;
    private String accessKeyId;
    private String secret;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
