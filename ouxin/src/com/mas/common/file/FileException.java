/*
 *Copyright (c) 2012, 2015, MAS and/or itself. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.file;

import com.mas.common.exception.CompileEx;

/**
 * File compile exception. </br></br>
 * 
 * @version 1.0
 * 
 * @since JDK 1.7
 *
 * @author MAS
 */
public class FileException extends CompileEx
{
	private static final long serialVersionUID = 1L;

	/**
	 * File compile exception.
	 * 
	 * @param message Specified exception message.
	 */
	public FileException(String message)
	{
		super(message);
	}
	
	/**
	 * File compile exception.
	 * 
	 * @param ex {@link Throwable}.
	 */
	public FileException(Throwable ex)
	{
		super(ex);
	}
	
	/**
	 * File compile exception.
	 * 
	 * @param message Specified exception message.
	 * @param ex {@link Throwable}.
	 */
	public FileException(String message, Throwable ex)
	{
		super(message, ex);
	}
}