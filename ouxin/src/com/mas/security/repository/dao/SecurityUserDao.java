/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao;

import com.mas.core.repository.dao.CrudDao;
import com.mas.security.domain.entity.SecurityUser;

/**
 * Security user DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface SecurityUserDao extends CrudDao<SecurityUser>
{
	/**
	 * 更新密码。
	 * 
	 * @param id user ID。
	 * @param password 新密码。
	 * 
	 * @return true or false。
	 */
	boolean updatePassword(Object id, String password);
}