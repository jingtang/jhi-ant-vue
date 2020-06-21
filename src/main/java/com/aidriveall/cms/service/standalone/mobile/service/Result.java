package com.aidriveall.cms.service.standalone.mobile.service;

import com.aidriveall.cms.service.standalone.mobile.enumeration.ErrorEnum;

/**
 * 结果
 * Created by liamjung on 2018/1/19.
 */
public class Result<D> {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 数据
     */
    private D data;

    /**
     * 构造成功结果
     *
     * @return
     */
    public static Result success() {
        return new Result().success(true);
    }

    /**
     * 构造成功结果
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        return new Result()
                .success(true)
                .data(data);
    }

    /**
     * 构造错误结果
     *
     * @param errorEnum
     * @return
     */
    public static Result error(ErrorEnum errorEnum) {
        return new Result()
                .success(false)
                .errorCode(errorEnum.code())
                .errorMsg(errorEnum.msg());
    }

    public Result success(Boolean b) {
        this.success = b;
        return this;
    }

    public Result errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Result errorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public Result data(D data) {
        this.data = data;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
