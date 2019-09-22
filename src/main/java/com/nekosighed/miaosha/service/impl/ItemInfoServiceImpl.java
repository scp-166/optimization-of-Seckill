package com.nekosighed.miaosha.service.impl;

import com.nekosighed.miaosha.dao.ItemInfoDOMapper;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.pojo.ItemInfoDO;
import com.nekosighed.miaosha.pojo.ItemStockDO;
import com.nekosighed.miaosha.service.ItemInfoService;
import com.nekosighed.miaosha.service.model.ItemInfoModel;
import com.nekosighed.miaosha.service.model.ItemStockModel;
import com.nekosighed.miaosha.service.model.PromoInfoModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import com.nekosighed.miaosha.validation.ValidationResult;
import com.nekosighed.miaosha.validation.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ItemInfoServiceImpl implements ItemInfoService {
    @Resource
    private ItemInfoDOMapper itemInfoDoMapper;

    @Resource
    private ItemStockServiceImpl itemStockService;

    @Resource
    private PromoInfoServiceImpl promoInfoService;

    @Resource
    private ValidatorImpl validator;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ItemInfoModel saveItemInfo(ItemInfoModel itemInfoModel) {
        // 校验参数
        if (Objects.isNull(itemInfoModel)) {
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR, "缺失商品信息参数");
        }
        // 校验属性
        ValidationResult result = validator.validate(itemInfoModel);
        if (result.isHaveErrors()) {
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR, result.getErrorsMsg());
        }
        // 转换 itemModel -> dataObject
        ItemInfoDO itemInfoDO = FillDataUtils.fillModelToDo(itemInfoModel, ItemInfoDO.class);
        // 写入数据库
        if (itemInfoDoMapper.insertSelective(itemInfoDO) <= 0) {
            throw new BusinessException(BusinessErrorEnum.ITEM_SAVE_ERROR);
        }
        itemInfoModel.setId(itemInfoDO.getId());

        ItemStockModel itemStockModel = new ItemStockModel();
        itemStockModel.setItemId(itemInfoModel.getId());
        itemStockModel.setStock(itemInfoModel.getStock());

        if (Objects.isNull(itemStockService.saveItemStock(itemStockModel))) {
            throw new BusinessException(BusinessErrorEnum.ITEM_STOCK_SAVE_ERROR);
        }
        // 返回创建完成的对象
        return this.getItemInfoById(itemInfoModel.getId());
    }

    @Override
    public List<ItemInfoModel> getItemInfoList() {
        List<ItemInfoDO> itemInfoDOList = itemInfoDoMapper.listItemInfo();
        return itemInfoDOList.stream().map(itemInfoDO -> {
            ItemStockModel itemStockModel = itemStockService.getItemStockByItemId(itemInfoDO.getId());
            ItemStockDO itemStockDO = FillDataUtils.fillModelToDo(itemStockModel, ItemStockDO.class);
            return FillData.fillBothDoToModel(itemInfoDO, itemStockDO);

        }).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemInfoModel getItemInfoById(Integer id) {
        ItemInfoDO itemInfoDO = itemInfoDoMapper.selectByPrimaryKey(id);
        if (Objects.isNull(itemInfoDO)) {
            return null;
        }
        // 获得库存信息
        ItemStockModel itemStockModel = itemStockService.getItemStockByItemId(itemInfoDO.getId());
        ItemStockDO itemStockDO = FillDataUtils.fillModelToDo(itemStockModel, ItemStockDO.class);
        ItemInfoModel itemInfoModel = FillData.fillBothDoToModel(itemInfoDO, itemStockDO);
        if (Objects.isNull(itemInfoModel)){
            throw new BusinessException(BusinessErrorEnum.FILL_DATA_NULL, "合并失败");
        }

        // 设置活动信息
        PromoInfoModel promoInfoModel = promoInfoService.getNotFinishedPromoInfoByItemId(itemInfoModel.getId());
        if (Objects.nonNull(promoInfoModel)){
            itemInfoModel.setPromoInfoModel(promoInfoModel);
        }
        itemInfoModel.setIndPromoId(promoInfoModel.getId());
        return itemInfoModel;
    }

    @Override
    public boolean incSalesByPrimaryId(Integer id, Integer itemAccount) {
        return itemInfoDoMapper.incSalesByPrimaryId(id, itemAccount) > 0;
    }

    private static class FillData {
        /**
         * 合并两个 DO，部分查询
         *
         * @param itemInfoDO
         * @param itemStockDO
         * @return
         */
        private static ItemInfoModel fillBothDoToModel(ItemInfoDO itemInfoDO, ItemStockDO itemStockDO) {
            if (Objects.isNull(itemInfoDO)) {
                return null;
            }
            ItemInfoModel itemInfoModel = new ItemInfoModel();
            BeanUtils.copyProperties(itemInfoDO, itemInfoModel);
            if (Objects.nonNull(itemStockDO)) {
                itemInfoModel.setStock(itemStockDO.getStock());
            }
            return itemInfoModel;
        }
    }
}
