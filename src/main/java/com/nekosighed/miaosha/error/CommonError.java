package com.nekosighed.miaosha.error;

/**
 * 通用错误接口
 */
public interface CommonError {
    /**
     * 获得错误码
     * @return
     */
    int getCode();

    /**
     * 获得错误描述信息
     * @return
     */
    String getMsg();

    /**
     * 设置错误描述信息
     *  并且返回自身
     * @param msg
     * @return 设置过错误描述信息的自身
     */
    CommonError setMsg(String msg);
}
