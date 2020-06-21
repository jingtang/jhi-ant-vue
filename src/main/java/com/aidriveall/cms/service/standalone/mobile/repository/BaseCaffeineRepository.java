package com.aidriveall.cms.service.standalone.mobile.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.aidriveall.cms.service.standalone.mobile.SmsModel;

import java.util.concurrent.TimeUnit;

/**
 * caffeine仓储抽象类
 * Created by liamjung on 2018/1/23.
 */
public abstract class BaseCaffeineRepository {

    protected final Cache<String, SmsModel> CACHE;

    protected BaseCaffeineRepository(int smsModelExpiration) {

        CACHE = Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(smsModelExpiration, TimeUnit.SECONDS)
//                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .build();
    }
}
