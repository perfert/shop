/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.query.sql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.security.domain.entity.SecurityUser;
import com.mas.security.repository.query.SecurityUserQueryDao;

/**
 * Security user query SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityUserQuerySql extends QuerySql<SecurityUser> implements SecurityUserQueryDao
{
	@Override
	public SecurityUser queryByAccount( Object account )
	{
		return
				DataAccessUtils.uniqueResult(
						getJdbcTemplate().query(
								"SELECT * FROM " + tableName() + " WHERE ACCOUNT = ? AND STATE = 1"
								, new Object[] { account }
								, new BeanPropertyRowMapper<SecurityUser>(SecurityUser.class)
								)
						);
	}
	
	@Override
	protected SimpleQuerySql querySql( SecurityUser query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andEqWhereIfNotBlankValue( "SYSTEM_MODULE_ID", query.getSystemModuleId() )
					.andEqWhereIfNotBlankValue( "USER_STATE", query.getUserState() )
					.andEqWhereIfNotBlankValue( "IS_SUPER_MANAGER", query.getIsSuperManager() )
					.andEqWhereIfNotBlankValue( "IS_SYSTEM_MODULE_MANAGER", query.getIsSystemModuleManager() )
					.andLkWhereIfNotBlankValue( "ACCOUNT", query.getAccount() );
	}

	@Override
	protected String tableName()
	{
		return SecurityUser.TABLE_NAME;
	}
}