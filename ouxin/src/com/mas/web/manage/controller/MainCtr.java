/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mas.common.verify.VerifyUtil;
import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.security.cache.CacheMenu;
import com.mas.security.domain.vo.Principal;
import com.mas.web.springmvc.controller.BaseCtr;
import com.mas.web.springmvc.util.SpringUtils;

/**
 * Manage main.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( MainCtr.URI_PREFIX )
public class MainCtr extends BaseCtr
{
	public static final String URI_PREFIX = "manage";
	
	@Autowired  
    private static ResourceBundleMessageSource rms; 
    public static String getTextValue(String key) {  
        return rms.getMessage(key, null, null);  
    }  
	
	@RequestMapping( "main" )
	public String main(HttpServletRequest req, HttpServletResponse resp, ModelMap model, @PathVariable Map<String, Object> params)
	{
		return URI_PREFIX + "/main";
	}
	
	@RequestMapping( "unauthorized" )
	public String unauthorized(HttpServletRequest req, HttpServletResponse resp, ModelMap model, @PathVariable Map<String, Object> params)
	{
		return URI_PREFIX + "/unauthorized";
	}
	
	@RequestMapping( value = {"login", "", "/"} )
	public String login(HttpServletRequest req, HttpServletResponse resp, ModelMap model, @PathVariable Map<String, Object> params)
	{
		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
		if( UnknownAccountException.class.getName().equals( exceptionClassName ) )
		{
			bindMessage( model, new ErrorMsg( STATUS.error, "用户名或密码错误！" ) );
		} else if( DisabledAccountException.class.getName().equals( exceptionClassName ) )
		{
			bindMessage( model, new ErrorMsg( STATUS.error, "用户状态异常！" ) );
		} else if( IncorrectCredentialsException.class.getName().equals( exceptionClassName ) )
		{
			bindMessage( model, new ErrorMsg( STATUS.error, "用户名或密码错误！" ) );
		} else if ( VerifyUtil.isNotBlank( exceptionClassName ) ) {
			bindMessage( model, new ErrorMsg( STATUS.error, "异常错误！" ) );
		} else {
			Principal principal = (Principal)SecurityUtils.getSubject().getPrincipal();
			if( null != principal && VerifyUtil.isNotBlank( principal.getId() ) )
			{
				return redirect( URI_PREFIX + "/main" );
			}
		}
		return URI_PREFIX + "/login";
	}
	
	@Autowired
	private CacheMenu cacheMenu;
}