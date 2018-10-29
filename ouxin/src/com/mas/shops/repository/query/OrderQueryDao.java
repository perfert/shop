/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query;

import com.mas.core.repository.query.QueryDao;
import com.mas.shops.domain.entity.Order;

/**
 * 商家
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
public interface OrderQueryDao extends QueryDao<Order>{

    Order get(String mid,String thirdOrderId);
}