package com.nekosighed.miaosha.service.impl;

import com.nekosighed.miaosha.dao.UserPasswordDOMapper;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.pojo.UserPasswordDO;
import com.nekosighed.miaosha.service.UserPasswordService;
import com.nekosighed.miaosha.service.model.UserPasswordModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {
    @Resource
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserPasswordModel getUserPasswordByUserId(Integer userId) {
        UserPasswordDO userPasswordDO = userPasswordDOMapper.getUserPasswordByUserId(userId);
        return FillDataUtils.fillDoToModel(userPasswordDO, UserPasswordModel.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int saveUserPassword(UserPasswordModel userPasswordModel) {
        UserPasswordDO userPasswordDO = FillDataUtils.fillModelToDo(userPasswordModel, UserPasswordDO.class);
        if (userPasswordDO != null) {
            int saveStatus = userPasswordDOMapper.insertSelective(userPasswordDO);
            if (saveStatus <= 0){
                throw new BusinessException(BusinessErrorEnum.SAVE_PASSWORD_ERROR);
            }
        }
        return 1;
    }
}
