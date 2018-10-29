/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.query;

import com.mas.core.repository.query.QueryDao;
import com.mas.security.domain.entity.SecurityUser;

/**
 * Security user query DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface SecurityUserQueryDao extends QueryDao<SecurityUser>
{
	SecurityUser queryByAccount(Object account);
}