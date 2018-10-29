/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * Security user authorization.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SecurityAuthorization extends Entity
{
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "SYS_SYSTEM_MODULE";
	
	private Object systemModuleId;
	
	private Object roleId;
	private Object userId;
	
	public Object getSystemModuleId()
	{
		return systemModuleId;
	}
	public void setSystemModuleId( Object systemModuleId )
	{
		this.systemModuleId = systemModuleId;
	}
	public Object getRoleId()
	{
		return roleId;
	}
	public void setRoleId( Object roleId )
	{
		this.roleId = roleId;
	}
	public Object getUserId()
	{
		return userId;
	}
	public void setUserId( Object userId )
	{
		this.userId = userId;
	}
}