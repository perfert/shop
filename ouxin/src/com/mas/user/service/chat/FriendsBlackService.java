/**    
 * 文件名：FriendsBlackService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.FriendsBlack;
import com.mas.user.repository.dao.chat.FriendsBlackDao;
import com.mas.user.repository.query.chat.FriendsBlackQueryDao;
import com.mas.user.service.MemberService;

/**
 * 
 * 项目名称：chat 
 * 类名称： 黑名单Service
 * 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class FriendsBlackService extends BaseServiceImpl<FriendsBlack> {

    /**
     * 持久化数据对象。(保存的对象必须是好友,黑名单中的用户无法给该 IM 用户发送消息)
     * 
     * @param bean 数据对象。
     * 
     * @return 持久化数据的数据ID。
     * 
     * @throws ServiceException 
     */
    @Override
    public Object persistence( FriendsBlack bean ){
        Object  object = null;
        /*Member member = memberService.getByAccount(bean.getMemberAccount());
        if(null == member)
            throw new ServiceException("申请账户不存在！");
        Member target = memberService.getByAccount(bean.getTargetAccount());
        if(null == target)
            throw new ServiceException("好友账户不存在！");
        bean.setMemberId(member.getId());
        bean.setTargetId(target.getId());
        object = super.persistence(bean);
        if(bean.getType() != 0){
            //pushService
        }else{
            //pushService
        }*/
        return object;
    }
    
    /**
     * 根据ID，物理删除数据对象。(解除黑名单)
     * 
     * @param id 数据对象ID。
     * 
     * @return true or false。
     */
    @Override
    public boolean delete( Object id ){
      //pushService
        return super.delete(id);
    }
    
    
    /**
     * 获取applyId是否给targetId拉黑
     */
    public FriendsBlack getFriendsBlack(Object applyId,Object targetId) {
        return queryDao.getFriendsBlack(applyId,targetId);
    }
    
    @Override
    protected CrudDao<FriendsBlack> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<FriendsBlack> queryDao()
    {
        return queryDao;
    }
    
    @Autowired private MemberService memberService;
    @Autowired private FriendsBlackDao dao;
    @Autowired private FriendsBlackQueryDao queryDao;
    
}
