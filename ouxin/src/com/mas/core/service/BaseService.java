/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.core.service;

import com.mas.common.orm.util.Page;
import com.mas.core.domain.entity.Entity;
import com.mas.core.domain.vo.StateVo;
import com.mas.shops.domain.entity.Certificate;

/**
 * Base service.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface BaseService<E extends Entity>
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
	 * @return 持久化数据的数据ID。
	 * 
	 * @throws ServiceException 
	 */
	Object persistence(E bean);
	
	/**
	 * 开启数据对象正常状态。
	 * 
	 * @param id 数据对象ID。
	 * 
	 * @return true or false。
	 */
	boolean enable(Object id);
	
	/**
	 * 关闭数据对象正常状态。
	 * 
	 * @param id 数据对象ID。
	 * 
	 * @return true or false。
	 */
	boolean disable(Object id);
	
	/**
	 * 根据ID，逻辑移除数据对象。
	 * 
	 * @param id 数据对象ID。
	 * 
	 * @return true or false。
	 */
	boolean remove(Object id);
	
	/**
	 * 根据ID，物理删除数据对象。
	 * 
	 * @param id 数据对象ID。
	 * 
	 * @return true or false。
	 */
	boolean delete(Object id);
	
	/**
	 * @param state 数据状态（为空则忽略数据状态检索）。
	 * 
	 * @return 检索所有数据对象。
	 */
	//List<E> queryAll(Integer state);
	
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