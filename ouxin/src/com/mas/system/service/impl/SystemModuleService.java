/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.domain.vo.StateVo;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.system.domain.entity.SystemModule;
import com.mas.system.repository.dao.SystemModuleDao;
import com.mas.system.repository.query.SystemModuleQueryDao;

/**
 * 系统模块 Service.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class SystemModuleService extends BaseServiceImpl<SystemModule>
{
	/**
	 * @param state 数据状态（为空则忽略数据状态检索）。
	 * 
	 * @return 检索所有数据对象。
	 */
	public List<SystemModule> queryAll(StateVo state)
	{
		return queryDao.queryAll( state );
	}
	
	@Override
	protected CrudDao<SystemModule> crudDao()
	{
		return this.dao;
	}

	@Override
	protected QueryDao<SystemModule> queryDao()
	{
		return this.queryDao;
	}
	
	@Autowired
	private SystemModuleDao dao;
	@Autowired
	private SystemModuleQueryDao queryDao;
}