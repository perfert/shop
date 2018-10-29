/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.exception;

import com.mas.common.verify.VerifyUtil;

/**
 * Abstract the compile exception.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class CompileEx extends Exception
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Compile exception.
	 * 
	 * @param message Specified exception message.
	 */
	public CompileEx(String message)
	{
		super(
				message(message)
				);
	}
	
	/**
	 * Compile exception.
	 * 
	 * @param ex {@link Throwable}.
	 */
	public CompileEx(Throwable ex)
	{
		super(
				throwable(ex)
				);
	}
	
	/**
	 * Compile exception.
	 * 
	 * @param message Specified exception message.
	 * @param ex {@link Throwable}.
	 */
	public CompileEx(String message, Throwable ex)
	{
		super(
				message(message)
				, throwable(ex)
				);
	}
	
	static Throwable throwable(Throwable ex) {
		return
				VerifyUtil.isNull(ex) ? new Throwable( "Unspecified exception." )
						: ex;
	}
	
	static String message(String message) {
		return
				VerifyUtil.isBlank(message) ? "Unspecified exception message."
						: message;
	}
}