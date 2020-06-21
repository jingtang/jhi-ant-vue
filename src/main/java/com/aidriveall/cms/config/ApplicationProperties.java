package com.aidriveall.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Jhi Ant Vue.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final Upload upload = new Upload();
    private final SmsConfig smsConfig = new SmsConfig();
    private Pay pay = new Pay();

    public Upload getUpload() {
        return upload;
    }

    public SmsConfig getSmsConfig() {
        return smsConfig;
    }

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    public static class Upload {
        private String rootPath;
        private String domainUrl;

        public String getRootPath() {
            return rootPath;
        }

        public void setRootPath(String rootPath) {
            this.rootPath = rootPath;
        }

        public String getDomainUrl() {
            return domainUrl;
        }

        public void setDomainUrl(String domainUrl) {
            this.domainUrl = domainUrl;
        }
    }

    public static class SmsConfig {
        private DaYuConfig daYuConfig;

        public DaYuConfig getDaYuConfig() {
            return daYuConfig;
        }

        public void setDaYuConfig(DaYuConfig daYuConfig) {
            this.daYuConfig = daYuConfig;
        }

        public static class DaYuConfig {
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
    }
    public static class Pay {
        private WxPay wxPay;

        public WxPay getWxPay() {
            return wxPay;
        }

        public void setWxPay(WxPay wxPay) {
            this.wxPay = wxPay;
        }

        public static class WxPay {
            /**
             * 设置微信公众号或者小程序等的appid
             */
            private String appId;

            /**
             * 微信支付商户号
             */
            private String mchId;

            /**
             * 微信支付商户密钥
             */
            private String mchKey;

            /**
             * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
             */
            private String subAppId;

            /**
             * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
             */
            private String subMchId;

            /**
             * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
             */
            private String keyPath;

            public String getAppId() {
                return this.appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getMchId() {
                return mchId;
            }

            public void setMchId(String mchId) {
                this.mchId = mchId;
            }

            public String getMchKey() {
                return mchKey;
            }

            public void setMchKey(String mchKey) {
                this.mchKey = mchKey;
            }

            public String getSubAppId() {
                return subAppId;
            }

            public void setSubAppId(String subAppId) {
                this.subAppId = subAppId;
            }

            public String getSubMchId() {
                return subMchId;
            }

            public void setSubMchId(String subMchId) {
                this.subMchId = subMchId;
            }

            public String getKeyPath() {
                return this.keyPath;
            }

            public void setKeyPath(String keyPath) {
                this.keyPath = keyPath;
            }

        }
    }
}
