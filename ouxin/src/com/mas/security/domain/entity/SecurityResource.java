/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * Security resource.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SecurityResource extends Entity
{
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "SEC_RESOURCE";
	
	private Object systemModuleId;
	private Object parentId;
	private String name;
	private String uri;
	private Integer lft;
	private Integer rgt;
	private Boolean isMenu;
	private String icon;
	
	public Object getSystemModuleId()
	{
		return systemModuleId;
	}
	public void setSystemModuleId( Object systemModuleId )
	{
		this.systemModuleId = systemModuleId;
	}
	public Object getParentId()
	{
		return parentId;
	}
	public void setParentId( Object parentId )
	{
		this.parentId = parentId;
	}
	public String getName()
	{
		return name;
	}
	public void setName( String name )
	{
		this.name = name;
	}
	public String getUri()
	{
		return uri;
	}
	public void setUri( String uri )
	{
		this.uri = uri;
	}
	public Integer getLft()
	{
		return lft;
	}
	public void setLft( Integer lft )
	{
		this.lft = lft;
	}
	public Integer getRgt()
	{
		return rgt;
	}
	public void setRgt( Integer rgt )
	{
		this.rgt = rgt;
	}
	public Boolean getIsMenu()
	{
		return isMenu;
	}
	public void setIsMenu( Boolean isMenu )
	{
		this.isMenu = isMenu;
	}
	public String getIcon()
	{
		return icon;
	}
	public void setIcon( String icon )
	{
		this.icon = icon;
	}
}