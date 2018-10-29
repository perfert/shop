/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql.domain;

import com.mas.common.orm.sql.util.SqlConnKey;
import com.mas.common.orm.sql.util.SqlKey;
import com.mas.common.orm.sql.util.Symbol;

/**
 * Update SQL。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class UpdateSql extends Sql
{
	private final StringBuilder updateSql;
	private final StringBuilder whereSql;
	
	private boolean hasField = false;
	private boolean hasWhere = false;
	
	/**
	 * 添加 Update 字段。
	 * 
	 * @param fieldName 字段名。
	 * @param value 字段值。
	 * 
	 * @return this
	 */
	public UpdateSql addField( String fieldName, Object value )
	{
		if( this.hasField )
		{
			this.updateSql.append( COMMA );
		} else {
			this.hasField = true;
		}
		this.updateSql.append( putValue( new Field( getFrom(), fieldName ), Symbol.eq, value ) );
		
		return this;
	}
	
	/**
	 * 添加 "=" 赋值的 Update 条件，多个条件以 AND 连接。
	 * 
	 * @param fieldName 字段名。
	 * @param value 字段值。
	 * 
	 * @return this
	 */
	public UpdateSql andEqWhere( String fieldName, Object value )
	{
		return andWhere( fieldName, Symbol.eq, value );
	}
	
	/**
	 * 添加 Update 条件，多个条件以 AND 连接。
	 * 
	 * @param fieldName 字段名。
	 * @param symbol 赋值符号。
	 * @param value 字段值。
	 * 
	 * @return this
	 */
	public UpdateSql andWhere( String fieldName, Symbol symbol, Object value )
	{
		if( this.hasWhere )
		{
			this.whereSql.append( SPACE ).append( SqlConnKey.AND ).append( SPACE );
		} else {
			this.whereSql.append( SqlKey.WHERE ).append( SPACE );
			this.hasWhere = true;
		}
		this.whereSql.append( putValue( new Field( getFrom(), fieldName ), symbol, value ) );
		
		return this;
	}
	
	@Override
	public String sql()
	{
		return this.updateSql
				+ SPACE
				+ whereSql;
	}
	
	public UpdateSql( String tableName )
	{
		super( new From( tableName ) );
		this.updateSql 
			= new StringBuilder( SqlKey.UPDATE.value() )
					.append( SPACE )
					.append( getFrom().getTableName() )
					.append( SPACE )
					.append( getFrom().getTableAlias() )
					.append( SPACE )
					.append( SqlKey.SET.value() )
					.append( SPACE );
		this.whereSql = new StringBuilder();
	}
}