/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query;

import com.mas.core.repository.query.QueryDao;
import com.mas.shops.domain.entity.Guarantee;

/**
 * 商家
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
public interface GuaranteeQueryDao extends QueryDao<Guarantee>{

    Guarantee getByOrderId(String orderId);

    
    
}