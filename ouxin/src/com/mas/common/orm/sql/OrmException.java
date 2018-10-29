/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.orm.sql;

import com.mas.common.exception.CompileEx;

/**
 * Orm compile exception.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class OrmException extends CompileEx
{
	private static final long serialVersionUID = 1L;

	/**
	 * Orm compile exception.
	 * 
	 * @param message Specified exception message.
	 */
	public OrmException(String message)
	{
		super(message);
	}
	
	/**
	 * Orm compile exception.
	 * 
	 * @param ex {@link Throwable}.
	 */
	public OrmException(Throwable ex)
	{
		super(ex);
	}
	
	/**
	 * Orm compile exception.
	 * 
	 * @param message Specified exception message.
	 * @param ex {@link Throwable}.
	 */
	public OrmException(String message, Throwable ex)
	{
		super(message, ex);
	}
}