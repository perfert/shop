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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.chat.SignMember;
import com.mas.user.repository.dao.chat.SignMemberDao;
import com.mas.user.repository.query.chat.SignMemberQueryDao;

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
public class SignMemberService extends BaseServiceImpl<SignMember> {

    /**
     * 获取所有标签会员[包括别名]
     * @param signId
     * @return
     */
    public List<SignMember> getSignMemberBySignId(Object signId){
        return queryDao.queryAll(signId);
    }
    
    /**
     * 获取所有的SignMember
     * 
     * @param username 用户名
     * @param friends 好友名
     * 
     * @return boolean
     */
    public List<SignMember> getList(String userId, String friendId) {
        return queryDao.queryAll(userId,friendId);
    }
    
    /**
     * SignMember是否存在
     * 
     * @param signId 标签ID。
     * @param friendId 用户ID。
     * 
     * @return boolean
     */
    public SignMember exist(Object signId, String friendId) {
        return queryDao.exist(signId,friendId);
    }
    
    public boolean deleteExcluded(List<Object> ids,Object username, Object friends) {
        return dao.deleteExcluded(ids,username,friends);
    }
    
    
    @Override
    protected CrudDao<SignMember> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<SignMember> queryDao()
    {
        return queryDao;
    }
    @Autowired private SignMemberDao dao;
    @Autowired private SignMemberQueryDao queryDao;
    
   
    
    
}
