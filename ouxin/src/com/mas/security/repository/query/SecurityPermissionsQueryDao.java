/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.query;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.security.domain.entity.SecurityPermissions;

/**
 * Security permissions query DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface SecurityPermissionsQueryDao extends QueryDao<SecurityPermissions>
{
	/**
	 * 检索所有数据。
	 * 
	 * @param query 检索对象。
	 * 
	 * @return {@link List} or null
	 */
	List<SecurityPermissions> queryAll( SecurityPermissions query );
}