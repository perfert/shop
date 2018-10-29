/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.core.repository.query;

import com.mas.common.orm.util.Page;
import com.mas.core.domain.entity.Entity;
import com.mas.core.domain.vo.StateVo;

/**
 * Entity query DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface QueryDao<E extends Entity>
{
	/**
	 * @param state 数据状态（为空则忽略数据状态检索）。
	 * 
	 * @return 检索所有数据对象。
	 */
	//List<E> queryAll(StateVo state);
	
	/**
	 * 分页获取数据对象。
	 * 
	 * @param page 分页对象。
	 * @param state 数据状态（为空则忽略数据状态检索）。
	 */
	void queryPage(Page page, StateVo state);
	
	/**
	 * 分页获取数据对象。
	 * 
	 * @param page 分页对象。
	 * @param query 检索条件。
	 */
	void queryPage(Page page, E query);
}