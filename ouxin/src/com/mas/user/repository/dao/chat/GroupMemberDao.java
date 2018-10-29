/**    
 * 文件名：GroupMemberDao.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.chat.GroupMember;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface GroupMemberDao extends CrudDao<GroupMember>{

    boolean deleteGroupMember(Object groupId,String userName);

    boolean deleteGroupMember(Object groupId, String[] memberIds);

}
