/**    
 * 文件名：CustomerCacheEventListenerFactory.java    
 *    
 * 版本信息：    
 * 日期：2018-5-9    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.web.listener;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

import java.util.Properties;

/**
 * 类名称： 创建人：yixuan
 * 
 * @version v1.00 2018-5-9
 */
public class CustomerCacheEventListenerFactory extends CacheEventListenerFactory {
    @Override
    public CacheEventListener createCacheEventListener(Properties properties) {
        return new CustomerCacheEventListener();
    }
}