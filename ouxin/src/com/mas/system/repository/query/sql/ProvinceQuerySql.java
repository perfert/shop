/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.query.sql;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.system.domain.entity.Province;
import com.mas.system.repository.query.ProvinceQueryDao;

/**
 * 省。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class ProvinceQuerySql extends QuerySql<Province> implements ProvinceQueryDao
{
	@Override
	public List<Province> queryAll( Province query )
	{
		SimpleQuerySql sql = null == query ? new SimpleQuerySql( tableName() )
		: querySql( query ).andEqWhereIfNotBlankValue( "STATE", query.getState() );

		List<Province> result =
				VerifyUtil.isEmpty( sql.values() ) ?
						getJdbcTemplate().query(
								sql.sql()
								, new BeanPropertyRowMapper<Province>(Province.class)
								)
						: getNamedParameterJdbcTemplate().query(
								sql.sql()
								, sql.values()
								, new BeanPropertyRowMapper<Province>(Province.class)
								);
		return result;
	}

	@Override
	protected SimpleQuerySql querySql( Province query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andLkWhereIfNotBlankValue( "NAME", query.getName() );
	}

	@Override
	protected String tableName()
	{
		return Province.TABLE_NAME;
	}
}