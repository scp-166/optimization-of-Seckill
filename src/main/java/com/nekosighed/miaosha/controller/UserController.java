package com.nekosighed.miaosha.controller;

import com.nekosighed.miaosha.controller.viewobject.UserInfoVO;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.response.CommonReturnType;
import com.nekosighed.miaosha.service.impl.UserInfoServiceImpl;
import com.nekosighed.miaosha.service.model.UserInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class UserController extends BaseController{
    @Resource
    private UserInfoServiceImpl userInfoService;

    @GetMapping("/user")
    public CommonReturnType getUserInfo(@RequestParam("id") Integer id) throws Exception {
        UserInfoVO userInfoVO = fillModelTOVo(userInfoService.getUserInfoById(id));
        if (userInfoVO == null) {
            // 会抛出对应异常错误信息
            // throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
            // 会抛出未知错误信息
            throw new Exception(BusinessErrorEnum.USER_NOT_EXIST.getMsg());
        }
        return CommonReturnType.success(userInfoVO);
    }

    /**
     * 将核心领域模型 转化为 viewObject
     *
     * @param userInfoModel
     * @return
     */
    private UserInfoVO fillModelTOVo(UserInfoModel userInfoModel) {
        if (userInfoModel == null) {
            return null;
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfoModel, userInfoVO);
        return userInfoVO;
    }
}
