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
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.shops.domain.entity.Guarantee;
import com.mas.shops.repository.dao.GuaranteeDao;
import com.mas.shops.repository.query.GuaranteeQueryDao;


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
public class GuaranteeService  extends BaseServiceImpl<Guarantee>{

    public Guarantee getByOrderId(String orderId) {
        if(StringUtils.isNotEmpty(orderId))
            return queryDao.getByOrderId(orderId);
        else
            return null;
    }
    
    /**
     * 更新担保金状态
     * @param id
     * @param value
     * @return
     */
    public boolean updateStatus(Object id, int state) {
        return dao.updateStatus(id,state);
    }
    
    @Override
    protected CrudDao<Guarantee> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<Guarantee> queryDao() {
        return queryDao;
    }

    @Autowired
    private GuaranteeDao dao;
    @Autowired
    private GuaranteeQueryDao queryDao;
    
    
   
}