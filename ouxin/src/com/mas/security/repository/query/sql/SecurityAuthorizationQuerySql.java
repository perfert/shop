/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.query.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.security.domain.entity.SecurityAuthorization;
import com.mas.security.repository.query.SecurityAuthorizationQueryDao;

/**
 * Security authorization query SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityAuthorizationQuerySql extends QuerySql<SecurityAuthorization> implements SecurityAuthorizationQueryDao
{
	@Override
	protected SimpleQuerySql querySql( SecurityAuthorization query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andEqWhereIfNotBlankValue( "SYSTEM_MODULE_ID", query.getSystemModuleId() )
					.andEqWhereIfNotBlankValue( "USER_ID", query.getUserId() )
					.andEqWhereIfNotBlankValue( "ROLE_ID", query.getRoleId() );
	}
	
	@Override
	protected String tableName()
	{
		return SecurityAuthorization.TABLE_NAME;
	}
}