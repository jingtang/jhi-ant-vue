package com.aidriveall.cms.config;

import com.aidriveall.cms.service.standalone.mobile.service.impl.DaYuConfig;
import io.github.jhipster.async.ExceptionHandlingAsyncTaskExecutor;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class SmsConfiguration {

    private final Logger log = LoggerFactory.getLogger(SmsConfiguration.class);

    private final ApplicationProperties applicationProperties;

    public SmsConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public DaYuConfig config(){
        DaYuConfig config = new DaYuConfig();
        config.setRegionId(applicationProperties.getSmsConfig().getDaYuConfig().getRegionId());
        config.setAccessKeyId(applicationProperties.getSmsConfig().getDaYuConfig().getAccessKeyId());
        config.setSecret(applicationProperties.getSmsConfig().getDaYuConfig().getSecret());
        return config;
    }
}
