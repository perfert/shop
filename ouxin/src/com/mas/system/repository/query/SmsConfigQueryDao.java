/**    
 * 文件名：SmsConfigQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2017-11-22    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.system.repository.query;

import com.mas.core.repository.query.QueryDao;
import com.mas.system.domain.entity.SmsConfig;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-22  
 */
public interface SmsConfigQueryDao extends QueryDao<SmsConfig>{
    
    /**
     * 获取默认
     * @return
     */
    SmsConfig getDefault();
}
