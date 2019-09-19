package com.nekosighed.miaosha.service;

import com.nekosighed.miaosha.service.model.ItemInfoModel;

import java.util.List;

public interface ItemInfoService {
    /**
     * 创建 商品信息
     *
     * @param itemInfoModel
     * @return
     */
    ItemInfoModel saveItemInfo(ItemInfoModel itemInfoModel);

    /**
     * 查询 商品信息列表
     *
     * @return
     */
    List<ItemInfoModel> getItemInfoList();

    /**
     * 查询单个 商品信息
     *
     * @param id
     * @return
     */
    ItemInfoModel getItemInfoById(Integer id);

    /**
     * 增加商品对应的销量
     *
     * @param id
     * @param itemAccount
     */
    boolean incSalesByPrimaryId(Integer id, Integer itemAccount);
}
