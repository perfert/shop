/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.query.sql;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.system.domain.entity.Area;
import com.mas.system.repository.query.AreaQueryDao;

/**
 * 地区。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class AreaQuerySql extends QuerySql<Area> implements AreaQueryDao
{
	@Override
	public List<Area> queryByCity( Object cityCode )
	{
		List<Area> result =
				getJdbcTemplate().query(
						"SELECT * FROM " + tableName() + " WHERE STATE = 1 AND CITY_CODE = ?"
						, new Object[] { cityCode }
						, new BeanPropertyRowMapper<Area>(Area.class)
						);
		return result;
	}

	@Override
	protected SimpleQuerySql querySql( Area query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andEqWhereIfNotBlankValue( "CITY_CODE", query.getCityCode() )
					.andLkWhereIfNotBlankValue( "NAME", query.getName() );
	}

	@Override
	protected String tableName()
	{
		return Area.TABLE_NAME;
	}
}