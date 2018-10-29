/**    
 * 文件名：GroupMemberService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.chat;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Group;
import com.mas.user.domain.entity.chat.GroupMember;
import com.mas.user.repository.dao.chat.GroupMemberDao;
import com.mas.user.repository.query.chat.GroupMemberQueryDao;

/**
 * 
 * 项目名称：chat 
 * 类名称： 
 * 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class GroupMemberService extends BaseServiceImpl<GroupMember> {

    /**
     * 删除组成员(前台传过的删除成员没有判断是否少于3人)
     * 
     * @param groupId 组ID。
     * @param userName 群组成员
     */
    public boolean deleteGroupMember(Object groupId,String userName) {
        Group group = groupService.get(groupId);
        groupService.updateGroupCount(group.getId(),group.getNowCount() - 1);
        return dao.deleteGroupMember(groupId,userName);
    }
    
    /**
     * 删除群人员
     * @param groupId
     * @param memberIds
     * @return
     */
    public boolean deleteGroupMember(Object groupId, String[] memberIds) {
        Group group = groupService.get(groupId);
        if(null == group)
            throw new ServiceException("group.error.noexist");
        return dao.deleteGroupMember(groupId,memberIds);
    }
    
    /**
     * 后台删除组成员
     * 
     * @param id 组与会员关联表的ID。
     */
    @Override
    public boolean delete( Object id ){
        GroupMember groupMember = get(id);
        Group group = groupService.get(groupMember.getGroupId());
        Long count = queryDao.getCounts(group.getId());
        if(count - 1 < 3)
            throw new ServiceException("group.error.member.num");
        groupService.updateGroupCount(group.getId(),group.getNowCount() - 1);
        return super.delete(id);
    }
    
    /**
     * 获取组所有会员
     * @param groupId 组ID
     * @return
     */
    public List<Member> queryMembers(Object groupId) {
        return queryDao.queryMembers(groupId);
    }
    
    /**
     * 是否群组会员
     * @param memberId
     * @param groupId
     * @return
     */
    public boolean exist(String memberId, String groupId) {
        return queryDao.exist(memberId,groupId);
    }
    
    @Override
    protected CrudDao<GroupMember> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<GroupMember> queryDao()
    {
        return queryDao;
    }
    @Autowired private GroupMemberDao dao;
    @Autowired private GroupMemberQueryDao queryDao;
    @Autowired private GroupService groupService;
    
   
}
