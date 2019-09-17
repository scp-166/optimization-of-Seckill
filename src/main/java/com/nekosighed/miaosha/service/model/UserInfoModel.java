package com.nekosighed.miaosha.service.model;

import lombok.*;

import java.io.Serializable;

/**
 * 核心领域类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoModel implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 性别
     */
    private String name;

    /**
     * 
     */
    private Boolean gender;

    /**
     * 
     */
    private Integer age;

    /**
     * 
     */
    private String telphone;

    /**
     * 注册方式 byPhone byWechat byWebo
     */
    private String registerMode;

    /**
     * 第三方id
     */
    private String thirdPartyId;

    /** ----- user_password 补充进来的的属性
     * 加密密码
     */
    private String encrptPassword;

    /**
     * user_info
     */
    private static final long serialVersionUID = 1L;
}