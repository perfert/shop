/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.core.repository;

import com.mas.common.exception.CompileEx;

/**
 * Dao compile exception.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class DaoException extends CompileEx
{
	private static final long serialVersionUID = 1L;

	/**
	 * Dao compile exception.
	 * 
	 * @param message Specified exception message.
	 */
	public DaoException(String message)
	{
		super(message);
	}
	
	/**
	 * Dao compile exception.
	 * 
	 * @param ex {@link Throwable}.
	 */
	public DaoException(Throwable ex)
	{
		super(ex);
	}
	
	/**
	 * Dao compile exception.
	 * 
	 * @param message Specified exception message.
	 * @param ex {@link Throwable}.
	 */
	public DaoException(String message, Throwable ex)
	{
		super(message, ex);
	}
}