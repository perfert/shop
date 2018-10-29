/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mas.common.verify.VerifyUtil;
import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.core.service.BaseService;
import com.mas.security.domain.entity.SecurityPermissions;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.domain.entity.SecurityResourceTree;
import com.mas.security.service.SecurityPermissionsService;
import com.mas.security.service.SecurityResourceService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.security.SecurityPermissionsCtrDto;

/**
 * 权限配置
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( SecurityPermissionsCtr.URI_PREFIX )
public class SecurityPermissionsCtr extends ManageCtr<SecurityPermissions, SecurityPermissionsCtrDto>
{
	public static final String URI_PREFIX = "manage/security/{systemModuleId}/role/{roleId}/permission";

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
	@RequestMapping( "list/{pageNo}")
	public String page(@PathVariable int pageNo, SecurityPermissionsCtrDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			SecurityPermissions query = new SecurityPermissions();
			query.setRoleId( pathValue( params, "roleId" ) );
			List<SecurityPermissions> list = service.queryAll( query );
			
			List<SecurityResourceTree> menuTree = new ArrayList<SecurityResourceTree>();
			Map<Object, SecurityResourceTree> menuTreeMap = new HashMap<Object, SecurityResourceTree>();
			
			SecurityResource res = new SecurityResource();
			res.setSystemModuleId( pathValue( params, "systemModuleId" ) );
			List<SecurityResource> resources = this.resourceService.queryAll( res );
			
			SecurityResourceTree node = null;
			List<String> permissions = new ArrayList<String>();
			
			for( SecurityResource resource : resources )
			{
				node = new SecurityResourceTree();
				node.setNode( resource );
				menuTreeMap.put( resource.getId(), node );
			}
			if( VerifyUtil.isNotEmpty( list ) )
			{
				for( SecurityPermissions bean : list )
				{
					permissions.add( bean.getResourceId().toString() );
					if( menuTreeMap.containsKey( bean.getResourceId() ) )
					{
						menuTreeMap.get( bean.getResourceId() ).setChecked( true );
					}
				}
			}
			dto.setPermissions( permissions );
			for( SecurityResource resource : resources )
			{
				if( VerifyUtil.isBlank( resource.getParentId() ) || "0".equals( resource.getParentId().toString() ) )
				{
					menuTree.add( menuTreeMap.get( resource.getId() ) );
				} else {
					node = menuTreeMap.get( resource.getParentId() );
					node.addChildren( 
							menuTreeMap.get( resource.getId() ).setParent( node )
							);
				}
			}
			dto.setMenuTree( menuTree );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	/**
	 * 添加数据.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@Override
	public String create(SecurityPermissionsCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			service.persistence( pathValue( params, "roleId" ), dto.getMenuIds() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			return REDIRECT_VIEW_CREATE( dto, model, params );
		}
		bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		return REDIRECT_VIEW_LIST( dto, model, params );
	}
	
	/**
	 * 更新数据.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@Override
	public String modify(SecurityPermissionsCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			service.persistence( pathValue( params, "roleId" ), dto.getMenuIds() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			return REDIRECT_VIEW_CREATE( dto, model, params );
		}
		bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		return REDIRECT_VIEW_LIST( dto, model, params );
	}
	
	@Override
	protected String VIEW_PREFIX( Map<String, Object> params )
	{
		return "manage/security/permission";
	}
	
	@Override
	protected String REDIRECT_VIEW_PREFIX( Map<String, Object> params ) 
	{
		String result = "manage/security/" + pathValue( params, "systemModuleId" ) + "/role/" + pathValue( params, "roleId" ) + "/permission";
		return result;
	}

	@Override
	protected BaseService<SecurityPermissions> service()
	{
		return service;
	}

	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired private SecurityPermissionsService service;
	@Autowired private SecurityResourceService resourceService;
	
	private static final Logger log = LogManager.getLogger( SecurityPermissionsCtr.class );
}