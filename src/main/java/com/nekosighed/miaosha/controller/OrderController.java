package com.nekosighed.miaosha.controller;

import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.response.CommonReturnType;
import com.nekosighed.miaosha.service.impl.OrderInfoServiceImpl;
import com.nekosighed.miaosha.service.model.UserInfoModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

@RestController
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private OrderInfoServiceImpl orderInfoService;

    @PostMapping("/newOne")
    public CommonReturnType newOneOrder(@RequestParam("itemId") Integer itemId,
                                        @RequestParam("itemAccount") Integer itemAccount,
                                        @RequestParam(value = "promoId",required = false) Integer promoId) {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (Objects.isNull(isLogin)) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_LOGIN);
        }
        UserInfoModel userInfoModel = (UserInfoModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        orderInfoService.createOrder(userInfoModel.getId(), itemId, promoId, itemAccount);
        return CommonReturnType.success(null);
    }
}
