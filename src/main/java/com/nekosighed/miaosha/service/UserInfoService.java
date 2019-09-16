package com.nekosighed.miaosha.service;

import com.nekosighed.miaosha.service.model.UserInfoModel;

public interface UserInfoService {

    /**
     * 通过 id 获得用户信息
     */
    UserInfoModel getUserInfoById(Integer id);
}
