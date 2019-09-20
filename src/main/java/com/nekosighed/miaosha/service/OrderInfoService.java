package com.nekosighed.miaosha.service;

import com.nekosighed.miaosha.service.model.OrderInfoModel;

public interface OrderInfoService {
    /**
     * 创建订单
     *
     * @param userId
     * @param itemId
     * @param promoId
     * @param itemAmount
     * @return
     */
    OrderInfoModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer itemAmount);
}
