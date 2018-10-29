/**    
 * 文件名：MsgService.java    
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
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.chat.Msg;
import com.mas.user.repository.dao.chat.MsgDao;
import com.mas.user.repository.query.chat.MsgQueryDao;

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
public class MsgService extends BaseServiceImpl<Msg> {

    @Override
    protected CrudDao<Msg> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<Msg> queryDao()
    {
        return queryDao;
    }
    @Autowired private MsgDao dao;
    @Autowired private MsgQueryDao queryDao;

}
