/**    
 * 文件名：FriendsQueryDao.java    
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
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Friends;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface FriendsQueryDao extends QueryDao<Friends>{
    
    List<Friends> queryAll( Object uId );

    Friends getFriends(Object applyId, Object targetId);

    List<Member> getFriendsList(String userName);
    
    List<Member> getFriendsList2(String userName);

    boolean exist(String userId, String friendId);

    Friends getFriendsByUsername(String username, String friend);

    Friends getFriendsByUserId(String userId, String friendId);

    List<Member> getFriendsListByUserId(String mid);
}