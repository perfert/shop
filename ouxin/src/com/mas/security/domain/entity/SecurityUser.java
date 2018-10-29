/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mas.common.verify.VerifyUtil;
import com.mas.core.domain.entity.Entity;

/**
 * Security user.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SecurityUser extends Entity
{
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "SEC_USER";
	
	private Object systemModuleId;
	private Object roleId;
	private String account;
	private String password;
	private Boolean isSuperManager;					// 是否超级管理员（所有系统模块皆有访问权限）
	private Boolean isSystemModuleManager;		// 是否系统模块超级管理员（仅所属系统模块有访问权限）
	private Integer errorLoginCount;						// 当天登录错误次数
	private String lastLoginIp;
	private Date lastLoginDate;
	
	private Integer userState;								// 用户状态（0：冻结/locked；1：正常）
	
	private List<SecurityRole> roles;
	
	/**
	 * 是否拥有某种角色。
	 * 
	 * @param roleId 角色ID。
	 *  
	 * @return true or false
	 */
	public boolean hasRole( Object roleId )
	{
		if( VerifyUtil.isEmpty( this.roles ) && VerifyUtil.isBlank( this.roleId ) )
		{
			return false;
		}
		if( VerifyUtil.isNotBlank( this.roleId ) )
		{
			if( this.roleId.equals( roleId ) )
			{
				return true;
			}
		}
		for( SecurityRole role : this.roles )
		{
			if( role.getId().equals( roleId ) )
			{
				return true;
			}
		}
		return false;
	}
	
	public List<SecurityRole> getRoles()
	{
		return roles;
	}
	public void setRoles( List<SecurityRole> roles )
	{
		if( VerifyUtil.isNotEmpty( roles ) )
		{
			if( VerifyUtil.isNotEmpty( this.roles ) )
			{
				this.roles.addAll( roles );
			} else {
				this.roles = roles;
			}
		}
	}
	public void addRole( SecurityRole role )
	{
		if( null != role )
		{
			if( VerifyUtil.isEmpty( this.roles ) )
			{
				this.roles = new ArrayList<SecurityRole>();
			}
			this.roles.add( role );
		}
	}
	
	public Boolean enable()
	{
		return 
				null != getUserState() 
				&& 1 == getUserState()
				&& null != getState()
				&& 1 == getState();
	}
	
	public Boolean locked()
	{
		return 
				null == getUserState()
				|| 0 == getUserState()
				|| null == getState()
				|| 1 != getState();
	}

	public Object getSystemModuleId()
	{
		return systemModuleId;
	}

	public void setSystemModuleId( Object systemModuleId )
	{
		this.systemModuleId = systemModuleId;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount( String account )
	{
		this.account = account;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public Boolean getIsSuperManager()
	{
		return isSuperManager;
	}

	public void setIsSuperManager( Boolean isSuperManager )
	{
		this.isSuperManager = isSuperManager;
	}

	public Boolean getIsSystemModuleManager()
	{
		return isSystemModuleManager;
	}

	public void setIsSystemModuleManager( Boolean isSystemModuleManager )
	{
		this.isSystemModuleManager = isSystemModuleManager;
	}

	public Integer getErrorLoginCount()
	{
		return errorLoginCount;
	}

	public void setErrorLoginCount( Integer errorLoginCount )
	{
		this.errorLoginCount = errorLoginCount;
	}

	public String getLastLoginIp()
	{
		return lastLoginIp;
	}

	public void setLastLoginIp( String lastLoginIp )
	{
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginDate()
	{
		return lastLoginDate;
	}

	public void setLastLoginDate( Date lastLoginDate )
	{
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getUserState()
	{
		return userState;
	}

	public void setUserState( Integer userState )
	{
		this.userState = userState;
	}

	public Object getRoleId()
	{
		return roleId;
	}

	public void setRoleId( Object roleId )
	{
		this.roleId = roleId;
	}
}