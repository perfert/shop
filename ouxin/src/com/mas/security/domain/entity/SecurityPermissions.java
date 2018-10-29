/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * Security permissions.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SecurityPermissions extends Entity
{
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "SEC_PERMISSION";
	
	private Object systemModuleId;
	private Object resourceId;
	private Object roleId;
	
	// web
	private String resourceUri;
	
	//private String permissions;
	
	public Object getSystemModuleId()
	{
		return systemModuleId;
	}

	public void setSystemModuleId( Object systemModuleId )
	{
		this.systemModuleId = systemModuleId;
	}

	public Object getResourceId()
	{
		return resourceId;
	}

	public void setResourceId( Object resourceId )
	{
		this.resourceId = resourceId;
	}

	public Object getRoleId()
	{
		return roleId;
	}

	public void setRoleId( Object roleId )
	{
		this.roleId = roleId;
	}

	public String getResourceUri()
	{
		return resourceUri;
	}

	public void setResourceUri( String resourceUri )
	{
		this.resourceUri = resourceUri;
	}
	
//	public String getPermissions()
//	{
//		return VerifyUtil.isBlank( permissions ) ? this.permissions = String.valueOf( getSystemModuleId() ) + "-" + String.valueOf( getRoleId() )
//				: permissions;
//	}
}