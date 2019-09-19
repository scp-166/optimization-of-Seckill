package com.nekosighed.miaosha.pojo;

import java.math.BigDecimal;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
public class OrderInfoDO {
    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer itemId;

    /**
     * 购买商品数量
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemAccount() {
        return itemAccount;
    }

    public void setItemAccount(Integer itemAccount) {
        this.itemAccount = itemAccount;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}