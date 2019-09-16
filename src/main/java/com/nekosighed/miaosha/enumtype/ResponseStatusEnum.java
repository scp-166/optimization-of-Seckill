package com.nekosighed.miaosha.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关于返回值的枚举
 */
@Getter
@AllArgsConstructor
public enum ResponseStatusEnum {
    SUCCESS("success", "响应成功"),
    FAIL("fail", "响应失败")
    ;
    private String code;
    private String msg;
}
