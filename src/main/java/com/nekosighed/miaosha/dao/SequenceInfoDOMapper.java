package com.nekosighed.miaosha.dao;

import com.nekosighed.miaosha.pojo.SequenceInfoDO;
import org.apache.ibatis.annotations.Param;

public interface SequenceInfoDOMapper {
    /**
     * @Description:
     * @Author: chf
     * @CreateDate: 2019-09-19 16:09:51
     */
    int deleteByPrimaryKey(String tableInfo);

    /**
     * @Description:
     * @Author: chf
     * @CreateDate: 2019-09-19 16:09:51
     */
    int insert(SequenceInfoDO record);

    /**
     * @Description:
     * @Author: chf
     * @CreateDate: 2019-09-19 16:09:51
     */
    int insertSelective(SequenceInfoDO record);

    /**
     * @Description:
     * @Author: chf
     * @CreateDate: 2019-09-19 16:09:51
     */
    SequenceInfoDO selectByPrimaryKey(String tableInfo);

    /**
     * @Description:
     * @Author: chf
     * @CreateDate: 2019-09-19 16:09:51
     */
    int updateByPrimaryKeySelective(SequenceInfoDO record);

    /**
     * @Description:
     * @Author: chf
     * @CreateDate: 2019-09-19 16:09:51
     */
    int updateByPrimaryKey(SequenceInfoDO record);

    /**
     * 根据 表名称 获取步长信息
     *
     * @param tableInfo
     * @return
     */
    SequenceInfoDO getSequenceInfoByTableInfo(@Param("tableInfo") String tableInfo);
}