/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.query.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.security.domain.entity.SecurityPermissions;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.repository.query.SecurityResourceQueryDao;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.verify.VerifyUtil;

/**
 * Security resource query SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityResourceQuerySql extends QuerySql<SecurityResource> implements SecurityResourceQueryDao
{	
	@Override
	public List<SecurityResource> queryAll(SecurityResource query)
	{
		SimpleQuerySql sql = null == query ? new SimpleQuerySql( tableName() )
				: querySql( query ).andEqWhereIfNotBlankValue( "STATE", query.getState() );
		
		List<SecurityResource> result =
				VerifyUtil.isEmpty( sql.values() ) ?
						getJdbcTemplate().query(
								sql.sql()
								, new BeanPropertyRowMapper<SecurityResource>(SecurityResource.class)
								)
						: getNamedParameterJdbcTemplate().query(
								sql.sql()
								, sql.values()
								, new BeanPropertyRowMapper<SecurityResource>(SecurityResource.class)
								);
		return result;
	}
	
	@Override
	public List<SecurityResource> queryByRole( Object roleId )
	{
		return
				getJdbcTemplate().query(
						"SELECT bean.* FROM " + tableName() + " bean LEFT JOIN " + SecurityPermissions.TABLE_NAME + " per ON bean.ID = per.RESOURCE_ID WHERE per.ROLE_ID = ? ORDER BY bean.LFT ASC"
						, new Object[] { roleId }
						, new BeanPropertyRowMapper<SecurityResource>(SecurityResource.class)
						);
	}
	
	@Override
	public List<SecurityResource> queryThisAndParentBy( Object... resourceIds )
	{
		if( VerifyUtil.isEmpty( resourceIds ) ) return null;
		List<Object> ids = new ArrayList<Object>();
		for(Object resId : resourceIds )
		{
			ids.add( resId );
		}
		Map<String, List<Object>> values = new HashMap<String, List<Object>>();
		values.put( "ids", ids );
		return
				getNamedParameterJdbcTemplate().query(
						"SELECT * FROM " + tableName() + " WHERE ID IN ( SELECT bean.ID FROM " + tableName() + " bean LEFT JOIN " + tableName() + " res ON bean.LFT <= res.LFT AND bean.RGT >= res.RGT WHERE res.ID IN (:ids) GROUP BY bean.ID)"
						, values
						, new BeanPropertyRowMapper<SecurityResource>(SecurityResource.class)
						);
	}
	
	@Override
	protected SimpleQuerySql querySql( SecurityResource query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andEqWhereIfNotBlankValue( "SYSTEM_MODULE_ID", query.getSystemModuleId() )
					.andEqWhereIfNotBlankValue( "PARENT_ID", query.getParentId() )
					.andEqWhereIfNotBlankValue( "IS_MENU", query.getIsMenu() )
					.andLkWhereIfNotBlankValue( "NAME", query.getName() );
	}

	@Override
	protected String tableName()
	{
		return SecurityResource.TABLE_NAME;
	}
}