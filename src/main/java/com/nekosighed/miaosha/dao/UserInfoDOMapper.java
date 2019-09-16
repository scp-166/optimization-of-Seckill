package com.nekosighed.miaosha.dao;

import com.nekosighed.miaosha.pojo.UserInfoDO;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface UserInfoDOMapper {
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
    int insert(UserInfoDO record);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-16 22:34:06
     */
    UserInfoDO selectByPrimaryKey(Integer id);

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-16 22:34:06
     */
    List<UserInfoDO> selectAll();

    /**
     * @Description: 
     * @Author: chf
     * @CreateDate: 2019-09-16 22:34:06
     */
    int updateByPrimaryKey(UserInfoDO record);

    /**
     * 根据 id 获得 用户id
     * @param id
     */
    UserInfoDO getUserInfoById(@Param("id")Integer id);
}