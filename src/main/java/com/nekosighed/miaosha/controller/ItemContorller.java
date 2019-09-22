package com.nekosighed.miaosha.controller;

import com.nekosighed.miaosha.controller.viewobject.ItemInfoVO;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.response.CommonReturnType;
import com.nekosighed.miaosha.service.impl.CacheServiceImpl;
import com.nekosighed.miaosha.service.impl.ItemInfoServiceImpl;
import com.nekosighed.miaosha.service.model.ItemInfoModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import com.nekosighed.miaosha.utils.RedisUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Validated
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/item")
public class ItemContorller {
    @Resource
    private ItemInfoServiceImpl itemInfoService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private CacheServiceImpl cacheService;

    /**
     * 创建一条商品信息
     *
     * @param itemInfoVo
     * @return
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CommonReturnType createItem(@Validated ItemInfoVO itemInfoVo) {
        ItemInfoModel itemInfoModel = FillDataUtils.fillVoToModel(itemInfoVo, ItemInfoModel.class);
        itemInfoService.saveItemInfo(itemInfoModel);
        return CommonReturnType.success(null);
    }

    /**
     * 获得商品详情
     *
     * @param itemId
     * @return
     */
    @GetMapping(value = "/get")
    public CommonReturnType getItem(@RequestParam Integer itemId) {
        ItemInfoModel itemInfoModel = (ItemInfoModel) cacheService.getCommonCache("item_" + itemId);
        if (Objects.isNull(itemInfoModel)) {
            itemInfoModel = (ItemInfoModel) redisUtils.get("item_" + itemId);
            if (Objects.isNull(itemInfoModel)) {
                itemInfoModel = itemInfoService.getItemInfoById(itemId);
                // 设置 redis 缓存
                redisUtils.set("item_" + itemId, itemInfoModel, 600);
            }
            // 设置本地缓存
            cacheService.setCommonCache("item_" + itemId, itemInfoModel);
        }

        ItemInfoVO itemInfoVO = FillDataUtils.fillModelToVo(itemInfoModel, ItemInfoVO.class);
        itemInfoVO = FillData.advanceData(itemInfoVO, itemInfoModel);
        return CommonReturnType.success(itemInfoVO);
    }

    /**
     * 获得商品详情列表
     *
     * @return
     */
    @GetMapping(value = "getAll")
    public CommonReturnType getAllItem() {
        List<ItemInfoModel> itemInfoModels = itemInfoService.getItemInfoList();
        return CommonReturnType.success(
                itemInfoModels.stream().map(itemInfoModel -> {
                    return FillDataUtils.fillModelToVo(itemInfoModel, ItemInfoVO.class);
                }).collect(Collectors.toList())
        );
    }

    private static class FillData {
        /**
         * 补充 ItemInfoVo 的活动信息
         *
         * @param itemInfoVO
         * @param itemInfoModel
         * @return
         */
        public static ItemInfoVO advanceData(ItemInfoVO itemInfoVO, ItemInfoModel itemInfoModel) {
            // 设置
            if (Objects.nonNull(itemInfoVO)) {
                if (Objects.isNull(itemInfoModel.getPromoInfoModel())) {
                    // 商品没有对应活动信息，设置为未开始(就是没有秒杀活动
                    itemInfoVO.setPromoStatus(-1);
                } else {
                    itemInfoVO.setPromoStatus(itemInfoModel.getPromoInfoModel().getIndStatus());
                    itemInfoVO.setPromoId(itemInfoModel.getPromoInfoModel().getId());
                    itemInfoVO.setPromoPrice(itemInfoModel.getPromoInfoModel().getPromoPrice());
                    // 将 joda-time 序列化指定字符串
                    itemInfoVO.setPromoStartTime(itemInfoModel.getPromoInfoModel().getStartTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
                }
                return itemInfoVO;
            } else {
                return null;
            }
        }
    }
}
