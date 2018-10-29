/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.core.domain.vo;

/**
 * State value object.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public enum StateVo
{
	/** 正常 */
	ENABLE(1)
	
	/** 关闭/禁用 */
	, DISABLE(0)
	
	/** 移除 */
	, REMOVE(-1)
	;
	
	public int value() { return this.value; }
	@Override public String toString() { return String.valueOf( value() ); } 
	
	private final int value;
	private StateVo(int value)
	{
		this.value = value;
	}
}