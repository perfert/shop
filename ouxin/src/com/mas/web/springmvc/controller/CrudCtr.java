/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.springmvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mas.common.file.FileBean;
import com.mas.common.file.FileException;
import com.mas.common.verify.VerifyUtil;
import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.core.domain.entity.Entity;
import com.mas.core.service.BaseService;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;
import com.mas.web.springmvc.util.UploadUtil;

/**
 * Crud Controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public abstract class CrudCtr<L extends Entity, D extends BaseCtrDto<L>> extends BaseCtr
{
	/*
	 * =====================================================================================================================
	 * ======= CRUD 操作接口 ===============================================================================================
	 * =====================================================================================================================
	 */
	
	/**
	 * 分页检索数据.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@RequestMapping( "list" )
	public String page(D dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params) 
	{
		return
				page(null == dto.getPager() ? 1 : dto.getPager().getPageNo(), dto, request, model, params);
	}
	
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
	public String page(@PathVariable int pageNo, D dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			service().queryPage( dto.getPager(pageNo), dto.getQuery() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	/**
	 * 添加数据表单.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@RequestMapping( value = "create", method = RequestMethod.GET )
	public String create(D dto, ModelMap model, @PathVariable Map<String, Object> params)
	{
		return 
				edit(null, dto, model, params);
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
	@RequestMapping( value = "create", method = RequestMethod.POST )
	public String create(D dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			dto.getBean().setId( null );
			service().persistence( dto.getBean() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			return REDIRECT_VIEW_CREATE( dto, model, params );
		}
		bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		return REDIRECT_VIEW_LIST( dto, model, params );
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
	@RequestMapping( value = "{id}", method = RequestMethod.GET )
	public String edit(@PathVariable String id, D dto, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if ( VerifyUtil.isNotBlank(id) ) {
				dto.setBean(service().get(id));
			}
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return VIEW_INPUT( dto, model, params );
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
	@RequestMapping( value = "{id}", method = RequestMethod.POST )
	public String modify(D dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		Object id = pathValue(params, "id");
		try {
			if( null == id || VerifyUtil.isBlank( id.toString() ) )
			{
				throw new Exception("更新数据ID不能为空！");
			}
			service().persistence( dto.getBean() );
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
	@RequestMapping( value = "del/{ids}" )
	public String delete(@PathVariable Object[] ids, D dto, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			if( VerifyUtil.isNotEmpty( ids ) )
			{
				for(Object id : ids )
				{
					service().delete( id );
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
	@RequestMapping( value = "remove/{ids}" )
	public String remove(@PathVariable Object[] ids, D dto, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			service().remove( ids );
			bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return REDIRECT_VIEW_LIST( dto, model, params );
	}
	
	/*
	 * =====================================================================================================================
	 * ======= Upload 同步文件上传 ==========================================================================================
	 * =====================================================================================================================
	 */
	
	/**
	 * 上传文件.
	 * 
	 * @param request MultipartHttpServletRequest
	 * @param savePath 文件保存路径.
	 * @param inputName input name.
	 * 
	 * @return FileBean / null
	 * 
	 * @throws FileException 
	 */
	protected FileBean uploadFile(HttpServletRequest request, String savePath, String inputName) throws FileException
	{
		if ( request instanceof MultipartHttpServletRequest ) {
			MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipart.getFile(inputName);
			return
					0 < multipartFile.getSize() ? UploadUtil.upload(multipartFile, savePath)
							: null;
		} 
		return null;
	}
	
	/**
	 * 上传文件.
	 * 
	 * @param request MultipartHttpServletRequest
	 * @param savePath 保存路径.
	 * @param inputName input name.
	 * 
	 * @return 文件uri / null.
	 * 
	 * @throws FileException 
	 */
	protected String uploadUri(HttpServletRequest request, String savePath, String inputName) throws FileException
	{		
		FileBean fileBean = uploadFile(request, savePath, inputName);
		return
				null != fileBean ? fileBean.getFileUri()
						: null;
	}
	
	
	
	/*
	 * =====================================================================================================================
	 * ======= URI AND VIEW 仿问URI 及VIEW端 URI  ===========================================================================
	 * =====================================================================================================================
	 */
	
	/**
	 * @return view uri 前缀.
	 */
	protected abstract String VIEW_PREFIX( Map<String, Object> params );
	
	/**
	 * @return redirect uri 前缀.
	 */
	protected String REDIRECT_VIEW_PREFIX( Map<String, Object> params ) 
	{
		return VIEW_PREFIX( params );
	}
	
	/**
	 * @return forward 列表页 URI.
	 */
	protected String VIEW_LIST( BaseCtrDto<L> dto, ModelMap model, Map<String, Object> params )
	{
		bind( dto, model, params );
		return
				VIEW_PREFIX( params ) + SPLIT + VIEW_LIST_BASE;
	}
	
	/**
	 * @return redirect 列表页 URI.
	 */
	protected String REDIRECT_VIEW_LIST( BaseCtrDto<L> dto, RedirectAttributes model, Map<String, Object> params )
	{
		bind( dto, model, params );
		return
				redirect( REDIRECT_VIEW_PREFIX( params ) + SPLIT + VIEW_LIST_BASE );
	}
	
	/**
	 * @return forward 表单 URI.
	 */
	protected String VIEW_INPUT( BaseCtrDto<L> dto, ModelMap model, Map<String, Object> params )
	{
		bind( dto, model, params );
		return
				VIEW_PREFIX( params ) + SPLIT + VIEW_INPUT_BASE;
	}
	
	/**
	 * @return redirect 添加表单 URI.
	 */
	protected String REDIRECT_VIEW_CREATE( BaseCtrDto<L> dto, RedirectAttributes model, Map<String, Object> params )
	{
		bind( dto, model, params );
		return
				redirect( REDIRECT_VIEW_PREFIX( params ) + SPLIT + VIEW_CREATE_BASE );
	}
	
	/**
	 * @return redirect 编辑表单 URI.
	 */
	protected String REDIRECT_VIEW_EDIT( BaseCtrDto<L> dto, RedirectAttributes model, Map<String, Object> params, Object id )
	{
		bind( dto, model, params );
		return redirect( REDIRECT_VIEW_PREFIX( params ) + SPLIT + String.valueOf( id ) );
	}
	
	protected abstract BaseService<L> service();
	
	protected abstract Logger log();
}
