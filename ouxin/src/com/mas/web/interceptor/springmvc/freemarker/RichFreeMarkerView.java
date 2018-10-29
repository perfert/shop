/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.interceptor.springmvc.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.mas.security.domain.vo.Principal;

/**
 * 扩展 freemarker 标签.<br>
 * 支持 JSP 标签, Application、Session、Request、RequestParameters属性.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class RichFreeMarkerView extends FreeMarkerView {
    public static final String CURRENT_LOGIN_MEMBER = "current_login_member";

    public static final String WEICHAT_ID = "WEICHAT_ID";

    /** 部署路径 属性名称 */
    public static final String KEY_CONTEXT_PATH = "ctxPath";

    @Override
    protected void exposeModelAsRequestAttributes(Map<String, Object> model, HttpServletRequest request)
            throws Exception {
        String ctx = request.getContextPath();
        model.put(KEY_CONTEXT_PATH, ctx);
        model.put("my", request.getSession().getAttribute(CURRENT_LOGIN_MEMBER));
        model.put("wxid", request.getSession().getAttribute(WEICHAT_ID));
        model.put("appid", WeichatKey.APPID);

        Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
        if (null != principal) {
            model.put("manage", principal);
            principal.access(WebUtils.getPathWithinApplication(WebUtils.toHttp(request)));
        }

        super.exposeModelAsRequestAttributes(model, request);
    }
}