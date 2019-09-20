package com.nekosighed.miaosha.service.impl;

import com.nekosighed.miaosha.dao.OrderInfoDOMapper;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.pojo.OrderInfoDO;
import com.nekosighed.miaosha.service.OrderInfoService;
import com.nekosighed.miaosha.service.UserInfoService;
import com.nekosighed.miaosha.service.model.*;
import com.nekosighed.miaosha.utils.FillDataUtils;
import com.sun.tools.corba.se.idl.constExpr.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    OrderInfoDOMapper orderInfoDOMapper;

    @Resource
    private ItemInfoServiceImpl itemInfoService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private ItemStockServiceImpl itemStockService;

    @Resource
    private SequenceInfoServiceImpl sequenceInfoService;

    @Resource
    private PromoInfoServiceImpl promoInfoService;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public OrderInfoModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer itemAccount) {
        // 校验参数，以及校验 用户是否合法， 商品是否存在， 购买数量是否足够, 是否处于活动状态
        ItemInfoModel itemInfoModel = itemInfoService.getItemInfoById(itemId);
        if (Objects.isNull(itemInfoModel)) {
            throw new BusinessException(BusinessErrorEnum.PARAM_VALIDATE_ERROR, "商品信息不存在");
        }
        UserInfoModel userInfoModel = userInfoService.getUserInfoById(userId);
        if (Objects.isNull(userInfoModel)) {
            throw new BusinessException(BusinessErrorEnum.PARAM_VALIDATE_ERROR, "用户信息不存在");
        }
        if (itemAccount <= 0 || itemAccount > 99) {
            throw new BusinessException(BusinessErrorEnum.PARAM_VALIDATE_ERROR, "购买属性范围不正确");
        }
        // 校验活动状态
        if (Objects.nonNull(promoId)) {
            // 活动是否存在
            PromoInfoModel promoInfoModel = promoInfoService.getPromoInfoById(promoId);
            if (Objects.isNull(promoInfoModel)) {
                throw new BusinessException(BusinessErrorEnum.PARAM_VALIDATE_ERROR, "活动不存在");
            }
            // 活动是否符合本商品id
            if (!promoInfoModel.getId().equals(itemInfoModel.getPromoInfoModel().getId())) {
                throw new BusinessException(BusinessErrorEnum.PARAM_VALIDATE_ERROR, "活动不匹配");
            }
            // 校验活动是否进行中
            if (itemInfoModel.getPromoInfoModel().getIndStatus() == 2){
                throw new BusinessException(BusinessErrorEnum.PARAM_VALIDATE_ERROR, "活动已经结束");
            }

            // 设置活动价格
            itemInfoModel.setPrice(promoInfoModel.getPromoPrice());
        }
        // 落单减库存
        if (!itemStockService.decStockAccount(itemId, itemAccount)) {
            throw new BusinessException(BusinessErrorEnum.ITEM_STOCK_NOT_ENOUGH);
        }
        // 保存在核心领域层，可以用于返回
        OrderInfoModel orderInfoModel = new OrderInfoModel();

        orderInfoModel.setItemId(itemInfoModel.getId());
        orderInfoModel.setUserId(userInfoModel.getId());
        orderInfoModel.setItemAccount(itemAccount);
        // 平销价格或者活动价格在前面已经设置过了
        orderInfoModel.setItemPrice(itemInfoModel.getPrice());
        orderInfoModel.setOrderPrice(itemInfoModel.getPrice().multiply(BigDecimal.valueOf(itemAccount)));
        // 生成交易流水号
        orderInfoModel.setId(generateOrderId());

        // 创建订单
        OrderInfoDO orderInfoDO = FillDataUtils.fillModelToDo(orderInfoModel, OrderInfoDO.class);
        if (Objects.isNull(orderInfoDO)) {
            throw new BusinessException(BusinessErrorEnum.FILL_DATA_NULL, "转换订单model失败");
        }
        orderInfoDOMapper.insertSelective(orderInfoDO);

        // 减少商品销量
        if (!itemInfoService.incSalesByPrimaryId(itemInfoModel.getId(), itemAccount)) {
            throw new BusinessException(BusinessErrorEnum.INC_ITEM_SALES_ERROR);
        }

        // 返回
        return orderInfoModel;
    }

    /**
     * REQUIRES_NEW 不是被其他事务回滚而回滚，永远用自己的事务
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderId() {
        StringBuilder builder = new StringBuilder();
        // 8 位年月日
        LocalDateTime localDateTime = LocalDateTime.now();
        String nowDate = localDateTime.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        builder.append(nowDate);
        // 6 位 自增长
        SequenceInfoModel sequenceInfoModel = sequenceInfoService.getSequenceInfoByTableInfo("order_info");
        int sequence = sequenceInfoModel.getCurrentStep();
        sequenceInfoModel.setCurrentStep(sequenceInfoModel.getCurrentStep() + sequenceInfoModel.getStep());
        if (!sequenceInfoService.updateByTableName(sequenceInfoModel)) {
            System.out.println("订单编号更新失败");
        }
        // 拼接零
        String incrementId = String.valueOf(sequence);
        for (int i = 0; i < 6 - incrementId.length(); i++) {
            builder.append("0");
        }
        // 然后才拼接id
        builder.append(incrementId);

        // 2 位 分库分表
        builder.append("00");
        return builder.toString();
    }

    public static void main(String[] args) {

    }
}
