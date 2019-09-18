package com.nekosighed.miaosha.controller;

import com.nekosighed.miaosha.controller.viewobject.UserInfoVO;
import com.nekosighed.miaosha.controller.viewobject.UserRegisterVo;
import com.nekosighed.miaosha.enumtype.RegisterModeEnum;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.response.CommonReturnType;
import com.nekosighed.miaosha.service.impl.UserInfoServiceImpl;
import com.nekosighed.miaosha.service.impl.UserPasswordServiceImpl;
import com.nekosighed.miaosha.service.model.UserInfoModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import com.nekosighed.miaosha.utils.Md5Utils;
import com.nekosighed.miaosha.utils.RedisUtils;
import com.nekosighed.miaosha.validation.ValidationResult;
import com.nekosighed.miaosha.validation.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@RestController
@RequestMapping("/")
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
public class UserController extends BaseController {
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
        System.out.println(telphone);
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

        return CommonReturnType.success(opt);
    }

    @PostMapping("/register")
    public CommonReturnType register(UserRegisterVo userRegisterVo) {
        System.out.println(userRegisterVo);
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
        System.out.println(telphone);
        System.out.println(password);
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
        redisUtils.set("IS_LOGIN" + ":" + telphone, 1);
        redisUtils.set("LOGIN_USER" + ":" + telphone, userInfoModel);
        return CommonReturnType.success("登陆成功");
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
