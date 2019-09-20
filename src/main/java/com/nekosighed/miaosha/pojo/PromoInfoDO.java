package com.nekosighed.miaosha.pojo;

import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
@ToString
public class PromoInfoDO {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String promoName;

    /**
     * 
     */
    private Date startTime;

    /**
     * 
     */
    private Date endTime;

    /**
     * 
     */
    private BigDecimal promoPrice;

    /**
     * 匹配的商品id
     */
    private Integer matchingItemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName == null ? null : promoName.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getMatchingItemId() {
        return matchingItemId;
    }

    public void setMatchingItemId(Integer matchingItemId) {
        this.matchingItemId = matchingItemId;
    }
}