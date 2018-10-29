/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import com.mas.common.verify.VerifyUtil;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.domain.entity.SecurityResourceTree;

/**
 * Security subject.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class Principal implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private final String id;
	private final String account;
	
	private final boolean isSuperManager;
	
	private final List<SecurityResourceTree> menuTree;
	private final Map<String, SecurityResourceTree> menuTreeMap;
	private final Map<String, SecurityResourceTree> uriMap;
	private final SimpleAuthorizationInfo authorizationInfo;
	
	private boolean hasChangeRole = true;
	
	public String getId()
	{
		return id;
	}
	public String getAccount()
	{
		return account;
	}
	public List<SecurityResourceTree> getMenuTree()
	{
		return this.menuTree;
	}
	public Map<String, SecurityResourceTree> getMenuTreeMap()
	{
		return this.menuTreeMap;
	}
	
	public void access(String path)
	{
		if( VerifyUtil.isNotBlank( path ) && ! "#".equals( path ) )
		{
			String realPath = path.startsWith( "/" ) ? path.substring( 1 ) : path;
			if( this.uriMap.containsKey( realPath ) )
			{
				SecurityResourceTree menu = this.uriMap.get( realPath );
				if( null != menu )
				{
					for( SecurityResourceTree tree : menuTree )
					{
						tree.setMenuCheck( false );
					}
					menu.setMenuCheck( true );
				}
			}
		}
	}
	
	public boolean isHasChangeRole()
	{
		return hasChangeRole;
	}
	public void setHasChangeRole( boolean hasChangeRole )
	{
		this.hasChangeRole = hasChangeRole;
	}
	public SimpleAuthorizationInfo getAuthorizationInfo()
	{
		return authorizationInfo;
	}
	
	public boolean isSuperManager()
	{
		return isSuperManager;
	}
	
	public void reLoadMenu( List<SecurityResource> resources )
	{
		this.menuTree.clear();
		this.menuTreeMap.clear();
		this.uriMap.clear();
		
		SecurityResourceTree node = null;
		
		for( SecurityResource resource : resources )
		{
			node = new SecurityResourceTree();
			node.setNode( resource );
			this.menuTreeMap.put( resource.getId().toString(), node );
			if( VerifyUtil.isNotBlank( resource.getUri() ) && ! "#".equals( resource.getUri() ) ) uriMap.put( resource.getUri(), node );
		}
		for( SecurityResource resource : resources )
		{
			if( VerifyUtil.isBlank( resource.getParentId() ) || "0".equals( resource.getParentId().toString() ) )
			{
				this.menuTree.add( this.menuTreeMap.get( resource.getId().toString() ) );
			} else {
				node = this.menuTreeMap.get( resource.getParentId().toString() );
				node.addChildren( 
						this.menuTreeMap.get( resource.getId().toString() ).setParent( node )
						);
			}
		}
		for( SecurityResourceTree tree : this.menuTree )
		{
			if( tree.getNode().getIsMenu() ) tree.setIsMenu( tree.getNode().getIsMenu() );
		}
	}
	
	public Principal(String id, String account, boolean isSuperManager, List<SecurityResource> resources)
	{
		this.id = id;
		this.account = account;
		this.isSuperManager = isSuperManager;
		this.authorizationInfo = new SimpleAuthorizationInfo();
		this.menuTree = new ArrayList<SecurityResourceTree>();
		this.menuTreeMap = new HashMap<String, SecurityResourceTree>();
		this.uriMap = new HashMap<String, SecurityResourceTree>();
		
		reLoadMenu( resources );
	}
}
