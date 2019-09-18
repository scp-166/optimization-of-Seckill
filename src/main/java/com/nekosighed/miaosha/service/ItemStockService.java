package com.nekosighed.miaosha.service;

import com.nekosighed.miaosha.service.model.ItemStockModel;

public interface ItemStockService {

    /**
     * 通过 itemId 查询 商品对应库存
     *
     * @param itemId
     * @return
     */
    ItemStockModel getItemStockByItemId(Integer itemId);

    /**
     * 保存商品库存
     *
     * @param itemStockModel
     * @return
     */
    ItemStockModel saveItemStock(ItemStockModel itemStockModel);

    /**
     * 获得商品对应库存
     *
     * @param id
     * @return
     */
    ItemStockModel getItemStockByPrimaryId(Integer id);
}
