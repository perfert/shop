/**    
 * 文件名：GroupMemberQueryDao.java    
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
import com.mas.user.domain.entity.chat.GroupMember;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface GroupMemberQueryDao extends QueryDao<GroupMember>{
    
    /**
     * 
     * @param query 检索对象。
     * 
     * @return {@link List} or null
     */
    List<GroupMember> queryAll( Object uId );

    /**
     * 获取所有与groupId相关的会员
     * @param groupId 组ID。 
     */
    List<Member> queryMembers(Object groupId);

    Long getCounts(Object groupId);

    boolean exist(String memberId, String groupId);
}