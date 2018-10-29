/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.reposiroty.springjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.mas.common.exception.RuntimeEx;
import com.mas.common.reflection.ReflectionUtil;

/**
 * Abstract spring JDBC DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public abstract class BaseSpringJdbcDao
{
	protected final Class<?> ormClass;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	protected JdbcTemplate getJdbcTemplate()
	{
		return this.jdbcTemplate;
	}
	
	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate()
	{
		return namedParameterJdbcTemplate;
	}
	
	protected BaseSpringJdbcDao()
	{
		super();
		if ( null == (this.ormClass = ReflectionUtil.getSuperClassGenericType(getClass()))
				/*|| Object.class == this.ormClass*/ ) {
			throw new RuntimeEx("Unspecified orm class.");
		}
	}
	
	protected BaseSpringJdbcDao( Class<?> clazz )
	{
		super();
		if( null == clazz )
		{
			throw new RuntimeEx("Unspecified orm class.");
		}
		this.ormClass = clazz;
	}
}