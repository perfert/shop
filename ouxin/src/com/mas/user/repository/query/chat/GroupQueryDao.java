/**    
 * 文件名：GroupQueryDao.java    
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
import com.mas.user.domain.entity.chat.Group;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface GroupQueryDao extends QueryDao<Group>{
    
    /**
     * 
     * @param query 检索对象。
     * 
     * @return {@link List} or null
     */
    List<Group> queryAll( Object uId );

    /**
     * 获取组
     * @param pushGroupId 推送第三方组ID。
     * 
     */
    Group queryByPushGroupId(String pushGroupId);

    List<Group> getListByUserId(String userId);

    Long count(Object groupId);

    /**
     * 获取群组会员头像数组
     * @param groupId 组ID
     * @param size    会员个数
     * @return
     */
    List<String> getGroupMemberImages(String groupId, int size);

    /**
     * 获取群组会员数组
     * @param groupId 组ID
     * @param size    会员个数
     * @return
     */
    List<Member> getGroupMember(String groupId, int i);

    /**
     * 删除的会员组是否在前size个前面[群组创建时间为准]
     * @param groupId
     * @param memberIds
     * @param size
     * @return
     */
    boolean deleteContain(String groupId, String[] memberIds, int i);

    
}