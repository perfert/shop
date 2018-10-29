/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.query;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.security.domain.entity.SecurityResource;

/**
 * Security resource query DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface SecurityResourceQueryDao extends QueryDao<SecurityResource>
{	
	/**
	 * 检索所有节点
	 * 
	 * @param query 检索条件。
	 * 
	 * @param {@link List}
	 */
	List<SecurityResource> queryAll(SecurityResource query);
	
	List<SecurityResource> queryByRole(Object roleId);
	
	List<SecurityResource> queryThisAndParentBy(Object... resourceIds);
}