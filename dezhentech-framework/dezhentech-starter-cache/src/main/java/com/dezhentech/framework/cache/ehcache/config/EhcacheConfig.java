package com.dezhentech.framework.cache.ehcache.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * ehcache配置
 *
 * @author cuiyingfan
 * @date 2022/03/15
 */
@ConditionalOnProperty(prefix = "dezhen.cache", name = "type", havingValue = "ehcache")
public class EhcacheConfig {

    @Bean("ehCacheCacheManager")
    public EhCacheCacheManager getEhCacheCacheManager() throws IOException {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(CacheManager.create(new ClassPathResource("ehcache.xml").getInputStream()));
        return ehCacheCacheManager;
    }

    @Bean("dzEhcache")
    public Cache getCache(EhCacheCacheManager ehCacheCacheManager) {
        //初始化一个缓存
        CacheManager ehCachemanager = ehCacheCacheManager.getCacheManager();
        //添加缓存
        ehCachemanager.addCache("DzEhcache");
        //获取缓存
        Cache cache = ehCachemanager.getCache("DzEhcache");
        //修改缓存设置
        CacheConfiguration config = cache.getCacheConfiguration();
        config.setTimeToIdleSeconds(60);
        config.setTimeToLiveSeconds(120);
        return cache;
    }
}