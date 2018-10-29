/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.shops.domain.entity.Order;
import com.mas.shops.repository.dao.OrderDao;
import com.mas.shops.repository.query.OrderQueryDao;


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
public class OrderService  extends BaseServiceImpl<Order>{

    @Override
    protected CrudDao<Order> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<Order> queryDao() {
        return queryDao;
    }
    
    @Autowired
    private OrderDao dao;
    @Autowired
    private OrderQueryDao queryDao;
}