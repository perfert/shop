/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.system.domain.entity.Bank;
import com.mas.system.repository.dao.BankDao;
import com.mas.system.repository.query.BankQueryDao;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;

/**
 * 
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class BankService extends BaseServiceImpl<Bank>
{
	public List<Bank> queryAll()
	{
		return queryDao.queryAll();
	}

	@Override
	protected CrudDao<Bank> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<Bank> queryDao()
	{
		return queryDao;
	}
	
	@Autowired private BankDao dao;
	@Autowired private BankQueryDao queryDao;
}
