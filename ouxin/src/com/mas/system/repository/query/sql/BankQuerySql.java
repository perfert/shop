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
import com.mas.system.domain.entity.Bank;
import com.mas.system.repository.query.BankQueryDao;

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
public class BankQuerySql extends QuerySql<Bank> implements BankQueryDao
{

	@Override
	public List<Bank> queryAll()
	{
		return
				getJdbcTemplate().query(
						"SELECT * FROM " + tableName()
						, new BeanPropertyRowMapper<Bank>(Bank.class)
						);
	}

	@Override
	protected SimpleQuerySql querySql( Bank query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andLkWhereIfNotBlankValue( "NAME", query.getName() );
	}

	@Override
	protected String tableName()
	{
		return Bank.TABLE_NAME;
	}
}
