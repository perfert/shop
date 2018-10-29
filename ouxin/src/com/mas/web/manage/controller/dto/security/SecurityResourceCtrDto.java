/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.dto.security;

import java.util.List;

import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.domain.entity.SecurityResourceTree;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**
 * Security resource DTO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SecurityResourceCtrDto extends BaseCtrDto<SecurityResource>
{
	private SecurityResource parent;
	private List<SecurityResourceTree> menuTree;
	
	public SecurityResource getParent()
	{
		return parent;
	}

	public void setParent( SecurityResource parent )
	{
		this.parent = parent;
	}

	public List<SecurityResourceTree> getMenuTree()
	{
		return menuTree;
	}

	public void setMenuTree( List<SecurityResourceTree> menuTree )
	{
		this.menuTree = menuTree;
	}
}