package com.nekosighed.miaosha.service.model;

import lombok.*;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoInfoModel {
    private Integer id;

    /**
     * 活动名称
     */
    private String promoName;

    /**
     * 匹配商品id
     */
    private Integer matchingItemId;

    /**
     * 活动开始时间
     */
    private DateTime startTime;

    /**
     * 活动结束时间
     */
    private DateTime endTime;

    /**
     * 活动商品价格
     */
    private BigDecimal promoPrice;

    /**
     * 活动状态 0未开始 1正在进行 2结束
     * ind开头，表db无关
     */
    private Integer indStatus;
}
