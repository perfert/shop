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
import com.mas.system.domain.entity.Province;
import com.mas.system.repository.dao.ProvinceDao;
import com.mas.system.repository.query.ProvinceQueryDao;

/**
 * 省。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class ProvinceService extends BaseServiceImpl<Province>
{
	/**
	 * 获取所有数据。
	 * 
	 * @param query 检索条件。
	 * 
	 * @return {@link List} or null
	 * 
	 * @throws ServiceException 
	 */
	public List<Province> queryAll( Province query ) throws ServiceException
	{
			return queryDao.queryAll( query );
	}
	
	@Override
	protected CrudDao<Province> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<Province> queryDao()
	{
		return queryDao;
	}
	
	@Autowired private ProvinceDao dao;
	@Autowired private ProvinceQueryDao queryDao;
}