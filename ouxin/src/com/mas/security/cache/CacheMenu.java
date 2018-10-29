/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mas.common.verify.VerifyUtil;
import com.mas.core.service.ServiceException;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.domain.entity.SecurityResourceTree;
import com.mas.security.service.SecurityResourceService;

/**
 * 菜单缓存。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
public final class CacheMenu
{
	private final List<SecurityResourceTree> menuTree;
	private final Map<Object, SecurityResourceTree> menuTreeMap;
	
	private boolean hasChangeMenu = true;
	
	public void notifyHasChange()
	{
		this.hasChangeMenu = true;
	}
	
	/**
	 * @return 树状结构。
	 */
	public List<SecurityResourceTree> menuTree()
	{
		if( hasChangeMenu )
		{
			synchronized( this )
			{
				if( hasChangeMenu )
				{
					init();
				}
			}
		}
		return this.menuTree;
	}
	
	/**
	 * @return Map 结构。
	 */
	public Map<Object, SecurityResourceTree> menuTreeMap()
	{
		if( hasChangeMenu )
		{
			synchronized( this )
			{
				if( hasChangeMenu )
				{
					init();
				}
			}
		}
		return this.menuTreeMap;
	}
	
	@PostConstruct
	private void init()
	{
		try
		{
			this.menuTree.clear();
			this.menuTreeMap.clear();
			
			List<SecurityResource> resources = this.resourceService.queryAll( null );
			SecurityResourceTree node = null;
			
			for( SecurityResource resource : resources )
			{
				node = new SecurityResourceTree();
				node.setNode( resource );
				this.menuTreeMap.put( resource.getId(), node );
			}
			for( SecurityResource resource : resources )
			{
				if( VerifyUtil.isBlank( resource.getParentId() ) || "0".equals( resource.getParentId().toString() ) )
				{
					this.menuTree.add( this.menuTreeMap.get( resource.getId() ) );
				} else {
					node = this.menuTreeMap.get( resource.getParentId() );
					node.addChildren( 
							this.menuTreeMap.get( resource.getId() ).setParent( node )
							);
				}
			}
		}
		catch( ServiceException ex )
		{
			log.error( ex );
		}
		this.hasChangeMenu = false;
	}
	
	private CacheMenu()
	{
		super();
		
		this.menuTree = new ArrayList<SecurityResourceTree>();
		this.menuTreeMap = new HashMap<Object, SecurityResourceTree>();
	}
	
	@Autowired private SecurityResourceService resourceService;
	
	private static final Logger log = LogManager.getLogger( CacheMenu.class );
}