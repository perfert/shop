/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.exception;

/**
 * Abstract runtime exception.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class RuntimeEx extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Runtime exception.
	 * 
	 * @param message Specified exception message.
	 */
	public RuntimeEx(String message)
	{
		super(
				CompileEx.message(message)
				);
	}
	
	/**
	 * Runtime exception.
	 * 
	 * @param ex {@link Throwable}.
	 */
	public RuntimeEx(Throwable ex)
	{
		super(
				CompileEx.throwable(ex)
				);
	}
	
	/**
	 * Runtime exception.
	 * 
	 * @param message Specified exception message.
	 * @param ex {@link Throwable}.
	 */
	public RuntimeEx(String message, Throwable ex)
	{
		super(
				CompileEx.message(message)
				, CompileEx.throwable(ex)
				);
	}
}