/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao;

import com.mas.core.repository.dao.CrudDao;
import com.mas.security.domain.entity.SecurityPermissions;

/**
 * Security permissions DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface SecurityPermissionsDao extends CrudDao<SecurityPermissions>
{
	/**
	 * 根据角色ID，删除所有权限。
	 * 
	 * @param roleId 角色ID。
	 * 
	 * @return 删除数量。
	 */
	int deleteAllByRoleId( Object roleId );
}