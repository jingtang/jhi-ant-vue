package com.aidriveall.cms.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.aidriveall.cms.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.aidriveall.cms.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.aidriveall.cms.domain.User.class.getName());
            createCache(cm, com.aidriveall.cms.domain.Authority.class.getName());
            createCache(cm, com.aidriveall.cms.domain.User.class.getName() + ".authorities");
            createCache(cm, com.aidriveall.cms.domain.UploadFile.class.getName());
            createCache(cm, com.aidriveall.cms.domain.UploadImage.class.getName());
            createCache(cm, com.aidriveall.cms.domain.DataDictionary.class.getName());
            createCache(cm, com.aidriveall.cms.domain.DataDictionary.class.getName() + ".children");
            createCache(cm, com.aidriveall.cms.domain.GpsInfo.class.getName());
            createCache(cm, com.aidriveall.cms.domain.AdministrativeDivision.class.getName());
            createCache(cm, com.aidriveall.cms.domain.AdministrativeDivision.class.getName() + ".children");
            createCache(cm, com.aidriveall.cms.domain.Authority.class.getName() + ".children");
            createCache(cm, com.aidriveall.cms.domain.Authority.class.getName() + ".users");
            createCache(cm, com.aidriveall.cms.domain.Authority.class.getName() + ".viewPermissions");
            createCache(cm, com.aidriveall.cms.domain.ViewPermission.class.getName());
            createCache(cm, com.aidriveall.cms.domain.ViewPermission.class.getName() + ".children");
            createCache(cm, com.aidriveall.cms.domain.ViewPermission.class.getName() + ".apiPermissions");
            createCache(cm, com.aidriveall.cms.domain.ViewPermission.class.getName() + ".authorities");
            createCache(cm, com.aidriveall.cms.domain.ApiPermission.class.getName());
            createCache(cm, com.aidriveall.cms.domain.ApiPermission.class.getName() + ".children");
            createCache(cm, com.aidriveall.cms.domain.ApiPermission.class.getName() + ".viewPermissions");
            createCache(cm, com.aidriveall.cms.domain.CommonTable.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonTable.class.getName() + ".commonTableFields");
            createCache(cm, com.aidriveall.cms.domain.CommonTable.class.getName() + ".relationships");
            createCache(cm, com.aidriveall.cms.domain.CommonTableField.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonTableRelationship.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonExtData.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonInteger.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonLong.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonBoolean.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonString.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonZonedDateTime.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonFloat.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonDouble.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonTextBlob.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonLocalDate.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CommonBigDecimal.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CompanyCustomer.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CompanyCustomer.class.getName() + ".children");
            createCache(cm, com.aidriveall.cms.domain.CompanyCustomer.class.getName() + ".companyUsers");
            createCache(cm, com.aidriveall.cms.domain.CompanyCustomer.class.getName() + ".companyBusinesses");
            createCache(cm, com.aidriveall.cms.domain.CompanyUser.class.getName());
            createCache(cm, com.aidriveall.cms.domain.CompanyBusiness.class.getName());
            createCache(cm, com.aidriveall.cms.domain.BusinessType.class.getName());
            createCache(cm, com.aidriveall.cms.domain.UReportFile.class.getName());
            createCache(cm, com.aidriveall.cms.domain.ProcessTableConfig.class.getName());
            createCache(cm, com.aidriveall.cms.domain.ProcessFormConfig.class.getName());
            createCache(cm, com.aidriveall.cms.domain.ProcessEntityRelation.class.getName());
            createCache(cm, com.aidriveall.cms.domain.Leave.class.getName());
            createCache(cm, com.aidriveall.cms.domain.Leave.class.getName() + ".images");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
