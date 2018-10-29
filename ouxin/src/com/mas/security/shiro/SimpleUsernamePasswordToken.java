/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 认证 Token。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SimpleUsernamePasswordToken extends UsernamePasswordToken
{
	private static final long serialVersionUID = 1L;

	public SimpleUsernamePasswordToken( String username, String password, boolean rememberMe, String host, String captcha )
	{
		super( username, password, rememberMe, host );
	}
}