/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.service;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
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
import com.mas.security.domain.entity.SecurityUser;
import com.mas.security.domain.vo.Principal;
import com.mas.security.repository.dao.SecurityUserDao;
import com.mas.security.repository.query.SecurityUserQueryDao;
import com.mas.security.util.PasswordUtil;

/**
 * Security user service.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class SecurityUserService extends BaseServiceImpl<SecurityUser>
{
	@Override
	public Object persistence( SecurityUser bean ) throws ServiceException
	{
			if( VerifyUtil.isBlank( bean.getId() ) )
			{
				bean.setPassword( PasswordUtil.encrypt( bean.getAccount(), bean.getPassword() ) );
				bean.setErrorLoginCount( 0 );
				bean.setLastLoginIp( "127.0.0.1" );
				bean.setLastLoginDate( new Date() );
				bean.setUserState( 1 );
			} else {
				if( "1".equals( bean.getId().toString() ) ) return null;
				SecurityUser original = get( bean.getId() );
				if( null == original ) return null;
				bean.setErrorLoginCount( original.getErrorLoginCount() );
				bean.setLastLoginIp( original.getLastLoginIp() );
				bean.setLastLoginDate( original.getLastLoginDate() );
				bean.setUserState( original.getUserState() );
			}
			bean.setIsSuperManager( false );
			bean.setIsSystemModuleManager( false );
			Object result = dao.persistence( bean );
			if( VerifyUtil.isNotBlank( bean.getId() ) && ! "0".equals( bean.getId().toString() ) )
			{
				Collection<Session> sessions = sessionDAO.getActiveSessions();
				for( Session session : sessions )
				{
					SimplePrincipalCollection obj = ( SimplePrincipalCollection ) session.getAttribute( DefaultSubjectContext.PRINCIPALS_SESSION_KEY );
					if( null != obj )
					{
						Principal principal = ( Principal ) obj.getPrimaryPrincipal();
						if( bean.getId().toString().equals( principal.getId() ) )
						{
							principal.setHasChangeRole( true );
							break ;
						}
					}
				}
			}
			return result;
	}
	
	/**
	 * 更新密码。
	 * 
	 * @param id user ID。
	 * @param password 新密码。
	 * 
	 * @return true or false。
	 * 
	 * @throws ServiceException 
	 */
	public boolean updatePassword(Object id, String password) throws ServiceException
	{
		SecurityUser bean = get( id );
		if( null == bean ) return false;
		boolean result = dao.updatePassword( id, PasswordUtil.encrypt( bean.getAccount(), password ) );
		if( result )
		{
			Collection<Session> sessions = sessionDAO.getActiveSessions();
			for( Session session : sessions )
			{
				SimplePrincipalCollection obj = ( SimplePrincipalCollection ) session.getAttribute( DefaultSubjectContext.PRINCIPALS_SESSION_KEY );
				if( null != obj )
				{
					Principal principal = ( Principal ) obj.getPrimaryPrincipal();
					if( String.valueOf( id ).equals( principal.getId() ) )
					{
						principal.setHasChangeRole( true );
						break ;
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 重置密码。
	 * 
	 * @param id user ID。
	 * @param password 新密码。
	 * 
	 * @return 新密码或 "";
	 * 
	 * @throws ServiceException 
	 */
	public String resetPassword(Object id) throws ServiceException
	{
		SecurityUser bean = get( id );
		if( null == bean ) return "";
			String newPassword = RandomStringUtils.randomNumeric(6);
			if( dao.updatePassword( id, PasswordUtil.encrypt( bean.getAccount(), newPassword ) ) )
			{
				Collection<Session> sessions = sessionDAO.getActiveSessions();
				for( Session session : sessions )
				{
					SimplePrincipalCollection obj = ( SimplePrincipalCollection ) session.getAttribute( DefaultSubjectContext.PRINCIPALS_SESSION_KEY );
					if( null != obj )
					{
						Principal principal = ( Principal ) obj.getPrimaryPrincipal();
						if( String.valueOf( id ).equals( principal.getId() ) )
						{
							principal.setHasChangeRole( true );
							break ;
						}
					}
				}
				return newPassword;
			} else {
				throw new ServiceException( "重置密码失败！" );
			}
	}
	
	@Override
	public boolean enable( Object id )
	{
		boolean result = super.enable( id );
		if( result )
		{
			Collection<Session> sessions = sessionDAO.getActiveSessions();
			for( Session session : sessions )
			{
				SimplePrincipalCollection obj = ( SimplePrincipalCollection ) session.getAttribute( DefaultSubjectContext.PRINCIPALS_SESSION_KEY );
				if( null != obj )
				{
					Principal principal = ( Principal ) obj.getPrimaryPrincipal();
					if( String.valueOf( id ).equals( principal.getId() ) )
					{
						principal.setHasChangeRole( true );
						break ;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean disable( Object id )
	{
		boolean result = super.disable( id );
		if( result )
		{
			Collection<Session> sessions = sessionDAO.getActiveSessions();
			for( Session session : sessions )
			{
				SimplePrincipalCollection obj = ( SimplePrincipalCollection ) session.getAttribute( DefaultSubjectContext.PRINCIPALS_SESSION_KEY );
				if( null != obj )
				{
					Principal principal = ( Principal ) obj.getPrimaryPrincipal();
					if( String.valueOf( id ).equals( principal.getId() ) )
					{
						principal.setHasChangeRole( true );
						break ;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean remove( Object id )
	{
		boolean result = super.remove( id );
		if( result )
		{
			Collection<Session> sessions = sessionDAO.getActiveSessions();
			for( Session session : sessions )
			{
				SimplePrincipalCollection obj = ( SimplePrincipalCollection ) session.getAttribute( DefaultSubjectContext.PRINCIPALS_SESSION_KEY );
				if( null != obj )
				{
					Principal principal = ( Principal ) obj.getPrimaryPrincipal();
					if( String.valueOf( id ).equals( principal.getId() ) )
					{
						principal.setHasChangeRole( true );
						break ;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean delete( Object id )
	{
		boolean result = super.delete( id );
		if( result )
		{
			Collection<Session> sessions = sessionDAO.getActiveSessions();
			for( Session session : sessions )
			{
				SimplePrincipalCollection obj = ( SimplePrincipalCollection ) session.getAttribute( DefaultSubjectContext.PRINCIPALS_SESSION_KEY );
				if( null != obj )
				{
					Principal principal = ( Principal ) obj.getPrimaryPrincipal();
					if( String.valueOf( id ).equals( principal.getId() ) )
					{
						principal.setHasChangeRole( true );
						break ;
					}
				}
			}
		}
		return result;
	}
	
	public SecurityUser queryByAccount( Object account )
	{
		return queryDao.queryByAccount( account );
	}
	
	@Override
	protected CrudDao<SecurityUser> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<SecurityUser> queryDao()
	{
		return queryDao;
	}
	
	@Autowired
	private SecurityUserDao dao;
	@Autowired
	private SecurityUserQueryDao queryDao;
	@Autowired private SessionDAO sessionDAO;
}