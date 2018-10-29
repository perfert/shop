/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.service;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.common.verify.VerifyUtil;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.security.domain.entity.SecurityRole;
import com.mas.security.domain.vo.Principal;
import com.mas.security.repository.dao.SecurityRoleDao;
import com.mas.security.repository.query.SecurityRoleQueryDao;

/**
 * Security role service.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class SecurityRoleService extends BaseServiceImpl<SecurityRole>
{
	@Override
	public Object persistence( SecurityRole bean )
	{
		Object result = super.persistence( bean );
		if( VerifyUtil.isNotBlank( bean.getId() ) && ! "0".equals( bean.getId().toString() ) )
		{
			Collection<Session> sessions = sessionDAO.getActiveSessions();
			for( Session session : sessions )
			{
				SimplePrincipalCollection obj = ( SimplePrincipalCollection ) session.getAttribute( DefaultSubjectContext.PRINCIPALS_SESSION_KEY );
				if( null != obj )
				{
					Principal principal = ( Principal ) obj.getPrimaryPrincipal();
					SimpleAuthorizationInfo authorizationInfo = principal.getAuthorizationInfo();
					if( null != authorizationInfo.getRoles() && 0 < authorizationInfo.getRoles().size() )
					{
						for( String roleId : authorizationInfo.getRoles() )
						{
							if( roleId.equals( String.valueOf( bean.getId() ) ) )
							{
								principal.setHasChangeRole( true );
								break ;
							}
						}
					}
				}
			}
		}
		return result;
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
	public List<SecurityRole> queryAll( SecurityRole query ) throws ServiceException
	{
			return queryDao.queryAll( query );
	}
	
	@Override
	protected CrudDao<SecurityRole> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<SecurityRole> queryDao()
	{
		return queryDao;
	}
	
	@Autowired
	private SecurityRoleDao dao;
	@Autowired
	private SecurityRoleQueryDao queryDao;
	@Autowired private SessionDAO sessionDAO;
}