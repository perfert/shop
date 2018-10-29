/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * Security role.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SecurityRole extends Entity
{
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "SEC_ROLE";
	
	private Object systemModuleId;
	private String name;
	private String remark;
	
	public Object getSystemModuleId()
	{
		return systemModuleId;
	}

	public void setSystemModuleId( Object systemModuleId )
	{
		this.systemModuleId = systemModuleId;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}
	
	@Override
	public boolean equals( Object obj )
	{
		if( null == obj || ! ( obj instanceof SecurityRole ) )
		{
			return false;
		}
		SecurityRole objBean = ( SecurityRole ) obj;
		return
				getId().equals( objBean.getId() )
				&& getSystemModuleId().equals( objBean.getSystemModuleId() );
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark( String remark )
	{
		this.remark = remark;
	}
}