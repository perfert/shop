/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.common.web;

import java.io.Serializable;

/**
 * Web error entity.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class ErrorMsg implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String KEY = "error";
	
	private Object bean;
	private final STATUS status;
	private final String message;
	private final String url;
	
	public ErrorMsg(Object bean)
	{
		this(null, null, null);
		this.bean = bean;
	}
	
	public ErrorMsg(STATUS status, String message)
	{
		this( status, message, null);
	}
	
	public ErrorMsg(STATUS status, String message, String url)
	{
		this.status = null != status ? status : STATUS.error;
		this.message = message;
		this.url = url;
	}
	
	public STATUS getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getUrl() {
		return url;
	}
	
	public Object getBean() {
		return this.bean;
	}
	/**
	 * JSON 状态码.
	 * 
	 * @version 1.0
	 * 
	 * @author MAS
	 */
	public enum STATUS
	{
		success		// 成功.
		, warn		// 警告.
		, error		// 错误.
		, failed		// 失败(这里定为 404 错误.)
	}
}