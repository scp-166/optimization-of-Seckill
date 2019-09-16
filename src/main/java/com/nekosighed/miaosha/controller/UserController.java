package com.nekosighed.miaosha.controller;

import com.nekosighed.miaosha.controller.viewobject.UserInfoVO;
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
public class UserController {
    @Resource
    private UserInfoServiceImpl userInfoService;

    @GetMapping("/user")
    public CommonReturnType getUserInfo(@RequestParam("id") Integer id){
        return CommonReturnType.success(fillModelTOVo(userInfoService.getUserInfoById(id)));
    }

    /**
     * 将核心领域模型 转化为 viewObject
     *
     * @param userInfoModel
     * @return
     */
    private UserInfoVO fillModelTOVo(UserInfoModel userInfoModel){
        if (userInfoModel == null){
            return null;
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfoModel, userInfoVO);
        return userInfoVO;
    }
}
