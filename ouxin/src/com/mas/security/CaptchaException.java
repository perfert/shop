/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security;

import com.mas.common.exception.CompileEx;

/**
 * Captcha compile exception.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class CaptchaException extends CompileEx
{
	private static final long serialVersionUID = 1L;

	/**
	 * Captcha compile exception.
	 * 
	 * @param message Specified exception message.
	 */
	public CaptchaException(String message)
	{
		super(message);
	}
	
	/**
	 * Captcha compile exception.
	 * 
	 * @param ex {@link Throwable}.
	 */
	public CaptchaException(Throwable ex)
	{
		super(ex);
	}
	
	/**
	 * Captcha compile exception.
	 * 
	 * @param message Specified exception message.
	 * @param ex {@link Throwable}.
	 */
	public CaptchaException(String message, Throwable ex)
	{
		super(message, ex);
	}
}