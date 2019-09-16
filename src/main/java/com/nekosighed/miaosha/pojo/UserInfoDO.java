package com.nekosighed.miaosha.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDO implements Serializable {
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

    /**
     * user_info
     */
    private static final long serialVersionUID = 1L;
}