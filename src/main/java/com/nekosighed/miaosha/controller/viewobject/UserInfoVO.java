package com.nekosighed.miaosha.controller.viewobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO implements Serializable {
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
     * user_info
     */
    private static final long serialVersionUID = 1L;
}