/**    
 * 文件名：FriendsApplyQueryDao.java    
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
import com.mas.user.domain.entity.chat.FriendsApply;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface FriendsApplyQueryDao extends QueryDao<FriendsApply>{
    
    /**
     * 获取所有好友申请表。
     * 
     * @param uId 用户ID。
     * 
     * @return {@link List} or null
     */
    List<FriendsApply> queryAll( Object uId );

    /**
     * 获取所有好友申请表。
     * 
     * @param applyId 用户ID。
     * 
     * @param receiveId 好友ID。
     * 
     * @return {@link List} or null
     */
    FriendsApply get(String applyId, String receiveId);

    /**
     * 获取跟我有关的好友申请
     * @param userId
     * @return
     */
    List<FriendsApply> getListByUserId(String userId);

}