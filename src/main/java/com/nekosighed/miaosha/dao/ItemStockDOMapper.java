package com.nekosighed.miaosha.dao;

import com.nekosighed.miaosha.pojo.ItemStockDO;
import org.apache.ibatis.annotations.Param;

public interface ItemStockDOMapper {
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
    int insert(ItemStockDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    int insertSelective(ItemStockDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    ItemStockDO selectByPrimaryKey(Integer id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    int updateByPrimaryKeySelective(ItemStockDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-18 16:56:47
     */
    int updateByPrimaryKey(ItemStockDO record);

    /**
     * 查询商品对应库存
     *
     * @param itemId
     * @return
     */
    ItemStockDO getItemStockByItemId(@Param("itemId") Integer itemId);

    /**
     * 减少对应产品的库存
     * @param itemId
     * @param itemAccount
     * @return
     */
     int decStockAccount(@Param("itemId") Integer itemId, @Param("itemAccount") Integer itemAccount);
}