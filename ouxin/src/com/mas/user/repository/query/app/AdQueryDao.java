/**    
 * 文件名：AdQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.app;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.app.Ad;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    AdQueryDao
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface AdQueryDao extends QueryDao<Ad>{
    
    /**
     * 
     * @param query 检索对象。
     * 
     * @return {@link List} or null
     */
    List<Ad> queryAll( Object uId );

    List<Ad> queryAdListByPositionId(Object positionId);


}