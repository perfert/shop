/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql.domain;

import com.mas.common.orm.sql.util.SqlKey;

/**
 * Insert SQL。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class InsertSql extends Sql
{
	private final StringBuilder insertSql;
	private final StringBuilder valuesSql;
	
	private boolean hasField = false;
	
	/**
	 * 添加 Insert 字段。
	 * 
	 * @param fieldName 字段名。
	 * @param value 字段值。
	 * 
	 * @return this
	 */
	public InsertSql addField( String fieldName, Object value )
	{
		if( this.hasField )
		{
			this.insertSql.append( COMMA );
			this.valuesSql.append( COMMA );
		} else {
			this.hasField = true;
		}
		Field field = new Field( getFrom(), fieldName );
		this.insertSql.append( field.getFieldName() );
		this.valuesSql.append( COLON ).append( addValue( field.getFieldName(), value ) );
		
		return this;
	}
	
	@Override
	public String sql()
	{
		return this.insertSql
				+ RIGHT_BRACKETS
				+ this.valuesSql
				+ RIGHT_BRACKETS;
	}
	
	/**
	 * Insert SQL。
	 * 
	 * @param tableName Insert 数据表名。
	 */
	public InsertSql( String tableName )
	{
		super( new From( tableName, null ) );
		this.insertSql = new StringBuilder( SqlKey.INSERT.value() )
				.append( SPACE )
				.append( getFrom().getTableName() )
				.append( SPACE )
				.append( getFrom().getTableAlias() )
				.append( LEFT_BRACKETS );
		this.valuesSql = new StringBuilder( SqlKey.VALUES.value() ).append( LEFT_BRACKETS );
	}
}