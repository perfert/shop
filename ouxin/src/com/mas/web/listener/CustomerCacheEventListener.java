/**    
 * 文件名：CustomerCacheEventListener.java    
 *    
 * 版本信息：    
 * 日期：2018-5-9    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.web.listener;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2018-5-9
 */
public class CustomerCacheEventListener implements CacheEventListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
        logger.info("cache removed. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    @Override
    public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {
        logger.info("cache put. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    @Override
    public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {
        logger.info("cache updated. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    @Override
    public void notifyElementExpired(Ehcache ehcache, Element element) {
        logger.info("cache expired. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    @Override
    public void notifyElementEvicted(Ehcache ehcache, Element element) {
        logger.info("cache evicted. key = {}, value = {}", element.getObjectKey(), element.getObjectValue());
    }

    @Override
    public void notifyRemoveAll(Ehcache ehcache) {
        logger.info("all elements removed. cache name = {}", ehcache.getName());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public void dispose() {
        logger.info("cache dispose.");
    }
}