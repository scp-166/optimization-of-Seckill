package com.nekosighed.miaosha.controller.viewobject;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegisterVo  implements Serializable {
    /**
     * 性别
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     *
     */
    @NotNull(message = "性别不能不填")
    private Boolean gender;

    /**
     *
     */
    @NotNull(message = "年龄不能不填")
    @Max(value = 150, message = "年龄不能超过150岁")
    @Min(value = 18, message = "年龄不能少于18岁")
    private Integer age;

    /**
     *
     */
    @NotBlank(message = "手机号不能为空")
    @Min(value = 11, message = "手机号不能少于11位")
    private String telphone;


    @NotBlank(message = "密码不能为空")
    private String password;

    @NotNull(message = "验证码不能不填")
    private Integer optCode;


    /**
     * UserRegisterVo
     */
    private static final long serialVersionUID = 1L;
}
