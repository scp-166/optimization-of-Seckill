package com.nekosighed.miaosha.controller;

import com.nekosighed.miaosha.controller.viewobject.ItemInfoVO;
import com.nekosighed.miaosha.response.CommonReturnType;
import com.nekosighed.miaosha.service.impl.ItemInfoServiceImpl;
import com.nekosighed.miaosha.service.model.ItemInfoModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Validated
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/item")
public class ItemContorller extends BaseController{
    @Resource
    private ItemInfoServiceImpl itemInfoService;

    /**
     * 创建一条商品信息
     *
     * @param itemInfoVo
     * @return
     */
    @PostMapping("create")
    public CommonReturnType createItem(@Validated ItemInfoVO itemInfoVo){
        ItemInfoModel itemInfoModel = FillDataUtils.fillVoToModel(itemInfoVo, ItemInfoModel.class);
        itemInfoService.saveItemInfo(itemInfoModel);
        return CommonReturnType.success(null);
    }
}
