package com.puyuntech.ycmall.service;

/**
 * Created by 施长成 on 2015/10/7 0007.
 */
public interface CacheService {

    /**
     * 获取缓存存储路径
     *
     * @return 缓存存储路径
     */
    String getDiskStorePath();

    /**
     * 获取缓存数
     *
     * @return 缓存数
     */
    int getCacheSize();

    /**
     * 清除缓存
     */
    void clear();

    /**
     * 设置缓存
     * @param key
     * @param value
     */
    public void setCache(String cacheName , String key , Object value);

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public Object getCache(String cacheName , String key);
    
    /**
     * 
     * 根据key 删除 Cache  .
     * <br>
     * author: 施长成
     *   date: 2015-11-28 下午1:50:38
     * @param key
     * @return
     */
    public boolean removeCache(String CacheName,String key);

}
