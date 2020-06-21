package com.aidriveall.cms.service.standalone.mobile.enumeration;

/**
 * 错误枚举
 * Created by liamjung on 2018/1/23.
 */
public enum ErrorEnum {

    //未知错误
    UNKNOWN("V_E_001", "未知错误"),

    //短信验证码相关
    SMS_CODE_NOT_EXISTED("V_E_010", "短信验证码不存在"),
    SMS_CODE_INCORRECT("V_E_011", "短信验证码有误"),
    SMS_CODE_EXPIRED("V_E_012", "短信验证码已过期"),
    SMS_CODE_EXCEED_SENDING_MAXIMUM("V_E_013", "禁止频繁发送短信验证码"),
    SMS_CODE_RESENDING_FAIL("V_E_014", "禁止频繁发送短信验证码"),
    SMS_CODE_EXCEED_VERIFYING_MAXIMUM("V_E_015", "短信验证码已过期"),
    MOBILE_EXISTED("V_E_016", "手机号码已经被注册"),
    MOBILE_USER_EXISTED("V_E_017","手机号码已经注册到当前用户"),
    //图片验证码相关
    IMAGE_CODE_INCORRECT("V_E_020", "图片验证码有误"),
    IMAGE_CODE_EXPIRED("V_E_021", "图片验证码已过期");

    /**
     * 错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String msg;

    ErrorEnum(String code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回错误码
     *
     * @return
     */
    public String code() {
        return code;
    }

    /**
     * 返回错误信息
     *
     * @return
     */
    public String msg() {
        return msg;
    }
}
