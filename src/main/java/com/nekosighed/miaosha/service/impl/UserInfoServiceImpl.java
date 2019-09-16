package com.nekosighed.miaosha.service.impl;

import com.nekosighed.miaosha.dao.UserInfoDOMapper;
import com.nekosighed.miaosha.dao.UserPasswordDOMapper;
import com.nekosighed.miaosha.pojo.UserInfoDO;
import com.nekosighed.miaosha.pojo.UserPasswordDO;
import com.nekosighed.miaosha.service.UserInfoService;
import com.nekosighed.miaosha.service.model.UserInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDOMapper userInfoDOMapper;

    @Resource
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserInfoModel getUserInfoById(Integer id) {
        UserInfoDO userInfoDO = userInfoDOMapper.selectByPrimaryKey(id);
        UserPasswordDO userPasswordDO = userPasswordDOMapper.getUserPasswordByUserId(id);
        return fillDoToModel(userInfoDO, userPasswordDO);
    }

    /**
     * 将 UserInfoDo 和 UserPasswordDo 的属性封装到 领域模型 UserInfoModel 中
     *
     * @param userInfoDO
     * @param userPasswordDO
     * @return 领域模型 UserInfoModel
     */
    private UserInfoModel fillDoToModel(UserInfoDO userInfoDO, UserPasswordDO userPasswordDO) {
        if (userInfoDO == null){
            return null;
        }
        UserInfoModel userInfoModel = new UserInfoModel();

        BeanUtils.copyProperties(userInfoDO, userInfoModel);

        if (userPasswordDO != null){
            userInfoModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userInfoModel;
    }
}
