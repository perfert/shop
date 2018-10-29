/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.system.domain.entity.Area;
import com.mas.system.repository.dao.AreaDao;
import com.mas.system.repository.query.AreaQueryDao;

/**
 * 地区。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class AreaService extends BaseServiceImpl<Area>
{
	/**
	 * 根据城市code，检索数据。
	 * 
	 * @param cityCode 城市CODE.
	 * 
	 * @return {@link List} or null
	 * 
	 * @throws ServiceException 
	 */
	public List<Area> queryByCity( Object cityCode ) throws ServiceException
	{
			return queryDao.queryByCity( cityCode );
	}

	@Override
	protected CrudDao<Area> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<Area> queryDao()
	{
		return queryDao;
	}
	
	@Autowired private AreaDao dao;
	@Autowired private AreaQueryDao queryDao;
}