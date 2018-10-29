/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.query;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.system.domain.entity.Province;

/**
 * 省。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface ProvinceQueryDao extends QueryDao<Province>
{
	/**
	 * 获取所有数据。
	 * 
	 * @param query 检索条件。
	 * 
	 * @return {@link List} or null
	 */
	List<Province> queryAll( Province query );
}