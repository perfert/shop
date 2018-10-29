/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.springmvc.controller;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mas.common.reflection.ReflectionException;
import com.mas.common.reflection.ReflectionUtil;
import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.core.domain.entity.Entity;
import com.mas.security.cache.CacheMenu;
import com.mas.web.springmvc.controller.dto.BaseCtrDto;

/**
 * Abstract spring MVC controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public abstract class BaseCtr{
	// ModelMap Key Name
	protected static final String KEY_BEAN = "bean";
	protected static final String KEY_PAGE = "pager";
	protected static final String KEY_SEARCH = "query";
	protected static final String DTO = "redirectDto";
	
	protected static final String CACHE_MENU_TREE = "cache_menu_tree";
	protected static final String CACHE_MENU_MAP = "cache_menu_map";
	
	protected static final String CACHE_CATEGORY_TREE = "cache_category_tree";
	protected static final String CACHE_CATEGORY_MAP = "cache_category_map";
	
	protected static final String CACHE_BRAND_MAP = "cache_productType_map";
	
	protected static final String CACHE_PRODUCT_TYPE_MAP = "cache_product_type_map";
	
	protected static final String CACHE_MEMBER_LEVEL_MAP = "cache_member_level_map";
	
	// View File Name
	protected static final String VIEW_LIST_BASE = "list";
	protected static final String VIEW_INPUT_BASE = "input";
	protected static final String VIEW_CREATE_BASE = "create";
	protected static final String SPLIT = "/";
	protected static final String VIEW_INDEX_BASE = "index";
	
	// error or exception URI NAME
	protected static final String URI_404 = "error/404";
	protected static final String URI_EXCEPTION = "error/exception";

	/**
	 * 根据 URI 名称, 获取 URI 里的值.
	 * 
	 * @param params {@link PathVariable} 参数集
	 * @param name URI 名称.
	 * 
	 * @return String / null.
	 */
	protected String pathValue( Map<String, Object> params, String name )
	{
		Object result = params.get( name );
		return null != result ? result.toString() : null;
	}

	/**
	 * 绑定数据到 request.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 参数模型.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return 参数模型.
	 */
	protected <L extends Entity> ModelMap bind( BaseCtrDto<L> dto, RedirectAttributes model, Map<String, Object> params )
	{
		ModelMap realModel = bind( dto, ( ModelMap ) model.getFlashAttributes(), params );
		realModel.addAttribute( DTO, dto );
		return realModel;
		/*
		 * attrs.addFlashAttribute("bean", dto.getBean()) .addFlashAttribute("page", dto.getPage()) .addFlashAttribute("search", dto.getSearch());
		 */
	}

	/**
	 * 绑定数据到 request.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 参数模型.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return 参数模型.
	 */
	protected <L extends Entity> ModelMap bind( BaseCtrDto<L> dto, ModelMap model, Map<String, Object> params )
	{
		if( null != dto )
		{
			if( null != dto.getBean() ) model.addAttribute( KEY_BEAN, dto.getBean() );
			if( null != dto.getPager() ) model.addAttribute( KEY_PAGE, dto.getPager() );
			if( null != dto.getQuery() ) model.addAttribute( KEY_SEARCH, dto.getQuery() );
			bindDto( dto, model );
		}
		if( null != params && 0 < params.size() )
		{
			String key = null;
			for( Iterator<String> it = params.keySet().iterator(); it.hasNext(); )
			{
				key = it.next();
				model.addAttribute( key, params.get( key ) );
			}
		}
		model.addAttribute( CACHE_MENU_TREE, cacheMenu.menuTree() );
		model.addAttribute( CACHE_MENU_MAP, cacheMenu.menuTreeMap() );
		return model;
	}
	
	/**
	 * 绑定提示信息。
	 * 
	 * @param dto Controller DTO Context.
	 * @param msg {@link ErrorMsg }
	 * 
	 * @return 参数模型.
	 */
	protected <L extends Entity> ModelMap bindMessage( ModelMap model, ErrorMsg msg )
	{
		if ( null != model ) model.addAttribute(ErrorMsg.KEY, msg);
		return model;
	}
	
	/**
	 * 绑定提示信息。
	 * 
	 * @param dto Controller DTO Context.
	 * @param msg {@link ErrorMsg }
	 * 
	 * @return 参数模型.
	 */
	protected <L extends Entity> ModelMap bindMessage( RedirectAttributes model, ErrorMsg msg )
	{
		return bindMessage( ( ModelMap ) model.getFlashAttributes(), msg );
	}

	/**
	 * 绑定DTO属性值.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 参数模型.
	 */
	private void bindDto( BaseCtrDto<?> dto, ModelMap model )
	{
		Field[] fields = dto.getClass().getDeclaredFields();
		String fieldName = null;
		Object fieldValue = null;
		for( Field field : fields )
		{
			fieldName = field.getName();
			try
			{
				if( ! "serialVersionUID".equals( fieldName ) )
					fieldValue = ReflectionUtil.invokeGetterMethod( dto, fieldName );
			}
			catch( ReflectionException e )
			{
				e.printStackTrace();
			}
			if( null != fieldValue ) model.addAttribute( fieldName, fieldValue );
		}
	}

	/**
	 * @param uri 最终 uri.
	 * 
	 * @return forward 转发 uri.
	 */
	protected String forward( String uri )
	{
		return "forward:/" + uri;
	}

	/**
	 * @param uri 最终 uri.
	 * 
	 * @return redirect 转发 uri.
	 */
	protected String redirect( String uri )
	{
		return "redirect:/" + (uri.startsWith( "/" ) || uri.startsWith( "\\" ) ? uri.substring( 1 ) : uri );
	}

	/**
	 * 处理 Exception.
	 * 
	 * @param ex Exception.
	 * @param log {@link Logger}
	 * @param model redirect model.
	 */
	 protected void handlerException(Exception ex, Logger log, RedirectAttributes model)
	 {
		 handlerException(ex, log, (ModelMap ) model.getFlashAttributes());
	 }
	/**
	 * 处理 Exception.
	 * 
	 * @param ex Exception.
	 * @param log {@link Logger}
	 * @param model 参数Model.
	 */
	 protected void handlerException(Exception ex, Logger log, ModelMap model)
	 {
		 String message = ex.getMessage();
		 log.error( message );
//	
//		 if ( ! (ex instanceof ServiceException ) && ! ( ex instanceof RepositoryException ) )
//		 {
//			 if ( ex instanceof NullPointerException ) message = Message.getMessage(getI18N(), "null_exception");
//			 new Log4j(log, message).error();
//			
//			 message = Message.getMessage(getI18N(), "exception");
//			 ex.printStackTrace();
//		 }
		 bindMessage( model, new ErrorMsg(STATUS.error, message, null) );
		// if ( null != model ) model.addAttribute(ErrorMsg.KEY, new ErrorMsg(STATUS.error, message, null));
	 }
	private Pattern p = Pattern.compile( "\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}" );

	protected String replaceUrlParameters( String path, Map<String, Object> params )
	{
		Matcher m = p.matcher( path );
		StringBuffer sb = new StringBuffer();
		while( m.find() )
		{
			String match = m.group();
			if( match.startsWith( "{" ) && match.endsWith( "}" ) )
			{
				String key = m.group( 1 );
				if( null != params.get( key ) ) m.appendReplacement( sb, params.get( key ).toString() );
			}
		}
		m.appendTail( sb );
		return sb.toString();
	}
	
	@Autowired protected CacheMenu cacheMenu;
}