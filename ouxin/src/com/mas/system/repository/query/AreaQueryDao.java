/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.query;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mas.core.repository.query.QueryDao;
import com.mas.system.domain.entity.Area;

/**
 * 地区。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public interface AreaQueryDao extends QueryDao<Area>
{
	/**
	 * 根据城市code，检索数据。
	 * 
	 * @param cityCode 城市CODE.
	 * 
	 * @return {@link List} or null
	 */
	List<Area> queryByCity( Object cityCode );
}