package com.nekosighed.miaosha.dao;

import com.nekosighed.miaosha.pojo.ItemInfoDO;

import java.util.List;

public interface ItemInfoDOMapper {
    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    int insert(ItemInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    int insertSelective(ItemInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    ItemInfoDO selectByPrimaryKey(Integer id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    int updateByPrimaryKeySelective(ItemInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    int updateByPrimaryKey(ItemInfoDO record);

    /**
     * 获得商品列表
     *  销量倒序排序
     *
     * @return
     */
    List<ItemInfoDO> listItemInfo();
}