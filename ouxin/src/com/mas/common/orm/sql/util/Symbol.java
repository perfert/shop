/*
 *Copyright (c) 2012, 2015, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql.util;

/**
 * SQL 参数赋值符号。
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public enum Symbol 
{
	eq("=")
	, ne("<>")
	, gt(">")
	, ge(">=")
	, lt("<")
	, le("<=")
	, lk("LIKE")
	, nl("NOT LIKE")
	, in("IN")
	, ni("NOT IN")
	, isn("IS NULL")
	, isnn("IS NOT NULL")
	;
	
	public String value() { return this.value; }
	@Override public String toString() { return value(); }
	
	private String value;
	private Symbol( String value ) { this.value = value; }
}