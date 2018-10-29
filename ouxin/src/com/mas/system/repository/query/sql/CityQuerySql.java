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
import com.mas.system.repository.query.CityQueryDao;
import com.mas.system.domain.entity.City;

/**
 * 市。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class CityQuerySql extends QuerySql<City> implements CityQueryDao
{
	@Override
	public List<City> queryByProvince( Object provinceCode )
	{
		List<City> result =
					getJdbcTemplate().query(
							"SELECT * FROM " + tableName() + " WHERE STATE = 1 AND PROVINCE_CODE = ?"
							, new Object[] { provinceCode }
							, new BeanPropertyRowMapper<City>(City.class)
							);
		return result;
	}

	@Override
	protected SimpleQuerySql querySql( City query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andEqWhereIfNotBlankValue( "PROVINCE_CODE", query.getProvinceCode() )
					.andLkWhereIfNotBlankValue( "NAME", query.getName() );
	}

	@Override
	protected String tableName()
	{
		return City.TABLE_NAME;
	}
}