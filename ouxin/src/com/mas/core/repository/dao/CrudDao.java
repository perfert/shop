/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.core.repository.dao;

import com.mas.core.domain.entity.Entity;
import com.mas.core.domain.vo.StateVo;

/**
 * Entity CRUD DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface CrudDao<E extends Entity>
{
	/**
	 * 根据ID，获取数据对象。
	 * 
	 * @param id 数据对象ID。
	 * 
	 * @return {@link E} or null。
	 */
	E get(Object id);
	
	/**
	 * 持久化数据对象。
	 * 
	 * @param bean 数据对象。
	 * 
	 * @return 持久化数据的数据ID or null。
	 */
	Object persistence(E bean);
	
	/**
	 * 修改数据状态。
	 * 
	 * @param id 数据对象ID。
	 * @param state 状态。
	 * 
	 * @return true or false。
	 */
	boolean modifyState(Object id, StateVo state);
	
	/**
	 * 根据ID，物理删除数据对象。
	 * 
	 * @param id 数据对象ID。
	 * 
	 * @return true or false。
	 */
	boolean delete(Object id);
}