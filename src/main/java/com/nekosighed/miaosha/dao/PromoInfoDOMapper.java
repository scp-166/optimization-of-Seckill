package com.nekosighed.miaosha.dao;

import com.nekosighed.miaosha.pojo.PromoInfoDO;

public interface PromoInfoDOMapper {
    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-20 10:18:13
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-20 10:18:13
     */
    int insert(PromoInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-20 10:18:13
     */
    int insertSelective(PromoInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-20 10:18:13
     */
    PromoInfoDO selectByPrimaryKey(Integer id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-20 10:18:13
     */
    int updateByPrimaryKeySelective(PromoInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-20 10:18:13
     */
    int updateByPrimaryKey(PromoInfoDO record);

    /**
     * 根据商品id获得对应的活动信息
     *
     * @param itemId
     * @return
     */
    PromoInfoDO getPromoInfoByItemId(Integer itemId);
}