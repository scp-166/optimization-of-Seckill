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
public class UserPasswordDO implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 加密密码
     */
    private String encrptPassword;

    /**
     * user_password
     */
    private static final long serialVersionUID = 1L;
}