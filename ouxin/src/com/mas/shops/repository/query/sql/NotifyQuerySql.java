/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query.sql;


import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.shops.domain.entity.Notify;
import com.mas.shops.repository.query.NotifyQueryDao;

/**
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class NotifyQuerySql extends QuerySql<Notify> implements NotifyQueryDao{
    
    
	@Override
	protected SimpleQuerySql querySql( Notify query )
	{
		return
				new SimpleQuerySql( tableName() )
					.addDesc( "ID" );
	}

	@Override
	protected String tableName()
	{
		return Notify.TABLE_NAME;
	}
}