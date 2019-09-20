package com.nekosighed.miaosha.service;

import com.nekosighed.miaosha.service.model.PromoInfoModel;

public interface PromoInfoService {

    /**
     * 获得即将秒杀和正在秒杀的商品对应的活动信息
     *
     * @param itemId
     * @return
     */
    PromoInfoModel getNotFinishedPromoInfoByItemId(Integer itemId);

    /**
     * 查询活动是否存在
     *
     * @param promoId
     * @return
     */
    PromoInfoModel getPromoInfoById(Integer promoId);
}
