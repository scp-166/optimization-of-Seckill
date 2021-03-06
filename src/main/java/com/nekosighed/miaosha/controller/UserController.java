package com.nekosighed.miaosha.controller;

import com.nekosighed.miaosha.controller.viewobject.UserInfoVO;
import com.nekosighed.miaosha.controller.viewobject.UserRegisterVO;
import com.nekosighed.miaosha.enumtype.RegisterModeEnum;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.response.CommonReturnType;
import com.nekosighed.miaosha.service.impl.UserInfoServiceImpl;
import com.nekosighed.miaosha.service.model.UserInfoModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import com.nekosighed.miaosha.utils.Md5Utils;
import com.nekosighed.miaosha.utils.RedisUtils;
import com.nekosighed.miaosha.validation.ValidationResult;
import com.nekosighed.miaosha.validation.ValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/")
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserInfoServiceImpl userInfoService;

    /**
     * spring 包装过的，是一个proxy，支持并发访问，非单例
     */
    @Resource
    HttpServletRequest httpServletRequest;

    @Resource
    RedisUtils redisUtils;

    @Resource
    ValidatorImpl validator;

    @PostMapping("/getOpt")
    public CommonReturnType getOpt(@RequestParam(value = "telphone", required = false) String telphone) {
        if (StringUtils.isEmpty(telphone)) {
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR, "手机号不能为空");
        }
        // 1. 按照一定的规则生成 opt Code
        Random random = new Random();
        // [0, 99999)
        int opt = random.nextInt(99999);
        // [10000, 109999)
        opt += 10000;

        // 2. 绑定用户手机号和 opt Code
        redisUtils.set(telphone, opt, 600);

        logger.info("手机 {} 获得验证码 {}", telphone, opt);

        return CommonReturnType.success(opt);
    }

    @PostMapping("/register")
    public CommonReturnType register(UserRegisterVO userRegisterVo) {
        logger.info("注册的账号参数是: {}", userRegisterVo);
        // 参数校验
        ValidationResult result = validator.validate(userRegisterVo);
        if (result.isHaveErrors()) {
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR, result.getErrorsMsg());
        }

        // 验证码校验
        if (!userRegisterVo.getOptCode().equals(redisUtils.get(userRegisterVo.getTelphone()))) {
            throw new BusinessException(BusinessErrorEnum.OPT_AUTH_ERROR);
        }
        // VO -> Model
        UserInfoModel userInfoModel = new UserInfoModel();
        BeanUtils.copyProperties(userRegisterVo, userInfoModel);
        userInfoModel.setRegisterMode(RegisterModeEnum.BY_PHONE.getMsg());
        try {
            userInfoModel.setEncrptPassword(Md5Utils.encodeByMd5(userRegisterVo.getPassword()));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new BusinessException(BusinessErrorEnum.UNKNOWN_ERROR, "密码解析错误");
        }
        // 进行注册
        userInfoService.register(userInfoModel);

        return CommonReturnType.success(null);
    }

    @PostMapping("/login")
    public CommonReturnType login(@RequestParam(value = "telphone", required = false) String telphone,
                                  @RequestParam(value = "password", required = false) String password) {
        logger.info("登陆手机号和密码分别是: {}, {}", telphone, password);

        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR, "手机号或密码不能为空");
        }
        UserInfoModel userInfoModel;
        try {
            userInfoModel = userInfoService.validateLogin(telphone, Md5Utils.encodeByMd5(password));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new BusinessException(BusinessErrorEnum.UNKNOWN_ERROR, "密码解析错误");
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        redisUtils.set(uuid, userInfoModel, 600);

        return CommonReturnType.success(uuid);
    }

    @GetMapping("/user")
    public CommonReturnType getUserInfo(@RequestParam("id") Integer id) throws Exception {
        UserInfoVO userInfoVO = FillDataUtils.fillModelToVo(userInfoService.getUserInfoById(id), UserInfoVO.class);
        if (userInfoVO == null) {
            // 会抛出对应异常错误信息
            // throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
            // 会抛出未知错误信息
            throw new Exception(BusinessErrorEnum.USER_NOT_EXIST.getMsg());
        }
        return CommonReturnType.success(userInfoVO);
    }


}
