package com.nekosighed.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.response.CommonReturnType;
import com.nekosighed.miaosha.service.impl.OrderInfoServiceImpl;
import com.nekosighed.miaosha.service.model.UserInfoModel;
import com.nekosighed.miaosha.utils.RedisUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

@RestController
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/order")
public class OrderController {
    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private OrderInfoServiceImpl orderInfoService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/newOne")
    public CommonReturnType newOneOrder(@RequestParam("itemId") Integer itemId,
                                        @RequestParam("itemAccount") Integer itemAccount,
                                        @RequestParam(value = "promoId", required = false) Integer promoId,
                                        @RequestParam(value = "token", required = false) String token) {

        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(BusinessErrorEnum.LACK_FOR_TOKEN);
        }
        UserInfoModel userInfoModel = (UserInfoModel) redisUtils.get(token);

        if (Objects.isNull(userInfoModel)) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_LOGIN);
        }

        orderInfoService.createOrder(userInfoModel.getId(), itemId, promoId, itemAccount);
        return CommonReturnType.success(null);
    }
}
