/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.util;

import com.mas.security.domain.entity.SecurityPermissions;
import com.mas.security.domain.entity.SecurityRole;

/**
 * Security auxiliary utility.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public final class SecurityUtil
{
	/** 会员登录验证码 KEY */
	public static final String CAPTCHA_MEMBER_LOGIN = "captcha_member_login";
	
	public static final String getRealRole( SecurityRole role )
	{
		return getRealRole( role.getSystemModuleId(), role.getId() );
	}
	
	public static final String getRealRole( SecurityPermissions permissions )
	{
		return getRealRole( permissions.getSystemModuleId(), permissions.getRoleId() );
	}
	
	/**
	 * 获取real角色。
	 * 
	 * @param systemTypeId 系统类型ID。
	 * @param roleId 角色ID。
	 * 
	 * @return String
	 */
	public static final String getRealRole( Object systemTypeId, Object roleId )
	{
		return String.valueOf( systemTypeId ) + "-" + String.valueOf( roleId );
	}
	
	private SecurityUtil() { super(); }
}