/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.query;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.system.domain.entity.City;

/**
 * 城市。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface CityQueryDao extends QueryDao<City>
{
	/**
	 * 根据省code，检索所有数据。
	 * 
	 * @param provinceCode 省 code.
	 * 
	 * @return {@link List} or null.
	 */
	List<City> queryByProvince( Object provinceCode );
}