/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.core.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Abstract entity.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public abstract class Entity implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Object id;
	private Integer priority;
	private Integer state;
	private Date createDate;
	private Date modifyDate;
	
    public Object getId()
	{
		return id;
	}
	public void setId( Object id )
	{
		this.id = id;
	}
	public Integer getPriority()
	{
		return priority;
	}
	public void setPriority( Integer priority )
	{
		this.priority = priority;
	}
	public Integer getState()
	{
		return state;
	}
	public void setState( Integer state )
	{
		this.state = state;
	}
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate( Date createDate )
	{
		this.createDate = createDate;
	}
	public Date getModifyDate()
	{
		return modifyDate;
	}
	public void setModifyDate( Date modifyDate )
	{
		this.modifyDate = modifyDate;
	}
	
	public Entity()
	{
		super();
		Date current = new Date();
		setCreateDate( current );
		setModifyDate( current );
	}
	
	
	
}