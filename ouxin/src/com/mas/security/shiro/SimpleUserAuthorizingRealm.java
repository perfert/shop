/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.mas.common.verify.VerifyUtil;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.domain.entity.SecurityRole;
import com.mas.security.domain.entity.SecurityUser;
import com.mas.security.domain.vo.Principal;
import com.mas.security.service.SecurityResourceService;
import com.mas.security.service.SecurityRoleService;
import com.mas.security.service.SecurityUserService;
import com.mas.security.util.PasswordUtil;

/**
 * 用户 Realm。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SimpleUserAuthorizingRealm extends AuthorizingRealm
{
	/**
	 * 授权查询回调函数，进行鉴权但缓存中无用户的授权信息时调用。在配有缓存的情况下，只加载一次。
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo( PrincipalCollection principals )
	{		
		// 获取当前登陆的用户
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		String userId = principal.getId();
		
		if( VerifyUtil.isNotBlank( userId ) )
		{
			if( principal.isHasChangeRole() )
			{
				synchronized( principal )
				{
					if( principal.isHasChangeRole() )
					{
						SimpleAuthorizationInfo authorizationInfo = principal.getAuthorizationInfo();
						if( null != authorizationInfo.getObjectPermissions() )
						{
							authorizationInfo.getObjectPermissions().clear();
						}
						if( null != authorizationInfo.getStringPermissions() )
						{
							authorizationInfo.getStringPermissions().clear();
						}
						if( null != authorizationInfo.getRoles() )
						{
							authorizationInfo.getRoles().clear();
						}
						/*for( SecurityRole role : securityRoleService.findByUserId( userId ) )
						{
							authorizationInfo.addRole( role.getId() );
						}*/
						SecurityUser user = securityUserService.get( principal.getId() );
						if( null != user )
						{
							SecurityRole role = securityRoleService.get( user.getRoleId() );
							if( null != role && 1 == role.getState() )
							{
								authorizationInfo.addRole( role.getSystemModuleId() + "-" + role.getId() );
								List<SecurityResource> memus = user.getIsSuperManager() ? resourceService.queryAll( null ) : resourceService.queryByRole( user.getRoleId() );
								principal.reLoadMenu( memus );
							}
						}
						principal.setHasChangeRole( false );
					}
				}
			}
			return principal.getAuthorizationInfo();	
		}
		return null;
	}

	/**
	 * 认证回调函数，认证（登录）时调用。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken token )
	{
		SimpleUsernamePasswordToken customToken = ( SimpleUsernamePasswordToken ) token;
		
		String account = customToken.getUsername();
		String password = String.valueOf( customToken.getPassword() );
		// String host = customToken.getHost();
        
        //FIXME MAS 需要作验证码验证
        if( VerifyUtil.isNotBlank( account ) && VerifyUtil.isNotBlank( password ) )
        {
        	SecurityUser user = securityUserService.queryByAccount( account );
        	if( null == user )
        	{
        		throw new UnknownAccountException();
        	}
        	if( ! user.enable() )
        	{
        		throw new DisabledAccountException();
        	}
//        	if( user.locked() )
//        	{
//        		throw new LockedAccountException();
//        	}
        	if( ! PasswordUtil.isPassword( account, password, user.getPassword() ) )
        	{
        		throw new IncorrectCredentialsException();
        	}
        	// 用户菜单
        	
        	// 踢出用户
//    		Collection<Session> sessions = sessionDAO.getActiveSessions();
//    		for( Session session : sessions )
//    		{
//    			System.out.println(session);
//    			Principal obj = ( Principal ) session.getAttribute( DefaultSubjectContext.PRINCIPALS_SESSION_KEY );
//    			System.out.println(obj);
//    		}

        	List<SecurityResource> memus = user.getIsSuperManager() ? resourceService.queryAll( null ) : resourceService.queryByRole( user.getRoleId() );
        	
        	return new SimpleAuthenticationInfo(new Principal( user.getId().toString(), account, user.getIsSuperManager(), memus ), password, getName() );
        }
        throw new UnknownAccountException();
	}
	
	@Autowired private SecurityUserService securityUserService;
	@Autowired private SecurityResourceService resourceService;
	@Autowired private SecurityRoleService securityRoleService;
	//@Autowired private SessionDAO sessionDAO;
}