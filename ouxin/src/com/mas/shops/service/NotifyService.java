/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.shops.domain.entity.Notify;
import com.mas.shops.repository.dao.NotifyDao;
import com.mas.shops.repository.query.NotifyQueryDao;


/**
 * 会员。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class NotifyService  extends BaseServiceImpl<Notify>{

    /**
     * 写入提示
     * @param objectId
     * @param typeId
     * @param status
     * @return
     */
    public Object insert(String objectId, int typeId, int status,String parameters) {
        if(StringUtils.isEmpty(objectId))
            throw new ServiceException("parameter.error");
        Notify notify = new Notify();
        notify.setObjectId(objectId);
        notify.setTypeId(typeId);
        notify.setStatus(status);
        notify.setState(1);
        notify.setParameters(parameters);
        return dao.persistence(notify);
    }
    
    /**
     * 更新回调提示状态
     * @param notifyId 通知ID
     * @param status   通知状态
     * @return
     */
    public boolean updateStatus(Object notifyId, int status) {
        if(null == notifyId)
            throw new ServiceException("parameter.error");
        return dao.updateStatus(notifyId,status);
    }
    
    @Override
    protected CrudDao<Notify> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<Notify> queryDao() {
        return queryDao;
    }
    
    @Autowired
    private NotifyDao dao;
    @Autowired
    private NotifyQueryDao queryDao;
    
    
}