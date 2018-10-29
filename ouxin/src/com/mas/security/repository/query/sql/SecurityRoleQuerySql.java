/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.query.sql;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.security.domain.entity.SecurityRole;
import com.mas.security.repository.query.SecurityRoleQueryDao;

/**
 * Security role query SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityRoleQuerySql extends QuerySql<SecurityRole> implements SecurityRoleQueryDao
{
	@Override
	public List<SecurityRole> queryAll( SecurityRole query )
	{
		SimpleQuerySql sql = null == query ? new SimpleQuerySql( tableName() )
		: querySql( query ).andEqWhereIfNotBlankValue( "STATE", query.getState() );

		List<SecurityRole> result =
				VerifyUtil.isEmpty( sql.values() ) ?
						getJdbcTemplate().query(
								sql.sql()
								, new BeanPropertyRowMapper<SecurityRole>(SecurityRole.class)
								)
						: getNamedParameterJdbcTemplate().query(
								sql.sql()
								, sql.values()
								, new BeanPropertyRowMapper<SecurityRole>(SecurityRole.class)
								);
		return result;
	}
	
	@Override
	protected SimpleQuerySql querySql( SecurityRole query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andEqWhereIfNotBlankValue( "SYSTEM_MODULE_ID", query.getSystemModuleId() )
					.andLkWhereIfNotBlankValue( "NAME", query.getName() );
	}
	
	@Override
	protected String tableName()
	{
		return SecurityRole.TABLE_NAME;
	}
}