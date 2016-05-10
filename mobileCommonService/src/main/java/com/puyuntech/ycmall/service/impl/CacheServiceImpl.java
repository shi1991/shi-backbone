package com.puyuntech.ycmall.service.impl;

import com.puyuntech.ycmall.service.CacheService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by 施长成 on 2015/10/7 0007.
 *
 * 缓存Service
 */
@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {

    @Resource(name = "ehCacheManager")
    private CacheManager cacheManager;

    /**
     * 获取缓存存储路径
     *
     * @return 缓存存储路径
     */
    @Override
    public String getDiskStorePath() {
        return cacheManager.getConfiguration().getDiskStoreConfiguration().getPath();
    }

    /**
     * 获取缓存数
     *
     * @return 缓存数
     */
    @Override
    public int getCacheSize() {
        int cacheSize = 0;
        String[] cacheNames = cacheManager.getCacheNames();
        if (cacheNames != null) {
            for (String cacheName : cacheNames) {
                Ehcache cache = cacheManager.getEhcache(cacheName);
                if (cache != null) {
                    cacheSize += cache.getSize();
                }
            }
        }
        return cacheSize;
    }

    /**
     * 清除缓存
     */
    @Override
    public void clear() {

    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    @Override
    @Transactional(readOnly = true)
    public void setCache( String CacheName , String key, Object value) {
        Ehcache cache = cacheManager.getEhcache( CacheName );
        cache.put(new Element(key , value));

    }

    /**
     * 获取缓存
     *
     * @param key
     *
     * @return
     */
    @Override
    public Object getCache(String CacheName , String key) {
        Ehcache cache = cacheManager.getEhcache( CacheName );
        if(null != cache.get(key)){
            return cache.get(key).getObjectValue();
        }else{
            return null;
        }
    }

	@Override
	public boolean removeCache(String CacheName ,String key) {
		Ehcache cache = cacheManager.getEhcache(CacheName);
		 if(null != cache.get(key)){
	            return cache.remove( key );
	        }else{
	            return false;
	        }
	}
}
