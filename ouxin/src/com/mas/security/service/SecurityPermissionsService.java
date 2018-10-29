/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.common.verify.VerifyUtil;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.security.domain.entity.SecurityPermissions;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.domain.entity.SecurityRole;
import com.mas.security.repository.dao.SecurityPermissionsDao;
import com.mas.security.repository.query.SecurityPermissionsQueryDao;
import com.mas.security.shiro.ChangePermissionsNotify;

/**
 * Security permissions service.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class SecurityPermissionsService extends BaseServiceImpl<SecurityPermissions>
{
	/**
	 * 根据角色ID，删除所有权限。
	 * 
	 * @param roleId 角色ID。
	 * 
	 * @return 删除数量。
	 * 
	 * @throws ServiceException 
	 */
	public int deleteAllByRoleId( Object roleId ) throws ServiceException
	{
		int result = dao.deleteAllByRoleId( roleId );
		changePermissionsNotify.hasChangePermissions();
		return result;
	}
	
	public Object persistence( Object roleId, Object... resourceIds ) throws ServiceException
	{
			SecurityRole role = roleService.get( roleId );
			if( null == role ) return null;
			
			deleteAllByRoleId( roleId );
			
			if( VerifyUtil.isNotEmpty( resourceIds ) )
			{
				SecurityPermissions bean = null;
				List<SecurityResource> list = resourceService.queryThisAndParentBy( resourceIds );
				if( VerifyUtil.isNotEmpty( list ) )
				{
					for( SecurityResource res : list )
					{
						bean = new SecurityPermissions();
						bean.setSystemModuleId( role.getSystemModuleId() );
						bean.setRoleId( roleId );
						bean.setResourceId( res.getId() );
						super.persistence( bean );
					}
				}
			}
			changePermissionsNotify.hasChangePermissions();
			return 0;
	}
	
	
	/**
	 * 检索所有数据。
	 * 
	 * @param query 检索对象。
	 * 
	 * @return {@link List} or null
	 * 
	 * @throws ServiceException 
	 */
	public List<SecurityPermissions> queryAll( SecurityPermissions query ) throws ServiceException
	{
			return queryDao.queryAll( query );
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
	protected CrudDao<SecurityPermissions> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<SecurityPermissions> queryDao()
	{
		return queryDao;
	}
	
	@Autowired
	private SecurityPermissionsDao dao;
	@Autowired
	private SecurityPermissionsQueryDao queryDao;
	@Autowired
	private SecurityRoleService roleService;
	@Autowired
	private SecurityResourceService resourceService;
	@Autowired
	private ChangePermissionsNotify changePermissionsNotify;
}