/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mas.common.verify.VerifyUtil;
import com.mas.core.service.BaseService;
import com.mas.security.domain.entity.SecurityUser;
import com.mas.security.service.SecurityRoleService;
import com.mas.security.service.SecurityUserService;
import com.mas.system.service.impl.SystemModuleService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.security.SecurityUserCtrDto;

/**
 * Security user Controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( SecurityUserCtr.URI_PREFIX )
public class SecurityUserCtr extends ManageCtr<SecurityUser, SecurityUserCtrDto>
{
	public static final String URI_PREFIX = "manage/security/user";
	
	/**
	 * 根据页码, 页行, 分页检索数据.
	 * 
	 * @param pageNo 页码.
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	public String page(@PathVariable int pageNo, SecurityUserCtrDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			service.queryPage( dto.getPager(pageNo), dto.getQuery() );
			dto.setRoleMap( roleService.queryAll( null ) );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	@Override
	public String edit(@PathVariable String id, SecurityUserCtrDto dto, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if ( VerifyUtil.isNotBlank(id) ) 
			{
				dto.setBean(service.get(id));
			}
			dto.setRoles( roleService.queryAll( null ) );
			dto.setSystemModules( systemModuleService.queryAll( null ) );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return VIEW_INPUT( dto, model, params );
	}
	
	@Override
	protected BaseService<SecurityUser> service()
	{
		return service;
	}
	
	@Override
	protected String VIEW_PREFIX( Map<String, Object> params )
	{
		return URI_PREFIX;
	}

	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired
	private SecurityUserService service;
	@Autowired
	private SecurityRoleService roleService;
	@Autowired
	private SystemModuleService systemModuleService;
	
	private static final Logger log = LogManager.getLogger( SecurityUserCtr.class );
}