/**    
 * 文件名：GroupService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.chat;

import io.rong.messages.GrpNtfMessage;
import io.rong.models.CodeSuccessResult;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.domain.vo.StateVo;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Group;
import com.mas.user.domain.entity.chat.GroupMember;
import com.mas.user.repository.dao.chat.GroupDao;
import com.mas.user.repository.query.chat.GroupQueryDao;
import com.mas.user.service.MemberService;
import com.mas.user.service.image.ImageService;
import com.mas.web.member.controller.JsonCtr;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class GroupService extends BaseServiceImpl<Group> {
    
    @Autowired private io.rong.service.MessageService messageService;
    @Autowired private io.rong.service.GroupService groupService;
    @Autowired private ImageService imageService;
    /**
     * 获取所有加入的群
     * @param userId 会员ID
     * @return
     */
    public List<Group> getListByUserId(String userId) {
        if(StringUtils.isEmpty(userId))
            return null;
        return queryDao.getListByUserId(userId);
    }

    /**
     * 获取群组成员数
     * @param groupId 群ID
     * @return
     */
    public Long count(Object groupId) {
        if(null == groupId)
            return 0L;
        return queryDao.count(groupId);
    }
    
    /**
     * 获取组所有会员
     * @param groupId
     * @return
     */
    public List<Member> getMembersByGroupId(String groupId) {
        if(StringUtils.isEmpty(groupId))
            return null;
        return groupMemberService.queryMembers(groupId);
    }
   

    /**
     * 根据第三方推方组ID，获取所有会员数据对象。
     * 
     * @param pushGroupId 第三方推方组ID。
     * 
     * @return {@link E} or null。
     */
    public List<Member> membersByPushGroupId(String pushGroupId) {
        Group group = queryDao.queryByPushGroupId(pushGroupId);
        if(null == group)
            return null;
        return groupMemberService.queryMembers(group.getId());
    }
    
    /**
     * 创建群组[处理推送错误]
     * @param name     组名
     * @param createId 创建者ID
     * @param memberIds 组员
     * @return
     */
    public Group create(String name, String createId, String[] memberIds) {
        Member member = memberService.get(createId);
        if(null == member)
            throw new ServiceException("user.noexist");
        
        Group group = new Group(name, null, true, true, 200L, memberIds.length, member.getId(), member.getUsername());
        group.setState(StateVo.ENABLE.value());
        Object groupId = dao.persistence(group);
        
        List<String> images = new ArrayList<String>();
        for (int i = 0; i < memberIds.length; i++) {
            if(StringUtils.isNotEmpty(memberIds[i])){
                Member m = memberService.get(memberIds[i]);
                GroupMember groupMember = new GroupMember();
                groupMember.setGroupId(groupId);
                groupMember.setMemberId(m.getId());
                groupMember.setMemberAccount(m.getUsername());
                groupMember.setState(StateVo.ENABLE.value());
                groupMemberService.persistence(groupMember);
                if(images.size() < 9)
                    images.add(m.getAvatar());
            }
        }
        //处理群头像
        String outPath = imageService.uploadGroupAvatar(images);
        dao.updateAvatar(outPath,groupId);
        
        try {
            CodeSuccessResult result = groupService.create(memberIds, groupId.toString(), group.getName());
            if(result.getCode() != 200)
                throw new ServiceException("group.error.create");
            
            JSONObject json = new JSONObject();
            json.put("operatorNickname", member.getNickName());
            json.put("targetGroupName", group.getName());
            String data = json.toString();
            CodeSuccessResult result2 = messageService.publishGrpNtfMessage(new String[]{groupId.toString()}, createId, GrpNtfMessage.Create, data, "创建群组", "");
            if(result2.getCode() != 200)
                throw new ServiceException("group.error.create");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("group.error.create");
        }
        return dao.get(groupId);
    }

    /**
     * 添加组成员
     * @param mid
     * @param groupId
     * @param memberIds
     * @return
     */
    public Group addMember(String mid, String groupId, String[] memberIds) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.noexist");
        Group group = dao.get(groupId);
        if(null == group)
            throw new ServiceException("group.noexist");
        
        int nowCount = 0;
        for (int i = 0; i < memberIds.length; i++) {
            if(StringUtils.isNotEmpty(memberIds[i])){
                //@TODO未判断是否重复会员(判断IDS 是否IN)
                Member m = memberService.get(memberIds[i]);
                GroupMember groupMember = new GroupMember();
                groupMember.setGroupId(group.getId());
                groupMember.setMemberId(m.getId());
                groupMember.setState(StateVo.ENABLE.value());
                groupMember.setMemberAccount(m.getUsername());
                groupMemberService.persistence(groupMember);
                nowCount = nowCount + 1;
            }
        }
        group.setNowCount(group.getNowCount() + nowCount);
        dao.persistence(group);
        
        //处理群组头像(是否冗余字段保存会员组)
        String outPath = null;
        if(group.getNowCount() < 9){
            List<String> images = queryDao.getGroupMemberImages(groupId,9);
            //处理群头像
            outPath = imageService.updateGroupAvatar(images,groupId,group.getAvatar());
            dao.updateAvatar(outPath,groupId);
        }
        try {
            CodeSuccessResult result = groupService.join(memberIds, groupId, group.getName());
            if(result.getCode() != 200)
                throw new ServiceException("group.error.join");
            
            
            JSONObject json = new JSONObject();
            json.put("operatorNickname", member.getNickName());
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArrayNick = new JSONArray();
            for(int i = 0;i<memberIds.length;i++){
                Member kick = memberService.get(memberIds[i]);
                jsonArray.put(i,kick.getId());
                jsonArrayNick.put(i,kick.getNickName());
            }
            if(StringUtils.isNotEmpty(outPath))
                json.put(Member.JSON_KEY_AVATAR, JsonCtr.HOST + outPath);
            json.put("targetUserIds", jsonArray);
            json.put("targetUserDisplayNames", jsonArrayNick);
            String data = json.toString();
            CodeSuccessResult result2 = messageService.publishGrpNtfMessage(new String[]{groupId.toString()}, mid, GrpNtfMessage.Add, data, "添加群成员", "");
            if(result2.getCode() != 200)
                throw new ServiceException("group.error.join");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("group.error.join");
        }
        return group;
    }
    
    
    /**
     * 修改组名称 [不处理推送错误]
     * @param mid
     * @param groupId 组ID
     * @param name
     * @return
     */
    public Object modify(String mid, String groupId, String name) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.noexist");
        Group group = dao.get(groupId);
        if(null == group)
            throw new ServiceException("group.noexist");
        group.setName(name);
        Object object = dao.persistence(group);
        try {
            CodeSuccessResult result = groupService.refresh(groupId, name);
            if(result.getCode() != 200)
                throw new ServiceException("group.error.name");
            
            JSONObject json = new JSONObject();
            json.put("operatorNickname", member.getNickName());
            json.put("targetGroupName", group.getName());
            String data = json.toString();
            CodeSuccessResult result2 = messageService.publishGrpNtfMessage(new String[]{groupId.toString()}, mid, GrpNtfMessage.Rename, data, "修改群名称", "");
            if(result2.getCode() != 200)
                throw new ServiceException("group.error.name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
    
    /**
     * 删除群成员(只有群主有权利) [不处理推送错误]
     * @param mid
     * @param groupId
     * @param memberIds
     * @return
     */
    public Group deleteMember(String mid, String groupId, String[] memberIds) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.noexist");
        Group group = dao.get(groupId);
        if(null == group)
            throw new ServiceException("group.error.noexist");
        if(!group.getOwner().toString().equalsIgnoreCase(mid))
            throw new ServiceException("group.error.security");
            
        //删除前的会员
        List<Member> tops = queryDao.getGroupMember(groupId, 9);
        boolean success = groupMemberService.deleteGroupMember(group.getId(), memberIds);
        if(!success)
            return null;
        try {
            CodeSuccessResult result = groupService.quit(memberIds, groupId);
            if(result.getCode() != 200)
                throw new ServiceException("group.error.member");
            //处理群组头像(是否冗余字段保存会员组)
            String outPath = null;
            if(group.getNowCount() < 9){
                boolean contain = false;
                for(Member uMember :tops){
                    for(String id : memberIds){
                        if(uMember.getId().toString().equals(id)){
                            contain = true;
                            break;
                        }
                    }
                }
                if(contain){
                    List<String> images = queryDao.getGroupMemberImages(groupId,9);
                    //处理群头像
                    outPath = imageService.uploadGroupAvatar(images);
                    dao.updateAvatar(outPath,groupId);
                }
            }
            
            JSONObject json = new JSONObject();
            json.put("operatorNickname", member.getNickName());
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArrayNick = new JSONArray();
            for(int i = 0;i<memberIds.length;i++){
                Member kick = memberService.get(memberIds[i]);
                jsonArray.put(i,kick.getId());
                jsonArrayNick.put(i,kick.getNickName());
            }
            if(StringUtils.isNotEmpty(outPath))
                json.put(Member.JSON_KEY_AVATAR, JsonCtr.HOST + outPath);
            json.put("targetUserIds", jsonArray);
            json.put("targetUserDisplayNames", jsonArrayNick);
            String data = json.toString();
            CodeSuccessResult result2 = messageService.publishGrpNtfMessage(new String[]{groupId.toString()}, mid, GrpNtfMessage.Kicked, data, "移出群成员", "");
            if(result2.getCode() != 200)
                throw new ServiceException("group.error.member");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("group.error.member");
        }
        return group;
    }

    /**
     * 退群
     * @param mid     退群人员
     * @param groupId  群ID
     * @return
     */
    public boolean deleteMember(String mid, String groupId) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.noexist");
        Group group = dao.get(groupId);
        if(null == group)
            throw new ServiceException("group.error.noexist");
        //如果是群主退群
        if(group.getOwner().toString().equalsIgnoreCase(mid))
            return delete(mid,groupId);
        
        //删除前的会员
        List<Member> tops = queryDao.getGroupMember(groupId, 9);
        boolean success = groupMemberService.deleteGroupMember(group.getId(), new String[]{mid});
        if(!success)
            return success;
        try {
            CodeSuccessResult result = groupService.quit(new String[]{mid}, groupId);
            if(result.getCode() != 200)
                throw new ServiceException("group.error.quit");
            
            //处理群组头像(是否冗余字段保存会员组)
            String outPath = null;
            if(group.getNowCount() < 9){
                boolean contain = false;
                for(Member uMember :tops){
                    if(uMember.getId().toString().equals(mid)){
                        contain = true;
                        break;
                    }
                }
                if(contain){
                    List<String> images = queryDao.getGroupMemberImages(groupId,9);
                    //处理群头像
                    outPath = imageService.uploadGroupAvatar(images);
                    dao.updateAvatar(outPath,groupId);
                }
            }
            
            JSONObject json = new JSONObject();
            json.put("operatorNickname", member.getNickName());
            //newCreatorId 如退出群的用户为群创建者则 newCreatorId 为新的群创建者 ID ，否则为 null
            json.put("newCreatorId", "");
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArrayNick = new JSONArray();
            jsonArray.put(0,member.getId());
            jsonArrayNick.put(0,member.getNickName());
            if(StringUtils.isNotEmpty(outPath))
                json.put(Member.JSON_KEY_AVATAR, JsonCtr.HOST + outPath);
            json.put("targetUserIds", jsonArray);
            json.put("targetUserDisplayNames", jsonArrayNick);
            String data = json.toString();
            CodeSuccessResult result2 = messageService.publishGrpNtfMessage(new String[]{groupId.toString()}, mid, GrpNtfMessage.Quit, data, "退出群组", "");
            if(result2.getCode() != 200)
                throw new ServiceException("group.error.quit");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("group.error.quit");
        }
        return success;
    }
    
    
    /**
     * 删除组[处理推送错误]
     * @param mid
     * @param groupId
     * @return
     */
    public boolean delete(String mid, String groupId) {
        Member member = memberService.get(mid);
        if(null == member)
            throw new ServiceException("user.noexist");
        Group group = dao.get(groupId);
        if(null == group)
            throw new ServiceException("group.error.noexist");
        
        if(group.getOwner().toString().equalsIgnoreCase(mid)){
            boolean success = super.delete(groupId);
            if(!success)
                return success;
            try {
                CodeSuccessResult result = groupService.dismiss(mid, groupId);
                if(result.getCode() != 200)
                    throw new ServiceException("group.error.delete");
                
                JSONObject json = new JSONObject();
                json.put("operatorNickname", member.getNickName());
                json.put("targetGroupName", group.getName());
                String data = json.toString();
                CodeSuccessResult result2 = messageService.publishGrpNtfMessage(new String[]{groupId.toString()}, mid, GrpNtfMessage.Dismiss, data, "解散群组", "");
                if(result2.getCode() != 200)
                    throw new ServiceException("group.error.delete");
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("group.error.delete");
            }
            return success;
        }
        //无权限删除
        return false;
    }
    
    
    /**
     * 更新组人数
     * 
     * @param groupId 组ID。
     * @param count 人数
     */
    public boolean updateGroupCount(Object groupId, int count) {
        return dao.updateGroupCount(groupId,count);
    }
    
    
    @Override
    protected CrudDao<Group> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<Group> queryDao()
    {
        return queryDao;
    }
    @Autowired private GroupDao dao;
    @Autowired private GroupQueryDao queryDao;
    @Autowired private MemberService memberService;
    @Autowired private GroupMemberService groupMemberService;
    


   
    
    
}
