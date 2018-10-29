/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql.domain;

import com.mas.common.orm.sql.ISimpleSql;
import com.mas.common.verify.VerifyUtil;

/**
 * SQL 字段。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
class Field implements ISimpleSql
{
	private final From from;
	private final String fieldName;
	
	/**
	 * @return SQL 检索表。
	 */
	public From getFrom()
	{
		return this.from;
	}

	/**
	 * @return SQL 字段名。
	 */
	public String getFieldName()
	{
		return this.fieldName;
	}
	
	@Override
	public String sql()
	{
		return getFieldName();
	}
	
	/**
	 * SQL 字段。
	 * 
	 * @param from SQL 检索表。
	 * @param fieldName 字段名。
	 */
	public Field(From from, String fieldName)
	{
		this.from = from;
		this.fieldName = VerifyUtil.isBlank( getFrom().getTableAlias() ) ? fieldName
				: getFrom().getTableAlias() + POINT + fieldName;
	}
}