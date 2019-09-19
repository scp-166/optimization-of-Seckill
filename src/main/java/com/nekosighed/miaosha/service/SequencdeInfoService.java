package com.nekosighed.miaosha.service;

import com.nekosighed.miaosha.service.model.SequenceInfoModel;

public interface SequencdeInfoService {
    /**
     * 获取 步长信息
     *
     * @param tableInfo
     * @return
     */
    SequenceInfoModel getSequenceInfoByTableInfo(String tableInfo);

    /**
     * 更新步长信息
     *
     * @param sequenceInfoModel
     * @return
     */
     boolean updateByTableName(SequenceInfoModel sequenceInfoModel);
}
