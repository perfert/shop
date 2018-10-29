/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql.domain;

import java.util.HashMap;
import java.util.Map;

import com.mas.common.orm.sql.ISql;
import com.mas.common.orm.sql.util.Symbol;

/**
 * 抽象 SQL 基类。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
abstract class Sql implements ISql
{
	private final From from;
	private final Map<String, Object> values;
	private int suffix = 0;
	
	@Override
	public final Map<String, Object> values()
	{
		return this.values;
	}
	
	/**
	 * @return SQL 检索表。
	 */
	protected From getFrom()
	{
		return this.from;
	}
	
	/**
	 * Put 参数值，返回相应赋值SQL。
	 * 
	 * @param field SQL字段。
	 * @param symbol 赋值符号。
	 * @param value 字段值。
	 * 
	 * @return 返回赋值SQL。
	 */
	String putValue( Field field, Symbol symbol, Object value)
	{
		boolean brackets = Symbol.in.equals( symbol ) || Symbol.ni.equals( symbol ) ? true
				: false;
		return 
				field.getFieldName()
				+ SPACE
				+ symbol.value()
				+ (brackets ? LEFT_BRACKETS : "")
				+ COLON
				+ addValue(
						symbol.name() + UNDERLINE + field.getFieldName()
						, ( Symbol.lk.equals( symbol ) || Symbol.nl.equals( symbol ) ? PERCENT + value + PERCENT
								: value ) 
						)
				+ (brackets ? RIGHT_BRACKETS : "");
	}
	
	/**
	 * Add 参数值，并返回最终参数值key。
	 * 
	 * @param key 初始key。
	 * @param value 参数值。
	 * 
	 * @return 最终的唯一的SQL参数值 key。
	 */
	String addValue(String key, Object value)
	{
		String realKey = generateValuesKey( key.replaceAll( "\\.", UNDERLINE ).toLowerCase() );
		this.values.put( realKey, value );
		return realKey;
	}
	
	/**
	 * 生成唯一的SQL参数值 key。
	 * 
	 * @param key 初始key。
	 * 
	 * @return 唯一的SQL参数值 key。
	 */
	private String generateValuesKey(String key)
	{
		if( values().containsKey( key ) )
		{
			return generateValuesKey( key + ( this.suffix ++ ) );
		}
		return key;
	}
	
	public Sql(From from)
	{
		this.from = from;
		this.values = new HashMap<String, Object>();
	}
}