/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * 系统模块。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SystemModule extends Entity
{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "SYS_SYSTEM_MODULE";
	
	private String name;
	private String accessPrefix;		// 访问路径前缀
	private String remark;
	
	public String getName()
	{
		return name;
	}
	public void setName( String name )
	{
		this.name = name;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark( String remark )
	{
		this.remark = remark;
	}
	public String getAccessPrefix()
	{
		return accessPrefix;
	}
	public void setAccessPrefix( String accessPrefix )
	{
		this.accessPrefix = accessPrefix;
	}
}