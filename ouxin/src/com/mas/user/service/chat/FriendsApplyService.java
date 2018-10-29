/**    
 * 文件名：FriendsApplyService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.chat;

import io.rong.messages.CmdMsgMessage;
import io.rong.service.MessageService;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Friends;
import com.mas.user.domain.entity.chat.FriendsApply;
import com.mas.user.domain.entity.chat.FriendsBlack;
import com.mas.user.repository.dao.chat.FriendsApplyDao;
import com.mas.user.repository.query.chat.FriendsApplyQueryDao;
import com.mas.user.service.MemberService;

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
public class FriendsApplyService extends BaseServiceImpl<FriendsApply> {

    @Autowired private MessageService messageService;
    
    /**
     * 获取申请列表[包括被申请人的头像和昵称]
     * @param userId 用户ID
     * @return
     */
    public List<FriendsApply> getListByUserId(String userId) {
        if(StringUtils.isEmpty(userId))
            throw new ServiceException("user.noexist");
        return queryDao.getListByUserId(userId);
    }
    
    
    /**
     * 好友申请
     * 
     * @param applyId 申请ID
     * @param receiveId 申请好友ID
     * @param reason 申请原因
     * @return String / null.
     */
    public Object create(String applyId, String receiveId, String reason) {
        Member member = memberService.get(applyId);
        if(null == member)
            throw new ServiceException("user.noexist");
        Member target = memberService.get(receiveId);
        if(null == target)
            throw new ServiceException("user.noexist");
        FriendsBlack black = friendsBlackService.getFriendsBlack(member.getId(),target.getId());
        if(null != black && black.getType() == 1)
            throw new ServiceException("申请账户已被拉黑！");
        Friends friends = friendsService.getFriends(member.getId(),target.getId());
        if(null != friends)
            throw new ServiceException("申请账户已是对方好友！");
        FriendsApply bean = queryDao.get(applyId,receiveId);
        if(null != bean ){
            bean.setMsg(reason);
            bean.setType(FriendsApply.TYPE_ADD_FRIEND);
        }else{
            bean = new FriendsApply();
            bean.setApplyId(member.getId());
            bean.setReceiveId(target.getId());
            bean.setApplyAccount(member.getUsername());
            bean.setReceiveAccount(target.getUsername());
            bean.setMsg(reason);
            bean.setType(FriendsApply.TYPE_ADD_FRIEND);
            bean.setId( null );
            bean.setState(1);
        }
        Object object = super.persistence(bean);
        
        //通知失败不影响操作
        try {
            JSONObject json = new JSONObject();
            json.put("operation", CmdMsgMessage.CONTACT_OPERATION_REQUEST);
            messageService.publishCmdMsgMessageFriend(applyId, receiveId, CmdMsgMessage.CMD_MESSAGE_TYPE,json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
    
    /**
     * 好友申请拒绝
     * 
     * @param apply 申请账号
     * @param receive 申请好友账号
     * 
     * @return String / null.
     */
    public Object refuse(String apply, String receive) {
       return dao.updateType(apply, receive,FriendsApply.TYPE_REFUSE_FRIEND);
    }
    
    /**
     * 好友申请同意
     * 
     * @param agreeId 同意人ID
     * @param friendId 申请人ID
     * 
     * @return Member.
     */
    public Member accept(String agreeId, String friendId) {
        Member member = memberService.get(agreeId);
        Member friendsMember = memberService.get(friendId);
        if(null == member)
            throw new ServiceException("user.noexist");
        if(null == friendsMember)
            throw new ServiceException("user.noexist");
        //推送那边已经处理了,如果加了好友,会取消申请
        if(friendsService.exist(friendId, agreeId)) {
            dao.updateType(friendId,agreeId,FriendsApply.TYPE_AGREE_FRIEND);
            dao.updateType(agreeId,friendId,FriendsApply.TYPE_AGREE_FRIEND);
            return friendsMember;
        }
             
        Friends friends = new Friends();
        friends.setMemberId(member.getId());
        friends.setFriendId(friendsMember.getId());
        friends.setMemberAccount(member.getUsername());
        friends.setFriendAccount(friendsMember.getUsername());
        friends.setState(1);
        
        Friends friends2 = new Friends();
        friends2.setMemberId(friendsMember.getId());
        friends2.setFriendId(member.getId());
        friends2.setMemberAccount(friendsMember.getUsername());
        friends2.setFriendAccount(member.getUsername());
        friends2.setState(1);
        friendsService.persistence(friends);
        friendsService.persistence(friends2);
        //这里同意其实是被申请人
        dao.updateType(friendId,agreeId,FriendsApply.TYPE_AGREE_FRIEND);
        dao.updateType(agreeId,friendId,FriendsApply.TYPE_AGREE_FRIEND);
        //通知失败影响操作,因为用了缓存要更新好友
        try {
            JSONObject json = new JSONObject();
            json.put("operation", CmdMsgMessage.CONTACT_OPERATION_ACCEPT_RESPONSE);
            json.put("message", "");
            json.put("sendId", member.getId());
            json.put("sendPortraitUri", member.getAvatar());
            json.put("sendNickname", member.getNickName());
            messageService.publishCmdMsgMessageFriend(agreeId, friendId,CmdMsgMessage.CMD_MESSAGE_TYPE, json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("apply.agree.fail");
        }
        return friendsMember;
    }
    
    public void handlerApply(FriendsApply bean){
        
    }
    
    @Override
    protected CrudDao<FriendsApply> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<FriendsApply> queryDao()
    {
        return queryDao;
    }
    @Autowired private FriendsApplyDao dao;
    @Autowired private FriendsApplyQueryDao queryDao;
    @Autowired private MemberService memberService;
    @Autowired private FriendsBlackService friendsBlackService;
    @Autowired private FriendsService friendsService;

    public FriendsApply get(String friendId, String userId) {
        return queryDao.get(friendId, userId);
    }
    
}
