package com.nekosighed.miaosha.error;


import lombok.AllArgsConstructor;

/**
 * 业务错误枚举
 */
@AllArgsConstructor
public enum BusinessErrorEnum implements CommonError{
    /**
     * 业务错误枚举值
     */
    UNKNOWN_ERROR(10000, "未知错误"),
    PARAM_ERROR(10001, "通用参数错误"),

    USER_NOT_EXIST(20001, "用户不存在"),
    USER_ALREADY_EXIST(20002, "用户已存在"),
    LOGIN_FAIL(20003, "用户手机号或密码不正确"),
    OPT_AUTH_ERROR(20004, "短信验证失败"),
    USER_INFO_PASSWORD_NOT_MATCHING(20005, "用户信息和密码不匹配"),
    SAVE_PASSWORD_ERROR(20006, "保存密码失败"),

    ITEM_SAVE_ERROR(30001, "商品保存失败"),
    ITEM_STOCK_SAVE_ERROR(30002, "商品库存保存失败")
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
