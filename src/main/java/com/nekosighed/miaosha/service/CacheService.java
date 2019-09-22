package com.nekosighed.miaosha.service;

/**
 * 本地缓存服务
 */
public interface CacheService {
    // 存缓存
    void setCommonCache(String key, Object value);

    // 取缓存
    Object getCommonCache(String key);

}
