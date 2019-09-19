package com.nekosighed.miaosha.service.model;

import lombok.*;

import java.math.BigDecimal;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderInfoModel {
    /**
     * 商品uuid
     * 2019091900000100
     */
    private String id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id
     */
    private Integer itemId;

    /**
     * 商品数量
     */
    private Integer itemAccount;

    /**
     * 商品单价
     */
    private BigDecimal itemPrice;

    /**
     * 订单总价
     */
    private BigDecimal orderPrice;
}
