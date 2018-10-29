/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.shiro;

import java.util.Collection;

import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * CustomModularRealmAuthorizer 将遍历其内部的Realm 集合，并按迭代顺序与每一个进行交互。</br> 
 * 1：如果Realm 自己实现了Authorizer 接口，它的各个Authorizer方法将被调用。</br> 
 * 2：如果Realm 不实现Authorizer 接口，它会被忽略。</br>
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SimpleModularRealmAuthorizer extends ModularRealmAuthorizer
{
	@Override
	public boolean isPermittedAll( PrincipalCollection principals, String... permissions )
	{
		assertRealmsConfigured();
		
		if( permissions != null && permissions.length > 0 )
		{
			for( String perm : permissions )
			{
				if( isPermitted( principals, perm ) )
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isPermittedAll( PrincipalCollection principals, Collection<Permission> permissions )
	{
		assertRealmsConfigured();
		
		if( permissions != null && !permissions.isEmpty() )
		{
			for( Permission permission : permissions )
			{
				if( isPermitted( principals, permission ) )
				{
					return true;
				}
			}
		}
		return false;
	}
}
