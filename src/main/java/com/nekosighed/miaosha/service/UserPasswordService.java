package com.nekosighed.miaosha.service;

import com.nekosighed.miaosha.pojo.UserPasswordDO;
import com.nekosighed.miaosha.service.model.UserPasswordModel;

public interface UserPasswordService {
    /**
     * 通过 用户id 查到密码信息
     * @param userId
     * @return
     */
     UserPasswordModel getUserPasswordByUserId(String userId);
}
