/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql;

/**
 * Simple SQL 接口。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface ISimpleSql
{
	/** "bean" */
	public static final String DEFAULT_TABLE_ALIAS = "bean";
	/** " " */
	public static final String SPACE = " ";
	/** ", " */
	public static final String COMMA = ", ";
	/** "." */
	public static final String POINT = ".";
	/** ":" */
	public static final String COLON = ":";
	/** "_" */
	public static final String UNDERLINE = "_";
	/** "%" */
	public static final String PERCENT = "%";
	/** " ( " */
	public static final String LEFT_BRACKETS = " ( ";
	/** " ) " */
	public static final String RIGHT_BRACKETS = " ) ";
	
	/**
	 * @return SQL 语句。
	 */
	String sql();
}