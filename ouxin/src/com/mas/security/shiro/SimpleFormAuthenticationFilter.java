/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.shiro;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 内置表单过滤器（认证）。</br> 使用 SHIRO 内置的表单过滤器来实现认证（登录）页面数据提交。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SimpleFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String account = getUsername(request);
        String password = getPassword(request);
        String host = getHost(request);

        return new SimpleUsernamePasswordToken(account, password, false, host, "");
    }

    @SuppressWarnings("all")
    @Override
    protected boolean onLoginSuccess(org.apache.shiro.authc.AuthenticationToken token, Subject subject,
            ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Object localObject;
        Session localSession = subject.getSession();
        HashMap localHashMap = new HashMap();
        Collection localCollection = localSession.getAttributeKeys();
        Iterator localIterator = localCollection.iterator();
        while (localIterator.hasNext()) {
            localObject = localIterator.next();
            localHashMap.put(localObject, localSession.getAttribute(localObject));
        }
        localSession.stop();
        localSession = subject.getSession();
        localIterator = localHashMap.entrySet().iterator();
        while (localIterator.hasNext()) {
            localObject = (Map.Entry) localIterator.next();
            localSession.setAttribute(((Map.Entry) localObject).getKey(), ((Map.Entry) localObject).getValue());
        }
        return super.onLoginSuccess(token, subject, servletRequest, servletResponse);
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        // Subject subject = getSubject(request, response);
        // long currentTime = System.currentTimeMillis();
        // long subjectTime =
        // subject.getSession().getStartTimestamp().getTime();
        //
        // System.out.println(currentTime - subjectTime);

        return super.executeLogin(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean authenticated = getSubject(request, response).isAuthenticated();
        boolean isLogin = isLoginRequest(request, response);

        return isLogin && authenticated ? false // 未退出又换其它账户登录时
                : super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest localHttpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse localHttpServletResponse = (HttpServletResponse) servletResponse;
        String str = localHttpServletRequest.getHeader("X-Requested-With");
        if ((str != null) && (str.equalsIgnoreCase("XMLHttpRequest"))) {
            localHttpServletResponse.addHeader("loginStatus", "accessDenied");
            localHttpServletResponse.sendError(403);
            return false;
        }
        return super.onAccessDenied(localHttpServletRequest, localHttpServletResponse);
    }
}