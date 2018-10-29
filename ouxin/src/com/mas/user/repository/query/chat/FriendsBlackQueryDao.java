/**    
 * 文件名：FriendsBlackQueryDao.java    
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
import com.mas.user.domain.entity.chat.FriendsBlack;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface FriendsBlackQueryDao extends QueryDao<FriendsBlack>{
    
    /**
     * 获取所有好友申请表。
     * 
     * @param uId 用户ID。
     * 
     * @return {@link List} or null
     */
    List<FriendsBlack> queryAll( Object uId );


    FriendsBlack getFriendsBlack(Object applyId, Object targetId);
}