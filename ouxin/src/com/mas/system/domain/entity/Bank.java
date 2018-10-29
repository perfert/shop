/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * 银行.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class Bank extends Entity
{
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "SYS_BANK";
	
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}
}
