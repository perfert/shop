/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao;

import com.mas.core.repository.dao.CrudDao;
import com.mas.security.domain.entity.SecurityAuthorization;

/**
 * Security authorization DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface SecurityAuthorizationDao extends CrudDao<SecurityAuthorization>
{
	/**
	 * 根据用户ID，删除所有授权。
	 * 
	 * @param userId 用户ID。
	 * 
	 * @return 删除数量。
	 */
	int deleteAllByUserId( Object userId );
}