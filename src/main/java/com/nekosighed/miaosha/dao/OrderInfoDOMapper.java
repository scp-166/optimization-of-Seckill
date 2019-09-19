package com.nekosighed.miaosha.dao;

import com.nekosighed.miaosha.pojo.OrderInfoDO;

public interface OrderInfoDOMapper {
    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-19 15:16:20
     */
    int deleteByPrimaryKey(String id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-19 15:16:20
     */
    int insert(OrderInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-19 15:16:20
     */
    int insertSelective(OrderInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-19 15:16:20
     */
    OrderInfoDO selectByPrimaryKey(String id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-19 15:16:20
     */
    int updateByPrimaryKeySelective(OrderInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-19 15:16:20
     */
    int updateByPrimaryKey(OrderInfoDO record);
}