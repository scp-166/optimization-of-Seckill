package com.nekosighed.miaosha.controller.viewobject;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
* @Description:
* @Author: chf
* @CreateDate: null
*/
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoVO {
    /**
     * 
     */
    private Integer id;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品标题不能为空")
    private String title;

    /**
     * 商品价格
     */
    @NotNull(message = "商品价格不能不填")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;

    /**
     * 商品描述
     */
    @NotBlank(message = "商品描述不能为空")
    private String description;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 商品地址
     */
    @NotBlank(message = "图片地址不能为空")
    private String imgUrl;

    /**
     * 库存
     */
    @NotNull(message = "库存不能不填")
    private Integer stock;

    // ----------------- 活动字段 ----------------
    /**
     * 活动状态
     */
    private Integer promoStatus;

    /**
     * 活动id
     */
    private Integer promoId;

    /**
     * 活动价格
     */
    private BigDecimal promoPrice;

    /**
     * 活动开始时间
     */
    private String promoStartTime;
}