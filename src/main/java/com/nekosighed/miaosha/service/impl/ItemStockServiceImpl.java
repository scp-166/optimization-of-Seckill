package com.nekosighed.miaosha.service.impl;

import com.nekosighed.miaosha.dao.ItemStockDOMapper;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.pojo.ItemStockDO;
import com.nekosighed.miaosha.service.ItemStockService;
import com.nekosighed.miaosha.service.model.ItemStockModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import com.nekosighed.miaosha.validation.ValidationResult;
import com.nekosighed.miaosha.validation.ValidatorImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ItemStockServiceImpl implements ItemStockService {
    @Resource
    private ItemStockDOMapper itemStockDOMapper;

    @Resource
    private ValidatorImpl validator;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ItemStockModel getItemStockByItemId(Integer itemId) {
        ItemStockDO itemStockDO = itemStockDOMapper.getItemStockByItemId(itemId);
        return FillDataUtils.fillDoToModel(itemStockDO, ItemStockModel.class);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ItemStockModel saveItemStock(ItemStockModel itemStockModel) {
        // 校验参数
        if (itemStockModel == null) {
            return null;
        }
        ValidationResult result = validator.validate(itemStockModel);
        if (result.isHaveErrors()) {
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR, result.getErrorsMsg());
        }
        // itemModel -> data object
        ItemStockDO itemStockDO = FillDataUtils.fillModelToDo(itemStockModel, ItemStockDO.class);
        if (itemStockDO == null) {
            return null;
        }
        if (itemStockDOMapper.insertSelective(itemStockDO) <= 0) {
            throw new BusinessException(BusinessErrorEnum.ITEM_SAVE_ERROR);
        }
        return getItemStockByPrimaryId(itemStockDO.getId());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemStockModel getItemStockByPrimaryId(Integer id) {
        ItemStockDO itemStockDO = itemStockDOMapper.selectByPrimaryKey(id);
        if (itemStockDO == null){
            return null;
        }
        return FillDataUtils.fillDoToModel(itemStockDO, ItemStockModel.class);
    }
}
