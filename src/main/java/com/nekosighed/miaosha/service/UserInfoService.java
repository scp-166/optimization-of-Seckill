package com.nekosighed.miaosha.service;

import com.nekosighed.miaosha.service.model.UserInfoModel;

public interface UserInfoService {

    /**
     * 通过 id 获得用户信息
     */
    UserInfoModel getUserInfoById(Integer id);

    /**
     * 注册用户
     *
     * @param userInfoModel
     */
    void register(UserInfoModel userInfoModel);

    /**
     * 验证登陆
     *
     * @param telphone
     * @param encrptPassword
     * @return
     */
    UserInfoModel validateLogin(String telphone, String encrptPassword);
}
