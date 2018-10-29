/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.core.service;

import com.mas.common.exception.RuntimeEx;

/**
 * Service compile exception.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class ServiceException extends RuntimeEx
{
	private static final long serialVersionUID = 1L;

	/**
	 * Service compile exception.
	 * 
	 * @param message Specified exception message.
	 */
	public ServiceException(String message)
	{
		super(message);
	}
	
	/**
	 * Service compile exception.
	 * 
	 * @param ex {@link Throwable}.
	 */
	public ServiceException(Throwable ex)
	{
		super(ex);
	}
	
	/**
	 * Service compile exception.
	 * 
	 * @param message Specified exception message.
	 * @param ex {@link Throwable}.
	 */
	public ServiceException(String message, Throwable ex)
	{
		super(message, ex);
	}
}