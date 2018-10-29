/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.domain.vo;

import java.io.Serializable;

/**
 * 用户菜单
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class UserMenu implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String pid;
	private String url;
	private Boolean isMenu;

	public String getId()
	{
		return id;
	}

	public void setId( Long id )
	{
		this.id = String.valueOf( id );
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid( Long pid )
	{
		if( 0L >= pid )
		{
			this.pid = "";
		}
		else
		{
			this.pid = String.valueOf( pid );
		}
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl( String url )
	{
		this.url = url;
	}

	public Boolean getIsMenu()
	{
		return isMenu;
	}

	public void setIsMenu( Boolean isMenu )
	{
		this.isMenu = isMenu;
	}
}
