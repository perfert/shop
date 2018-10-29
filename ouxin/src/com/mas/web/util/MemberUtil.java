/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.util;

import javax.servlet.http.HttpServletRequest;

import com.mas.user.domain.entity.Member;
import com.mas.web.interceptor.springmvc.freemarker.RichFreeMarkerView;

/**
 * 会员工具。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public final class MemberUtil
{
	/**
	 * 获取当前登录会员。
	 * 
	 * @return {@link Member} or null
	 */
	public static Member getMember( HttpServletRequest request )
	{
		return (Member) request.getSession().getAttribute( RichFreeMarkerView.CURRENT_LOGIN_MEMBER );
	}
	
	private MemberUtil() { super(); }
}