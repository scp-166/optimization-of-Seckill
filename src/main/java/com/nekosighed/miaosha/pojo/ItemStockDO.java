package com.nekosighed.miaosha.pojo;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
public class ItemStockDO {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}