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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mas.common.verify.VerifyUtil;
import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.core.service.BaseService;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.service.SecurityResourceService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.security.SecurityResourceCtrDto;

/**
 * Security resource controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( SecurityResourceCtr.URI_PREFIX )
public class SecurityResourceCtr extends ManageCtr<SecurityResource, SecurityResourceCtrDto>
{
	public static final String URI_PREFIX = "manage/security/{systemModuleId}/resource/{parentId}";

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
	@Override
	public String page(@PathVariable int pageNo, SecurityResourceCtrDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			dto.setMenuTree( cacheMenu.menuTree() );
		} catch (Exception ex) {
			handlerException( ex, log, model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	/**
	 * 编辑数据表单.
	 * 
	 * @param id 数据ID.
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 * @return
	 */
	@Override
	public String edit(@PathVariable String id, SecurityResourceCtrDto dto, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if ( VerifyUtil.isNotBlank(id) ) 
			{
				dto.setBean(service.get(id));
			}
			Object parentId = pathValue( params, "parentId" );
			if( VerifyUtil.isNotBlank( parentId ) && ! "0".equals( parentId.toString() ) )
			{
				dto.setParent( service.get( parentId ) );
			}
		} catch (Exception ex) {
			handlerException( ex, log, model );
		}
		return VIEW_INPUT(dto, model, params);
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
	public String create(SecurityResourceCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			dto.getBean().setId( null );
			dto.getBean().setSystemModuleId( pathValue( params, "systemModuleId" ) );
			dto.getBean().setParentId( pathValue( params, "parentId" ) );
			service.persistence( dto.getBean() );
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
	public String modify(SecurityResourceCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		Object id = pathValue(params, "id");
		try {
			if( null == id || VerifyUtil.isBlank( id.toString() ) )
			{
				throw new Exception("更新数据ID不能为空！");
			}
			dto.getBean().setSystemModuleId( pathValue( params, "systemModuleId" ) );
			service.persistence( dto.getBean() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			return REDIRECT_VIEW_EDIT( dto, model, params, id );
		}
		bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		return REDIRECT_VIEW_LIST( dto, model, params );
	}
	
	
	/**
	 * 根据ID, 删除数据.
	 * 
	 * @param ids 数据ID集.
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@Override
	public String delete(@PathVariable Object[] ids, SecurityResourceCtrDto dto, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			if( VerifyUtil.isNotEmpty( ids ) )
			{
				for(Object id : ids )
				{
					service.delete( id );
				}
			}
			bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return REDIRECT_VIEW_LIST( dto, model, params );
	}
	
	/**
	 * 根据ID, 删除数据.
	 * 
	 * @param ids 数据ID集.
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@Override
	public String remove(@PathVariable Object[] ids, SecurityResourceCtrDto dto, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			throw new Exception( "操作功能未开放！" );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return REDIRECT_VIEW_LIST( dto, model, params );
	}
	
	@Override
	protected BaseService<SecurityResource> service()
	{
		return null;
	}

	@Override
	protected String VIEW_PREFIX( Map<String, Object> params )
	{
		return "manage/security/resource";
	}
	
	@Override
	protected String REDIRECT_VIEW_PREFIX( Map<String, Object> params ) 
	{
		return "manage/security/" + pathValue( params, "systemModuleId" ) + "/resource/0";
	}
	
	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired
	private SecurityResourceService service;
	
	private static final Logger log = LogManager.getLogger(SecurityResourceCtr.class);
}