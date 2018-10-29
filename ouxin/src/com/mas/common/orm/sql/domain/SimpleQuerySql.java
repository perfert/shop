/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql.domain;

import com.mas.common.orm.sql.util.SqlConnKey;
import com.mas.common.orm.sql.util.SqlKey;
import com.mas.common.orm.sql.util.Symbol;
import com.mas.common.verify.VerifyUtil;

/**
 * Simple query SQL。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SimpleQuerySql extends Sql
{
	private final StringBuilder selectSql;
	private final StringBuilder whereSql;
	private final StringBuilder orderSql;
	
	private boolean hasSelect = false;
	private boolean hasWhere = false;
	private boolean hasOrder = false;

	@Override
	public String sql()
	{
		String sql =
				( ! hasSelect ? SqlKey.SELECT + " * "
						: this.selectSql )
				+ SPACE
				+ getFrom().sql();
		
		if( 0 < this.whereSql.length() )
		{
			sql += SPACE +  this.whereSql;
		}
		if( 0 < this.orderSql.length() )
		{
			sql += SPACE + this.orderSql;
		}
		return sql;
	}
	
	/**
	 * @return <span style="color:Red;">暂时提供使用，以后会改</span>
	 */
	public String where()
	{
		return whereSql.toString();
	}
	
	/**
	 * @return <span style="color:Red;">暂时提供使用，以后会改</span>
	 */
	public String orderBy()
	{
		return orderSql.toString();
	}
	
	/**
	 * 添加 Select 字段。
	 * 
	 * @param fieldName 字段名。
	 * 
	 * @return this。
	 */
	public SimpleQuerySql addSelect( String fieldName )
	{
		return addSelect( fieldName, null );
	}
	
	/**
	 * 添加 Select 字段。
	 * 
	 * @param fieldName 字段名。
	 * @param fieldNameAlias 字段别名（为 null 时，则不以别名形式出现）。
	 * 
	 * @return this。
	 */
	public SimpleQuerySql addSelect( String fieldName, String fieldNameAlias )
	{
		if( hasSelect )
		{
			this.selectSql.append( COMMA );
		} else {
			this.hasSelect = true;
		}
		this.selectSql.append( 
				new Field( getFrom(), fieldName ).getFieldName() 
				);
		if( VerifyUtil.isBlank( fieldNameAlias ) )
		{
			this.selectSql.append( SPACE )
					.append( SqlKey.AS )
					.append( SPACE )
					.append( fieldNameAlias );
		}
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
	public SimpleQuerySql andEqWhere( String fieldName, Object value )
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
	public SimpleQuerySql andWhere( String fieldName, Symbol symbol, Object value )
	{
		return where( SqlConnKey.AND, fieldName, symbol, value );
	}
	
	/**
	 * 当条件值不为 null 及 blank 时， 添加 "=" 赋值的 Update 条件，多个条件以 AND 连接。
	 * 
	 * @param fieldName 字段名。
	 * @param value 字段值。
	 * 
	 * @return this
	 */
	public SimpleQuerySql andEqWhereIfNotBlankValue( String fieldName, Object value )
	{
		return andWhereIfNotBlankValue( fieldName, Symbol.eq, value );
	}
	
	/**
	 * 当条件值不为 null 及 blank 时， 添加 "LIKE" 赋值的 Update 条件，多个条件以 AND 连接。
	 * 
	 * @param fieldName 字段名。
	 * @param value 字段值。
	 * 
	 * @return this
	 */
	public SimpleQuerySql andLkWhereIfNotBlankValue( String fieldName, Object value )
	{
		return andWhereIfNotBlankValue( fieldName, Symbol.lk, value );
	}
	
	/**
	 * 当条件值不为 null 及 blank 时， 添加 Update 条件，多个条件以 AND 连接。
	 * 
	 * @param fieldName 字段名。
	 * @param symbol 赋值符号。
	 * @param value 字段值。
	 * 
	 * @return this
	 */
	public SimpleQuerySql andWhereIfNotBlankValue( String fieldName, Symbol symbol, Object value )
	{
		return whereIfNotBlankValue( SqlConnKey.AND, fieldName, symbol, value );
	}
	
	/**
	 * 当条件值不为 null 及 blank 时， 添加 Update 条件。
	 * 
	 * @param conn 条件连接关键词。
	 * @param fieldName 字段名。
	 * @param symbol 赋值符号。
	 * @param value 字段值。
	 * 
	 * @return this
	 */
	public SimpleQuerySql whereIfNotBlankValue( SqlConnKey conn, String fieldName, Symbol symbol, Object value )
	{
		if( null == value || VerifyUtil.isBlank( value.toString() ) )
		{
			return this;
		} else {
			return where( conn, fieldName, symbol, value );
		}
	}
	
	/**
	 * 添加 Update 条件。
	 * 
	 * @param conn 条件连接关键词。
	 * @param fieldName 字段名。
	 * @param symbol 赋值符号。
	 * @param value 字段值。
	 * 
	 * @return this
	 */
	public SimpleQuerySql where( SqlConnKey conn, String fieldName, Symbol symbol, Object value )
	{
		if( this.hasWhere )
		{
			this.whereSql.append( SPACE ).append( conn ).append( SPACE );
		} else {
			this.whereSql.append( SqlKey.WHERE ).append( SPACE );
			this.hasWhere = true;
		}
		this.whereSql.append( putValue( new Field( getFrom(), fieldName ), symbol, value ) );
		
		return this;
	}
	
	/**
	 * 添加 DESC 排序字段。
	 * 
	 * @param fieldName
	 * 
	 * @return this
	 */
	public SimpleQuerySql addDesc( String fieldName )
	{
		initOrderSql( fieldName );
		this.orderSql.append( SqlKey.DESC );
		return this;
	}
	
	/**
	 * 添加 ASC 排序字段。
	 * 
	 * @param fieldName
	 * 
	 * @return this
	 */
	public SimpleQuerySql addAsc( String fieldName )
	{
		initOrderSql( fieldName );
		this.orderSql.append( SqlKey.ASC );
		return this;
	}
	
	/** 初始化 order sql */
	private void initOrderSql( String fieldName )
	{
		if( this.hasOrder )
		{
			this.orderSql.append( COMMA );
		} else {
			this.orderSql.append( SqlKey.ORDERBY ).append( SPACE );
			this.hasOrder = true;
		}
		this.orderSql.append( new Field( getFrom(), fieldName ).getFieldName() )
				.append( SPACE );
	}
	
	/**
	 * Simple query SQL。
	 * 
	 * @param tableName 数据表表名。
	 */
	public SimpleQuerySql( String tableName )
	{
		this( tableName, DEFAULT_TABLE_ALIAS );
	}
	
	/**
	 * Simple query SQL。
	 * 
	 * @param tableName 数据表表名。
	 * @param tableAlias 数据表别名（为 null 时，则SQL语句不以别名形式出现）。
	 */
	public SimpleQuerySql( String tableName, String tableAlias )
	{
		super( new From( tableName, tableAlias ) );
		this.selectSql = new StringBuilder( SqlKey.SELECT.value() ).append( SPACE );
		this.whereSql = new StringBuilder();
		this.orderSql = new StringBuilder();
	}
}