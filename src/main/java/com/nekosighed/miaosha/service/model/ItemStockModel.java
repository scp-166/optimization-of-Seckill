package com.nekosighed.miaosha.service.model;

import lombok.*;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemStockModel {
    /**
     * 
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer itemId;

    /**
     * 库存
     */
    private Integer stock;
}