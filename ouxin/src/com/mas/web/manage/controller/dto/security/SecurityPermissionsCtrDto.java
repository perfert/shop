/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.dto.security;

import java.util.List;

import com.mas.security.domain.entity.SecurityPermissions;
import com.mas.security.domain.entity.SecurityResourceTree;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**
 * 权限
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SecurityPermissionsCtrDto extends BaseCtrDto<SecurityPermissions>
{
	private String[] menuIds;
	private List<SecurityResourceTree> menuTree;
	private List<String> permissions;

	public String[] getMenuIds()
	{
		return menuIds;
	}

	public void setMenuIds( String[] menuIds )
	{
		this.menuIds = menuIds;
	}

	public List<SecurityResourceTree> getMenuTree()
	{
		return menuTree;
	}

	public void setMenuTree( List<SecurityResourceTree> menuTree )
	{
		this.menuTree = menuTree;
	}

	public List<String> getPermissions()
	{
		return permissions;
	}

	public void setPermissions( List<String> permissions )
	{
		this.permissions = permissions;
	}
}