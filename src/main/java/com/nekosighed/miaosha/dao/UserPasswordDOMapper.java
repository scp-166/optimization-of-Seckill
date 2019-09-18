package com.nekosighed.miaosha.dao;

import com.nekosighed.miaosha.pojo.UserPasswordDO;

import java.util.List;

public interface UserPasswordDOMapper {
    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-16 22:34:06
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-16 22:34:06
     */
    int insert(UserPasswordDO record);

    /**
     * @Description:
     * @Author: chf
     * @CreateDate: 2019-09-18 17:04:43
     */
    int insertSelective(UserPasswordDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-16 22:34:06
     */
    UserPasswordDO selectByPrimaryKey(Integer id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-16 22:34:06
     */
    List<UserPasswordDO> selectAll();

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-16 22:34:06
     */
    int updateByPrimaryKey(UserPasswordDO record);

    UserPasswordDO getUserPasswordByUserId(Integer id);
}