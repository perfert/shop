/**    
 * 文件名：FriendsService.java    
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
import com.mas.user.repository.dao.chat.FriendsDao;
import com.mas.user.repository.query.chat.FriendsQueryDao;
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
public class FriendsService extends BaseServiceImpl<Friends> {

    @Autowired private MessageService messageService;
    @Autowired private FriendsApplyService friendsApplyService;
    
    public Friends getFriends(Object applyId, Object targetId) {
        return queryDao.getFriends(applyId,targetId);
    }
    

    /**
     * 获取好友
     * 
     * @param username 我的账号
     * @param friend 好友账号
     * 
     * @return boolean.
     */
    public Friends getFriendsByUsername(String username, String friend) {
        return queryDao.getFriendsByUsername(username,friend);
    }
    
    /**
     * 获取好友
     * 
     * @param userId 我的ID
     * @param friendId 好友ID
     * 
     * @return boolean.
     */
    public Friends getFriendsByUserId(String userId, String friendId) {
        return queryDao.getFriendsByUserId(userId,friendId);
    }
    
    /**
     * 更新好友备注
     * 
     * @param username 我的账号
     * @param friend 好友账号
     * @param alias 别名
     * @param phone 电话
     * @param detail 详细
     * @param img 好友图
     *      * 
     * @return boolean.
     */
    public boolean updateInfo(String username, String friends, String alias, String phone, String detail, String img) {
        Friends myFriend = getFriendsByUsername(username,friends);
        if(null == myFriend)
            throw new ServiceException("friend.error.noexist");
        if(StringUtils.isNotEmpty(username))
            myFriend.setAlias(alias);
        if(StringUtils.isNotEmpty(phone))
            myFriend.setPhone(phone);
        if(StringUtils.isNotEmpty(detail))
            myFriend.setDetail(detail);
        if(StringUtils.isNotEmpty(img))
            myFriend.setImg(img);
        Object ojbect = dao.persistence(myFriend);
        return null != ojbect ? true : false;
    }
    
    /**
     * 更新好友备注
     * @param mid
     * @param friendId
     * @param alias 别名
     * @param phone 电话
     * @param detail 详细
     * @param img 好友图
     * @return
     */
    public boolean updateFriendInfo(String mid, String friendId, String alias, String phone, String detail,String image) {
        // TODO Auto-generated method stub
        
        Friends myFriend = getFriendsByUserId(mid, friendId);
        if(null == myFriend)
            throw new ServiceException("user.error.noexist");
        if(StringUtils.isNotEmpty(alias))
            myFriend.setAlias(alias);
        if(StringUtils.isNotEmpty(phone))
            myFriend.setPhone(phone);
        if(StringUtils.isNotEmpty(detail))
            myFriend.setDetail(detail);
        if(StringUtils.isNotEmpty(image))
            myFriend.setImg(image);
        Object ojbect = dao.persistence(myFriend);
        return null != ojbect ? true : false;
    }
  
    
    /**
     * 好友是否存在
     * 
     * @param userId 申请ID
     * @param friendId 申请好友ID
     * 
     * @return boolean.
     */
    public boolean exist(String userId, String friendId) {
        return queryDao.exist(userId,friendId);
    }
    
    /**
     * 好友删除
     * 
     * @param userName 申请账号
     * @param friendName 申请好友账号
     * 
     * @return boolean.
     */
    public boolean delete(String userId, String friendId) {
        boolean success = false;
        if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(friendId)){
            Member member = memberService.get(userId);
            if(null == member)
                throw new ServiceException("user.noexist");
            Member target = memberService.get(friendId);
            if(null == target)
                throw new ServiceException("user.noexist");
            success = dao.deleteFriends(userId,friendId);
            try {
                if(success){
                    //告诉好友已删除
                    FriendsApply bean = friendsApplyService.get(userId,friendId);
                    if(null == bean){
                        bean = new FriendsApply();
                        bean.setApplyId(userId);
                        bean.setReceiveId(friendId);
                        bean.setApplyAccount(member.getAccount());
                        bean.setReceiveAccount(target.getAccount());
                        bean.setId( null );
                        bean.setState(1);
                    }
                    bean.setType(FriendsApply.TYPE_DELETE_FRIEND);
                    friendsApplyService.persistence(bean);
                    
                    JSONObject json = new JSONObject();
                    json.put("sendId", member.getId());
                    json.put("operation", CmdMsgMessage.CONTACT_OPERATION_DELETE_RESPONSE);
                    messageService.publishCmdMsgMessageFriend(userId, friendId,CmdMsgMessage.CMD_MESSAGE_TYPE, json.toString());
                }
            } catch (Exception e) {
                throw new ServiceException("delete.error");
            }
        }
        return success;
    }
    
    @Override
    public Object persistence( Friends bean ){
        //@Todo
        Member member = memberService.get(bean.getMemberId());
        if(null == member)
            throw new ServiceException("user.error.noexist");
        Member target = memberService.get(bean.getFriendId());
        if(null == target)
            throw new ServiceException("friend.error.noexist");
        bean.setMemberId(member.getId());
        bean.setFriendId(target.getId());
        return super.persistence(bean);
    }

    
    /**
     * 获取用户的好友列表 <br>
     * @param userName
     *            用戶名
     * @return
     */
    public List<Member> getFriendsList(String userName) {
        return queryDao.getFriendsList(userName);
    }
    
    /**
     * 获取用户的好友列表[包括别名]
     * @param mid 用户ID
     * @return
     */
    public List<Member> getFriendsListByUserId(String mid) {
        return queryDao.getFriendsListByUserId(mid);
    }
    
    public List<Member> getFriendsList2(String userName) {
        return queryDao.getFriendsList2(userName);
    }
    
    
    
    
    @Override
    protected CrudDao<Friends> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<Friends> queryDao()
    {
        return queryDao;
    }
    
    @Autowired
    private MemberService memberService;
    @Autowired private FriendsDao dao;
    @Autowired private FriendsQueryDao queryDao;
    
}
