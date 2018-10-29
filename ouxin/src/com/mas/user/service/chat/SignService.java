/**    
 * 文件名：SignService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.chat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.domain.vo.StateVo;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Sign;
import com.mas.user.domain.entity.chat.SignMember;
import com.mas.user.repository.dao.chat.SignDao;
import com.mas.user.repository.query.chat.SignQueryDao;
import com.mas.user.service.MemberService;

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
public class SignService extends BaseServiceImpl<Sign> {
    
    @Autowired private MemberService memberService;
    @Autowired private SignMemberService signMemberService;
    
    /**
     * 获取用户所有标签
     * @param   username 用户名    
     */
    public List<Sign> getSignListByUsername(String memberId){
        return queryDao.queryAll(memberId);
    }
    
    /**
     * 有关用户好友的所有标签
     * @param   username 用户名   
     * @param   friends 好友名
     */
    public List<Sign> getSignListByUsername(String username, String friends) {
        return queryDao.getSignListByUsername(username,friends);
    }
    
    /**
     * 有关用户好友的所有标签
     * @param   userId 用户ID   
     * @param   friendId 好友ID
     */
    public List<Sign> getSignListByUserId(String userId, String friendId) {
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(friendId))
            return null;
        return queryDao.getSignListByUserId(userId,friendId);
    }

    /**
     * 保存
     * @param uId     用户ID
     * @param name    标签名
     * @param members 会员ID数组
     * @return
     */
    public Object save(String uId, String name, String[] members) {
        Member member = memberService.get(uId);
        if(null == member)
            throw new ServiceException("user.noexist");
        if(queryDao.exist(name))
            throw new ServiceException("sign.exist");
        Sign sign = new Sign();
        sign.setName(name);
        sign.setMemberId(member.getId());
        sign.setUsername(member.getUsername());
        sign.setNum(null != members ? members.length : 0);
        sign.setState(StateVo.ENABLE.value());
        Object signId = dao.persistence(sign);
        
        int count = 0;
        StringBuilder sb = new StringBuilder();
        if(null != members && members.length > 0  ){
            for (int i = 0; i < members.length; i++) {
                if(StringUtils.isNotEmpty(members[i])){
                    Member m = memberService.get(members[i]);
                    SignMember singMember = new SignMember();
                    singMember.setMemberId(m.getId());
                    singMember.setSignId(signId);
                    singMember.setUsername(m.getUsername());
                    singMember.setState(StateVo.ENABLE.value());
                    signMemberService.persistence(singMember);
                    if(count < 10)
                        sb.append("," + m.getNickName());
                    count ++;
                }
            }
            sb = new StringBuilder(sb.substring(1).toString());
        }else
            sb.append("请添加好友");
        dao.updateSign(signId,sb.toString(),count);
        return signId;
    }
    
    /**
     * 更新
     * @param signId
     * @param mid
     * @param name
     * @param listMember
     * @return
     */
    public Object update(String signId, String mid, String name, String[] listMember) {
        Sign sign = dao.get(signId);
        if(null == sign)
            throw new ServiceException("sign.noexist");
        String old = sign.getName();
        if(!name.equalsIgnoreCase(old)){
            if(queryDao.exist(name))
                throw new ServiceException("sign.exist");
        }
        
        dao.deleteSignMemberBySignId(signId);
        int count = 0;
        StringBuilder sb = new StringBuilder();
        if(null != listMember && listMember.length > 0  ){
            for (int i = 0; i < listMember.length; i++) {
                if(StringUtils.isNotEmpty(listMember[i])){
                    Member m = memberService.get(listMember[i]);
                    SignMember singMember = new SignMember();
                    singMember.setMemberId(m.getId());
                    singMember.setSignId(signId);
                    singMember.setUsername(m.getUsername());
                    singMember.setState(StateVo.ENABLE.value());
                    signMemberService.persistence(singMember);
                    if(count < 10)
                        sb.append("," + m.getNickName());
                    count ++;
                }
            }
            sb = new StringBuilder(sb.substring(1).toString());
        }else
            sb.append("friend.error.noexist");
        sign.setDetailName(sb.toString());
        sign.setName(name);
        sign.setNum(count);
        return dao.persistence(sign);
    }

    
    /**
     * 更新用户标签
     * @param   username 用户名   
     * @param   friends 好友名
     * @param   list 要保存或更新的标签,原来包含friends的标签会员对应该sign如果不list集合中,需要删除[方法重写]
     */
    public Object update(String userId, String friendId, List<Sign> list) {
        Member member = memberService.get(userId);
        if(null == member)
            throw new ServiceException("user.error.noexist");
        Member friend = memberService.get(friendId);
        if(null == friend)
            throw new ServiceException("friend.error.noexist");
        List<SignMember> ids = new ArrayList<SignMember>();
        for(Sign sign : list){
            if(null != sign.getId()){
                Sign bean = dao.get(sign.getId());
                if(null == bean)
                    throw new ServiceException("sign.noexist");
                SignMember existSignMemberId = signMemberService.exist(sign.getId(),friendId);
                if(null != existSignMemberId){
                    ids.add(existSignMemberId);
                    continue;
                }
                SignMember singMember = new SignMember();
                singMember.setMemberId(friend.getId());
                singMember.setSignId(sign.getId());
                singMember.setUsername(friend.getUsername());
                singMember.setAlias(friend.getNickName());
                singMember.setNickName(friend.getNickName());
                singMember.setAvatar(friend.getAvatar());
                singMember.setState(StateVo.ENABLE.value());
                Object signMemberId = signMemberService.persistence(singMember);
                
                singMember.setId(signMemberId);
                ids.add(singMember);
                
                String detailName = bean.getDetailName();
                if(null == detailName){
                    detailName = friend.getNickName();
                }else if(bean.getNum() < 10){
                    detailName = detailName + "," + friend.getNickName();
                }
                dao.updateSign(sign.getId(),detailName,bean.getNum() + 1);
            }else{
                if(queryDao.exist(sign.getName()))
                    throw new ServiceException("sign.exist");
                Sign bean = new Sign();
                bean.setName(sign.getName());
                bean.setMemberId(member.getId());
                bean.setUsername(member.getUsername());
                bean.setDetailName(friend.getNickName());
                bean.setNum(1);
                bean.setState(StateVo.ENABLE.value());
                Object signId = dao.persistence(bean);
                
                SignMember singMember = new SignMember();
                singMember.setMemberId(friend.getId());
                singMember.setSignId(signId);
                singMember.setUsername(friend.getUsername());
                singMember.setAlias(friend.getNickName());
                singMember.setNickName(friend.getNickName());
                singMember.setAvatar(friend.getAvatar());
                singMember.setState(StateVo.ENABLE.value());
                Object signMemberId = signMemberService.persistence(singMember);
                
                singMember.setId(signMemberId);
                ids.add(singMember);
            }
        }
        
        
        List<SignMember> allIds = signMemberService.getList(userId,friendId);
        for(SignMember o : allIds){
            boolean isDelete = true;
            for(SignMember exist:ids){
                if(o.getId().toString().equalsIgnoreCase(exist.getId().toString())){
                    isDelete = false;
                    break;
                }
            }
            if(isDelete){
                Sign sign = get(o.getSignId());
                String detailName = sign.getDetailName();
                if(detailName.contains(friend.getNickName())){
                    int length = detailName.length();
                    detailName = detailName.replace("," + friend.getNickName(), "");
                    if(detailName.length() == length)
                        detailName = detailName.replace(friend.getNickName(), "");
                }
                dao.updateSign(sign.getId(),detailName,sign.getNum() - 1);
                signMemberService.delete(o.getId());
            }
                
        }
        return ids;
    }

    /**
     * 删除
     * 
     * @param signId 标签ID。
     * @param signId 用户ID
     */
    public boolean delete(String signId, String userId) {
        return dao.delete(signId,userId);
    }
    
    @Override
    protected CrudDao<Sign> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<Sign> queryDao()
    {
        return queryDao;
    }
    @Autowired private SignDao dao;
    @Autowired private SignQueryDao queryDao;

    
    
    

    

    
}
