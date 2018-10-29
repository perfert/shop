/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.reflection;

import com.mas.common.exception.CompileEx;

/**
 * Reflection compile exception.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class ReflectionException extends CompileEx
{
	private static final long serialVersionUID = 1L;

	/**
	 * Reflection compile exception.
	 * 
	 * @param message Specified exception message.
	 */
	public ReflectionException(String message)
	{
		super(message);
	}
	
	/**
	 * Reflection compile exception.
	 * 
	 * @param ex {@link Throwable}.
	 */
	public ReflectionException(Throwable ex)
	{
		super(ex);
	}
	
	/**
	 * Reflection compile exception.
	 * 
	 * @param message Specified exception message.
	 * @param ex {@link Throwable}.
	 */
	public ReflectionException(String message, Throwable ex)
	{
		super(message, ex);
	}
}