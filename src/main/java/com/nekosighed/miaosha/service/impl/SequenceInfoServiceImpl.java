package com.nekosighed.miaosha.service.impl;

import com.nekosighed.miaosha.dao.SequenceInfoDOMapper;
import com.nekosighed.miaosha.error.BusinessErrorEnum;
import com.nekosighed.miaosha.error.BusinessException;
import com.nekosighed.miaosha.pojo.SequenceInfoDO;
import com.nekosighed.miaosha.service.SequencdeInfoService;
import com.nekosighed.miaosha.service.model.SequenceInfoModel;
import com.nekosighed.miaosha.utils.FillDataUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class SequenceInfoServiceImpl implements SequencdeInfoService {
    @Resource
    private SequenceInfoDOMapper sequenceInfoDOMapper;

    @Override
    public SequenceInfoModel getSequenceInfoByTableInfo(String tableInfo) {
        SequenceInfoDO sequenceInfoDO = sequenceInfoDOMapper.getSequenceInfoByTableInfo(tableInfo);
        return FillDataUtils.fillDoToModel(sequenceInfoDO, SequenceInfoModel.class);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean updateByTableName(SequenceInfoModel sequenceInfoModel) {
        if (Objects.isNull(sequenceInfoModel)) {
            throw new BusinessException(BusinessErrorEnum.PARAM_VALIDATE_ERROR, "步长信息缺失");
        }
        SequenceInfoDO sequenceInfoDO = FillDataUtils.fillModelToDo(sequenceInfoModel, SequenceInfoDO.class);
        if (Objects.isNull(sequenceInfoDO)) {
            throw new BusinessException(BusinessErrorEnum.FILL_DATA_NULL, "转换信息错误");
        }
        return sequenceInfoDOMapper.updateByPrimaryKeySelective(sequenceInfoDO) > 0;
    }
}
