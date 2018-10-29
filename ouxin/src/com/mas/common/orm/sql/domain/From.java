/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql.domain;

import com.mas.common.orm.sql.ISimpleSql;
import com.mas.common.orm.sql.util.SqlKey;
import com.mas.common.verify.VerifyUtil;

/**
 * SQL 检索表。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
class From implements ISimpleSql
{
	private final String tableName;
	private final String tableAlias;

	/**
	 * @return 数据表表名。
	 */
	public String getTableName()
	{
		return this.tableName;
	}

	/**
	 * @return 数据表别名 or ""。
	 */
	public String getTableAlias()
	{
		return this.tableAlias;
	}
	
	@Override
	public String sql()
	{
		return
				SqlKey.FROM
				+ SPACE
				+ ( VerifyUtil.isBlank( getTableAlias() ) ? getTableName()
						: getTableName() + SPACE + getTableAlias() );
	}
	
	/**
	 * SQL 检索表，默认以 {@link DEFAULT_TABLE_ALIAS} 为别名。
	 * 
	 * @param tableName 数据表表名。
	 */
	public From(String tableName)
	{
		this(tableName, DEFAULT_TABLE_ALIAS);
	}
	
	/**
	 * SQL 检索表。
	 * 
	 * @param tableName 数据表表名。
	 * @param tableAlias 数据表别名（为 null 时，则SQL语句不以别名形式出现）。
	 */
	public From(String tableName, String tableAlias)
	{
		this.tableName = tableName;
		this.tableAlias = VerifyUtil.isBlank( tableAlias ) ? ""
				: tableAlias;
	}
}