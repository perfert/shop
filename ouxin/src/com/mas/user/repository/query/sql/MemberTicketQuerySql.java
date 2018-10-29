/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.repository.query.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.repository.query.MemberTicketQueryDao;
import com.mas.user.domain.entity.MemberTicket;

/**
 * 
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class MemberTicketQuerySql extends QuerySql<MemberTicket> implements MemberTicketQueryDao
{
	@Override
	protected SimpleQuerySql querySql( MemberTicket query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andEqWhereIfNotBlankValue( "MEMBER_ID", query.getId() );
	}

	@Override
	protected String tableName()
	{
		return MemberTicket.TABLE_NAME;
	}
}
