package com.aidriveall.cms.config;

import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oauth", ignoreUnknownFields = false)
public class OAuthProperties {
    /**
     * QQ 配置
     */
    private AuthConfig qq;

    /**
     * github 配置
     */
    private AuthConfig github;

    /**
     * 微信 配置
     */
    private AuthConfig wechat;

    /**
     * Google 配置
     */
    private AuthConfig google;

    /**
     * Microsoft 配置
     */
    private AuthConfig microsoft;

    /**
     * Mi 配置
     */
    private AuthConfig mi;

    public AuthConfig getQq() {
        return qq;
    }

    public void setQq(AuthConfig qq) {
        this.qq = qq;
    }

    public AuthConfig getGithub() {
        return github;
    }

    public void setGithub(AuthConfig github) {
        this.github = github;
    }

    public AuthConfig getWechat() {
        return wechat;
    }

    public void setWechat(AuthConfig wechat) {
        this.wechat = wechat;
    }

    public AuthConfig getGoogle() {
        return google;
    }

    public void setGoogle(AuthConfig google) {
        this.google = google;
    }

    public AuthConfig getMicrosoft() {
        return microsoft;
    }

    public void setMicrosoft(AuthConfig microsoft) {
        this.microsoft = microsoft;
    }

    public AuthConfig getMi() {
        return mi;
    }

    public void setMi(AuthConfig mi) {
        this.mi = mi;
    }
}
