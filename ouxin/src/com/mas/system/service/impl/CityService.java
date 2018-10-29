/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.system.domain.entity.City;
import com.mas.system.repository.dao.CityDao;
import com.mas.system.repository.query.CityQueryDao;

/**
 * 市。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class CityService extends BaseServiceImpl<City>
{
	/**
	 * 根据省code，检索所有数据。
	 * 
	 * @param provinceCode 省 code.
	 * 
	 * @return {@link List} or null.
	 * 
	 * @throws ServiceException 
	 */
	public List<City> queryByProvince( Object provinceCode ) throws ServiceException
	{
			return queryDao.queryByProvince( provinceCode );
	}
	
	@Override
	protected CrudDao<City> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<City> queryDao()
	{
		return queryDao;
	}
	
	@Autowired private CityDao dao;
	@Autowired private CityQueryDao queryDao;
}