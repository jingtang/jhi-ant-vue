package com.aidriveall.cms.service.standalone.mobile.repository;


import com.aidriveall.cms.service.standalone.mobile.SmsModel;

/**
 * 短信仓储
 * Created by liamjung on 2018/1/19.
 */
public interface ISmsRepository {

    /**
     * 新增
     *
     * @param model
     */
    void save(SmsModel model);

    /**
     * 修改
     *
     * @param model
     */
    void update(SmsModel model);

    /**
     * 获取
     *
     * @param phoneNo 手机号码
     * @param flag    业务标志
     * @return
     */
    SmsModel get(String phoneNo, String flag);

    /**
     * 删除
     *
     * @param phoneNo 手机号码
     * @param flag    业务标志
     */
    void delete(String phoneNo, String flag);
}
