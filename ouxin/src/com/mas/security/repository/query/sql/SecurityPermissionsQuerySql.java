/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.query.sql;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.security.domain.entity.SecurityPermissions;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.repository.query.SecurityPermissionsQueryDao;

/**
 * Security permission query SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityPermissionsQuerySql extends QuerySql<SecurityPermissions> implements SecurityPermissionsQueryDao
{
	@Override
	public List<SecurityPermissions> queryAll( SecurityPermissions query )
	{
		SimpleQuerySql sql = null == query ? new SimpleQuerySql( tableName(), "bean" )
		: querySql( query ).andEqWhereIfNotBlankValue( "STATE", query.getState() );
		
		String select = null == query ? "SELECT bean.*, res.URI AS RESOURCE_URI FROM " + tableName() + " bean LEFT JOIN " + SecurityResource.TABLE_NAME + " res ON bean.RESOURCE_ID = res.ID"
				: "";

		List<SecurityPermissions> result =
				null == query ?
						getJdbcTemplate().query(
								select
								, new BeanPropertyRowMapper<SecurityPermissions>(SecurityPermissions.class)
								)
						: getNamedParameterJdbcTemplate().query(
								select + sql.sql()
								, sql.values()
								, new BeanPropertyRowMapper<SecurityPermissions>(SecurityPermissions.class)
								);
		return result;
	}
	
	@Override
	protected SimpleQuerySql querySql( SecurityPermissions query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andEqWhereIfNotBlankValue( "SYSTEM_MODULE_ID", query.getSystemModuleId() )
					.andEqWhereIfNotBlankValue( "RESOURCE_ID", query.getResourceId() )
					.andEqWhereIfNotBlankValue( "ROLE_ID", query.getRoleId() );
	}
	
	@Override
	protected String tableName()
	{
		return SecurityPermissions.TABLE_NAME;
	}
}