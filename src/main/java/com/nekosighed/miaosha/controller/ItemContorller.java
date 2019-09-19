package com.nekosighed.miaosha.controller;

import com.nekosighed.miaosha.controller.viewobject.ItemInfoVO;
import com.nekosighed.miaosha.response.CommonReturnType;
import com.nekosighed.miaosha.service.impl.ItemInfoServiceImpl;
import com.nekosighed.miaosha.service.model.ItemInfoModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/item")
public class ItemContorller extends BaseController {
    @Resource
    private ItemInfoServiceImpl itemInfoService;

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
        ItemInfoModel itemInfoModel = itemInfoService.getItemInfoById(itemId);
        ItemInfoVO itemInfoVO = FillDataUtils.fillModelToVo(itemInfoModel, ItemInfoVO.class);
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
}
