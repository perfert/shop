/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.common.verify.VerifyUtil;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.security.domain.entity.SecurityAuthorization;
import com.mas.security.repository.dao.SecurityAuthorizationDao;
import com.mas.security.repository.query.SecurityAuthorizationQueryDao;
import com.mas.security.shiro.ChangePermissionsNotify;

/**
 * Security authorization service.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class SecurityAuthorizationService extends BaseServiceImpl<SecurityAuthorization>
{
	@Override
	public Object persistence( SecurityAuthorization bean )
	{
		Object result = super.persistence( bean );
		if( VerifyUtil.isNotBlank( result ) && ! "0".equals( result ) ) changePermissionsNotify.hasChangePermissions();
		return result;
	}

	@Override
	public boolean enable( Object id )
	{
		boolean result = super.enable( id );
		if( result ) changePermissionsNotify.hasChangePermissions();
		return result;
	}

	@Override
	public boolean disable( Object id )
	{
		boolean result = super.disable( id );
		if( result ) changePermissionsNotify.hasChangePermissions();
		return result;
	}

	@Override
	public boolean remove( Object id )
	{
		boolean result = super.remove( id );
		if( result ) changePermissionsNotify.hasChangePermissions();
		return result;
	}

	@Override
	public boolean delete( Object id )
	{
		boolean result = super.delete( id );
		if( result ) changePermissionsNotify.hasChangePermissions();
		return result;
	}
	
	@Override
	protected CrudDao<SecurityAuthorization> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<SecurityAuthorization> queryDao()
	{
		return queryDao;
	}
	
	@Autowired
	private SecurityAuthorizationDao dao;
	@Autowired
	private SecurityAuthorizationQueryDao queryDao;
	@Autowired
	private ChangePermissionsNotify changePermissionsNotify;
}