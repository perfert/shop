/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.dto.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mas.common.verify.VerifyUtil;
import com.mas.security.domain.entity.SecurityRole;
import com.mas.security.domain.entity.SecurityUser;
import com.mas.system.domain.entity.SystemModule;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**
 * Security user.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SecurityUserCtrDto extends BaseCtrDto<SecurityUser>
{
	private List<SecurityRole> roles;
	private Map<String, SecurityRole> roleMap;
	private List<SystemModule> systemModules;

	public List<SecurityRole> getRoles()
	{
		return roles;
	}

	public void setRoles( List<SecurityRole> roles )
	{
		this.roles = roles;
	}

	public Map<String, SecurityRole> getRoleMap()
	{
		return roleMap;
	}

	public void setRoleMap( List<SecurityRole> list )
	{
		this.roleMap = new HashMap<String, SecurityRole>();
		if( VerifyUtil.isNotEmpty( list ) )
		{
			for( SecurityRole role : list )
			{
				roleMap.put( role.getId().toString(), role );
			}
		}
	}

	public List<SystemModule> getSystemModules()
	{
		return systemModules;
	}

	public void setSystemModules( List<SystemModule> systemModules )
	{
		this.systemModules = systemModules;
	}
}