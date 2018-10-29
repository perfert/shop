/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.shiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.mas.security.domain.vo.Principal;

/**
 * Permission filter.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SimplePermissionsAuthorizationFilter extends PermissionsAuthorizationFilter implements
        ChangePermissionsNotify {
    private boolean hasChangePermissions = false;

    @Override
    public void hasChangePermissions() {
        hasChangePermissions = true;
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws IOException {
        // return super.isAccessAllowed( request, response, mappedValue );
        Subject subject = getSubject(request, response);
        Principal principal = (Principal) subject.getPrincipal();
        if (null != principal && principal.isSuperManager()) {
            return true;
        }

        String[] perms = (String[]) mappedValue;

        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (subject.hasRole(perms[0])) {
                    return true;
                }
            } else {
                /*
                 * for (String perm : perms) { if (subject.hasRole(perm)) {
                 * return true; } }
                 */

                String[] arrayOfString1;
                int j = (arrayOfString1 = perms).length;
                for (int i = 0; i < j; i++) {
                    String perm = arrayOfString1[i];
                    if (subject.hasRole(perm)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        if (hasChangePermissions) {
            synchronized (this) {
                if (hasChangePermissions) {
                    filterChainDefinitions.resetFilterChainDefinitions();
                    hasChangePermissions = false;
                }
            }
        }
        return super.preHandle(request, response);
    }

    @Autowired
    private SimpleFilterChainDefinitions filterChainDefinitions;
}