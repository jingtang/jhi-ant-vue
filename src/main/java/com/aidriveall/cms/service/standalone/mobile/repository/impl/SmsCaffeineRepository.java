package com.aidriveall.cms.service.standalone.mobile.repository.impl;


import com.aidriveall.cms.service.standalone.mobile.SmsModel;
import com.aidriveall.cms.service.standalone.mobile.repository.BaseCaffeineRepository;
import com.aidriveall.cms.service.standalone.mobile.repository.ISmsRepository;

/**
 * 短信caffeine仓储
 * Created by liamjung on 2018/1/23.
 */
public class SmsCaffeineRepository extends BaseCaffeineRepository implements ISmsRepository {

    public SmsCaffeineRepository(int smsModelExpiration) {

        super(smsModelExpiration);
    }

    @Override
    public void save(SmsModel model) {

        CACHE.put(model.id(), model);
    }

    @Override
    public void update(SmsModel model) {

        this.save(model);
    }

    @Override
    public SmsModel get(String phoneNo, String flag) {

        return CACHE.getIfPresent(SmsModel.id(phoneNo, flag));
    }

    @Override
    public void delete(String phoneNo, String flag) {

        //异步操作
        CACHE.invalidate(SmsModel.id(phoneNo, flag));

        //同步缓存
//        CACHE.cleanUp();
    }
}
