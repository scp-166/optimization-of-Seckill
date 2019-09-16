package com.nekosighed.miaosha.error;


import lombok.AllArgsConstructor;

/**
 * 业务错误枚举
 */
@AllArgsConstructor
public enum BusinessErrorEnum implements CommonError{
    UNKNOWN_ERROR(10000, "未知错误"),
    PARAM_ERROR(10001, "通用参数错误"),

    USER_NOT_EXIST(20001, "用户不存在")
    ;
    private int code;
    private String msg;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public CommonError setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
