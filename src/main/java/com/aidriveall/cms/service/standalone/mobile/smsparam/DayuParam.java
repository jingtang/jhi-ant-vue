package com.aidriveall.cms.service.standalone.mobile.smsparam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class DayuParam {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 获得手机验证码的map参数
     * @param mobiles 支持多个手机号，使用,分开。
     * @param code
     */
    public static Map<String, Object> getVerifyCodeParam(String mobiles, String code) {
        Map<String, Object> paramMap = new HashMap<>();
        HashMap<String, Object> templateParamMap = new HashMap<>();
        paramMap.put("PhoneNumbers", mobiles);
        paramMap.put("SignName","精彩美课");
        paramMap.put("TemplateCode", "SMS_12951503");
        try {
            templateParamMap.put("code", code);
            paramMap.put("TemplateParam", mapper.writeValueAsString(templateParamMap));
        } catch (JsonProcessingException e) {
            throw new NullPointerException("TemplateParam json error.");
        }
        return paramMap;
    }

    public static void main(String[] args) {
        Map<String, Object> verifyCodeParam = getVerifyCodeParam("13700283", "200");
        System.out.println(verifyCodeParam);

    }
}
