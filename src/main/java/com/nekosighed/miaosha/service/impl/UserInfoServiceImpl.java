package com.nekosighed.miaosha.service.impl;

import com.nekosighed.miaosha.dao.UserInfoDOMapper;
import com.nekosighed.miaosha.dao.UserPasswordDOMapper;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.pojo.UserInfoDO;
import com.nekosighed.miaosha.pojo.UserPasswordDO;
import com.nekosighed.miaosha.service.UserInfoService;
import com.nekosighed.miaosha.service.model.UserInfoModel;
import com.nekosighed.miaosha.service.model.UserPasswordModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import com.nekosighed.miaosha.utils.Md5Utils;
import com.nekosighed.miaosha.validation.ValidationResult;
import com.nekosighed.miaosha.validation.ValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Resource
    private UserInfoDOMapper userInfoDOMapper;

    @Resource
    private UserPasswordServiceImpl userPasswordService;

    @Resource
    private ValidatorImpl validator;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserInfoModel getUserInfoById(Integer id) {
        UserInfoDO userInfoDO = userInfoDOMapper.selectByPrimaryKey(id);
        UserPasswordModel userPasswordModel = userPasswordService.getUserPasswordByUserId(id);
        UserPasswordDO userPasswordDO = FillDataUtils.fillModelToDo(userPasswordModel, UserPasswordDO.class);
        return FillData.fillDoToModel(userInfoDO, userPasswordDO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void register(UserInfoModel userInfoModel) {
        if (Objects.isNull(userInfoModel)) {
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR, "缺失用户信息参数");
        }
        ValidationResult result = validator.validate(userInfoModel);
        if (result.isHaveErrors()) {
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR, result.getErrorsMsg());
        }
        UserInfoDO userInfoDO = FillDataUtils.fillModelToDo(userInfoModel, UserInfoDO.class);
        UserPasswordDO userPasswordDO = FillDataUtils.fillModelToDo(userInfoModel, UserPasswordDO.class);
        try {
            userInfoDOMapper.insertSelective(userInfoDO);
        } catch (DuplicateKeyException e) {
            logger.warn("账号: {}遇到唯一索引而导致创建失败", userInfoModel.getTelphone());
            throw new BusinessException(BusinessErrorEnum.USER_ALREADY_EXIST);
        }
        // 设置 userId
        userPasswordDO.setUserId(userInfoDO.getId());
        UserPasswordModel userPasswordModel = FillDataUtils.fillDoToModel(userPasswordDO, UserPasswordModel.class);
        userPasswordService.saveUserPassword(userPasswordModel);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserInfoModel validateLogin(String telphone, String encrptPassword) {
        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(encrptPassword)) {
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR, "手机号或密码不能为空");
        }
        // 通过 telphone 拿到用户信息
        UserInfoDO userInfoDO = userInfoDOMapper.getUserInfoByTelphone(telphone);
        if (Objects.isNull(userInfoDO)) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }
        // 通过用户信息的id拿到密码
        UserPasswordModel userPasswordModel = userPasswordService.getUserPasswordByUserId(userInfoDO.getId());
        UserPasswordDO userPasswordDO = FillDataUtils.fillModelToDo(userPasswordModel, UserPasswordDO.class);
        if (Objects.isNull(userPasswordDO)) {
            throw new BusinessException(BusinessErrorEnum.FILL_DATA_NULL);
        }
        // 组装返回值
        UserInfoModel userInfoModel = FillData.fillDoToModel(userInfoDO, userPasswordDO);
        if (Objects.isNull(userInfoModel)){
            throw new BusinessException(BusinessErrorEnum.FILL_DATA_NULL, "数据格式转换为空");
        }
        // 通过用户信息的密码和传入来的密码进行匹配
        if (!Objects.equals(encrptPassword, userInfoModel.getEncrptPassword())) {
            throw new BusinessException(BusinessErrorEnum.LOGIN_FAIL);
        }
        return userInfoModel;
    }

    /**
     * model 和 do 之间的转换
     */
    private static class FillData {
        /**
         * 将 UserInfoDo 和 UserPasswordDo 的属性封装到 领域模型 UserInfoModel 中
         *
         * @param userInfoDO
         * @param userPasswordDO
         * @return 领域模型 UserInfoModel
         */
        private static UserInfoModel fillDoToModel(UserInfoDO userInfoDO, UserPasswordDO userPasswordDO) {
            if (Objects.isNull(userInfoDO)) {
                return null;
            }
            UserInfoModel userInfoModel = new UserInfoModel();

            BeanUtils.copyProperties(userInfoDO, userInfoModel);

            if (Objects.nonNull(userPasswordDO)) {
                userInfoModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
            }
            return userInfoModel;
        }
    }
}
