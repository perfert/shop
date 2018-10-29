/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query.sql;


import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.shops.domain.entity.Order;
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
@Repository
public class OrderQuerySql extends QuerySql<Order> implements OrderQueryDao{
    
    
	
	@Override
	protected SimpleQuerySql querySql( Order query )
	{
		return
				new SimpleQuerySql( tableName() )
					.addDesc( "ID" );
	}

	@Override
	protected String tableName()
	{
		return Order.TABLE_NAME;
	}

    @Override
    public Order get(String mid, String thirdOrderId) {
        return null;
    }

}