/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.dao;


import com.mas.core.repository.dao.CrudDao;
import com.mas.shops.domain.entity.Shops;

/**
 * 商家
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
public interface ShopsDao extends CrudDao<Shops> {

    boolean updateAuthStatus(Object id, Integer verify, String reason);

}