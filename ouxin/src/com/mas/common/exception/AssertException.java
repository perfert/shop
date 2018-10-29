/*
 *Copyright (c) 2012, 2015, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.exception;

/**
 * If the assertion fails, a runtime exception is thrown. </br></br>
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public class AssertException extends RuntimeEx
{
	private static final long serialVersionUID = 1L;

	/**
	 * Runtime assert exception.
	 * 
	 * @param message Specified exception message.
	 */
	public AssertException(String message)
	{
		super(message);
	}
	
	/**
	 * Runtime assert exception.
	 * 
	 * @param ex {@link Throwable}.
	 */
	public AssertException(Throwable ex)
	{
		super(ex);
	}
	
	/**
	 * Runtime assert exception.
	 * 
	 * @param message Specified exception message.
	 * @param ex {@link Throwable}.
	 */
	public AssertException(String message, Throwable ex)
	{
		super(message, ex);
	}
}