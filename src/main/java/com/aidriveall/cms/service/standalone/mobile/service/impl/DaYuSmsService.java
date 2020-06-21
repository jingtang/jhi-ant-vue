package com.aidriveall.cms.service.standalone.mobile.service.impl;

import com.aidriveall.cms.service.standalone.mobile.enumeration.ErrorEnum;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aidriveall.cms.service.standalone.mobile.service.BaseSmsService;
import com.aidriveall.cms.service.standalone.mobile.service.Result;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DaYuSmsService extends BaseSmsService<DaYuConfig> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaYuSmsService.class);
    private static ObjectMapper mapper = new ObjectMapper();
    private final IAcsClient client;
    // private final DaYuConfig config;
    public DaYuSmsService(DaYuConfig config) {
        super(config);
        // this.config = config;
        //初始化client,使用单例方式
        // DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "f04GAox0OGWGxyw4", "UCtwoQlfLxpNGbkjcm088GZlaEwMRR");
        DefaultProfile profile = DefaultProfile.getProfile(config.getRegionId(), config.getAccessKeyId(), config.getSecret());
        client = new DefaultAcsClient(profile);
    }

    /**
     * 发送短信通知
     *
     * @param smsParams 发送短信使用的参数
     * @param flag      业务标志
     * @return 返回值中的data为null或验证码图片base64
     */
    @Override
    public Result<String> sendNotification(Map<String, Object> smsParams, String flag) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", super.CONFIG.getRegionId());
        request.putQueryParameter("PhoneNumbers", "13704755857");
        request.putQueryParameter("SignName", "精彩美课");
        request.putQueryParameter("TemplateCode", (String) smsParams.get("TemplateCode"));
        request.putQueryParameter("TemplateParam", (String) smsParams.get("TemplateParam"));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    /**
     * 发送短信内容
     * <p>
     * by wangxinxx
     *
     * @param smsParams 服务端确定的接口参数，采用 Map 形式。
     * @return
     */
    @Override
    public Result<Object> send(Map<String, Object> smsParams) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", super.CONFIG.getRegionId());
        request.putQueryParameter("PhoneNumbers", (String) smsParams.get("PhoneNumbers"));
        request.putQueryParameter("SignName", "精彩美课");
        request.putQueryParameter("TemplateCode", (String) smsParams.get("TemplateCode"));
        request.putQueryParameter("TemplateParam", (String) smsParams.get("TemplateParam"));
        try {
            CommonResponse response = client.getCommonResponse(request);
            HashMap<String, Object> responseMap = mapper.readValue(response.getData(), new TypeReference<HashMap<String, Object>>() {
            });
            LOGGER.debug(response.getData());
            if (responseMap.get("Code").toString().equals("OK")) {
                return Result.success();
            } else {
                return Result.error(ErrorEnum.UNKNOWN);
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return Result.error(ErrorEnum.UNKNOWN);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(ErrorEnum.UNKNOWN);
        }
    }
}
