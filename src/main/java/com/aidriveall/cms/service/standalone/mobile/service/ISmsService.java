package com.aidriveall.cms.service.standalone.mobile.service;

import java.util.Map;

/**
 * 短信服务接口
 * Created by liamjung on 2018/1/19.
 */
public interface ISmsService {

    /**
     * 发送验证码
     *
     * @param phoneNo 手机号码
     * @param flag    业务标志
     * @return 返回值中的data为null或验证码图片base64
     */
    Result<String> sendVerifyCode(String phoneNo, String flag);

    /**
     *发送短信通知
     *
     * by wangxinxx
     *
     *
     * @param smsParams 发送短信使用的参数
     * @param flag      业务标志
     * @return 返回值中的data为null或验证码图片base64
     */
    Result sendNotification(Map<String, Object> smsParams, String flag);

    /**
     * 发送通知
     * 直接将 text 中的内容发送到用户手机中。
     *
     * @param phoneNo 手机号码
     * @param text    通知
     */
    // void send(String phoneNo, String text);

    /**
     * 发送短信内容
     *
     * by wangxinxx
     *
     * @param smsParams 服务端确定的接口参数，采用 Map 形式。
     * @return
     */
    Result<Object> send(Map<String, Object> smsParams);

    /**
     * 验证验证码
     *
     * @param phoneNo   手机号码
     * @param smsCode   短信验证码
     * @param imageCode 图片验证码
     * @param flag      业务标志
     * @return
     */
    Result<Void> verify(String phoneNo, String smsCode, String imageCode, String flag);

    /**
     * 刷新图片验证码
     *
     * @param phoneNo 手机号码
     * @param flag    业务标志
     * @return 返回值中的data为null或验证码图片base64
     */
    Result<String> refreshImageCode(String phoneNo, String flag);

    /**
     * 发送验证码，
     * @param phoneNo 手机号码
     * @param code    验证码
     * @param smsParams 发送短信参数，需要将 phone 和 code 也提前加入。
     * @param flag 业务标志
     * @return
     */
    // Result<String> send(String phoneNo, String code, Map<String, Object> smsParams, String flag);

    /**
     * 产生多少位的随机数字组成的字符。
     * @param digitNum
     * @return
     */
    // String getRandomCode(int digitNum);

    /**
     * 根据手机号码获得验证码。
     * @param phoneNo
     * @return
     */
    // String getSmsCodeByPhone(String phoneNo, String flag);


}
