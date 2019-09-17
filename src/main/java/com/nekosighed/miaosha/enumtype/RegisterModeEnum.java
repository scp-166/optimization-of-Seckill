package com.nekosighed.miaosha.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegisterModeEnum {
    /**
     * 注册方式
     */
    BY_PHONE("BY_PHONE", "手机注册方式"),
    BY_WECHAT("BY_WECHAT", "微信注册方式"),
    BY_WEBOT("BY_WEBOT", "微博注册方式")
    ;
    private String code;
    private String msg;
}
