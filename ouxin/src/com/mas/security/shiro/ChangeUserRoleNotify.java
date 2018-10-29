/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.shiro;

/**
 * 更新用户角色通知接口。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface ChangeUserRoleNotify
{
	/**
	 * 通知角色已更新。
	 */
	void hasChangeUserRole();
}