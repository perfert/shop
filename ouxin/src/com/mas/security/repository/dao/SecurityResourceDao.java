/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao;

import com.mas.security.domain.entity.SecurityResource;

/**
 * Security resource DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface SecurityResourceDao
{
	/**
	 * 根据ID，获取数据对象。
	 * 
	 * @param id 数据对象ID。
	 * 
	 * @return {@link SecurityResource} or null。
	 */
	SecurityResource get(Object id);
	
	/**
	 * 持久化数据对象。
	 * 
	 * @param bean 数据对象。
	 * 
	 * @return 持久化数据的数据ID。
	 */
	Object persistence(SecurityResource bean);
	
	/**
	 * 检索顶层最右边的一个节点
	 * 
	 * @param systemModuleId 系统模块ID
	 */
	SecurityResource searchLastNode(Object systemModuleId);

	/**
	 * 物理删除节点及其所有子节点。
	 * 
	 * @return true or false。
	 */
	boolean delete( Object id );

	/**
	 * 仅仅删除节点下面的所有子节点。（物理删除）
	 * 
	 * @return true or false。
	 */
	boolean deleteChildren( Object id ); 
}