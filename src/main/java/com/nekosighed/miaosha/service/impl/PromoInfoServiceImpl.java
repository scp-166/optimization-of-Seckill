package com.nekosighed.miaosha.service.impl;

import com.nekosighed.miaosha.dao.PromoInfoDOMapper;
import com.nekosighed.miaosha.pojo.PromoInfoDO;
import com.nekosighed.miaosha.service.PromoInfoService;
import com.nekosighed.miaosha.service.model.PromoInfoModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class PromoInfoServiceImpl implements PromoInfoService {
    @Resource
    private PromoInfoDOMapper promoInfoDOMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PromoInfoModel getNotFinishedPromoInfoByItemId(Integer itemId) {
        PromoInfoDO promoInfoDO= promoInfoDOMapper.getPromoInfoByItemId(itemId);
        PromoInfoModel promoInfoModel = FillDataUtils.fillDoToModel(promoInfoDO, PromoInfoModel.class);
        if (Objects.isNull(promoInfoModel)){
            return null;
        }
        // 默认Date和joda-time 的转换
        promoInfoModel.setStartTime(new DateTime(promoInfoDO.getStartTime()));
        promoInfoModel.setEndTime(new DateTime(promoInfoDO.getEndTime()));

        // 判断和设置商品对象活动状态
        if(promoInfoModel.getStartTime().isAfterNow()){
            promoInfoModel.setIndStatus(0);
        } else if (promoInfoModel.getEndTime().isBeforeNow()){
            promoInfoModel.setIndStatus(2);
        } else {
            promoInfoModel.setIndStatus(1);
        }

        return promoInfoModel;
    }

    @Override
    public PromoInfoModel getPromoInfoById(Integer promoId) {
        PromoInfoDO promoInfoDO = promoInfoDOMapper.selectByPrimaryKey(promoId);
        return FillDataUtils.fillDoToModel(promoInfoDO, PromoInfoModel.class);
    }
}
