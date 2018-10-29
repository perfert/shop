/**    
 * 文件名：SignQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.chat;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.chat.Sign;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface SignQueryDao extends QueryDao<Sign>{
    
    /**
     * 
     * @param query 检索对象。
     * 
     * @return {@link List} or null
     */
    List<Sign> queryAll( String username );

    boolean exist(String name);

    List<Sign> getSignListByUsername(String username, String friends);

    List<Sign> getSignListByUserId(String userId, String friendId);
}