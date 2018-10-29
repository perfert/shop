/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.shiro;

import java.security.SecurityPermission;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.mas.common.file.util.PathUtil;
import com.mas.common.verify.VerifyUtil;
import com.mas.security.domain.entity.SecurityPermissions;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.service.SecurityPermissionsService;
import com.mas.security.service.SecurityResourceService;

/**
 * 自定义filterChainDefinitions，从数据库加载验证规则。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class SimpleFilterChainDefinitions
{
	public static final String PREMISSION_STRING = "perms[{0}]";	// 资源结构格式
	public static final String ROLE_SINGLE_STRING = "role[{0}]";	// 单角色结构格式
	public static final String ROLE_STRING = "roles[\"{0}\"]";	// 多角色结构格式
	
	private String filterChainDefinitions; // filterChainDefinitions对默认的 URI 过滤定义

	void resetFilterChainDefinitions()
	{
		AbstractShiroFilter shiroFilter = null;
		try
		{
			shiroFilter = ( AbstractShiroFilter ) shiroFilterFactoryBean.getObject();
		}
		catch( Exception ex )
		{
			log.error( "Reset filter chain definitions error: " + ex.getMessage() );
		}
		// 获取 filter 管理器
		PathMatchingFilterChainResolver filterChainResolver = ( PathMatchingFilterChainResolver ) shiroFilter.getFilterChainResolver();
		DefaultFilterChainManager manager = ( DefaultFilterChainManager ) filterChainResolver.getFilterChainManager();
		
//		synchronized( this )
//		{
			// 清空初始验证规则
			manager.getFilterChains().clear();
			shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
			
			// 重新构建生成新规则
			Section chains = obtainPermission();
			if( ! CollectionUtils.isEmpty( chains ) )
			{
				for( Map.Entry<String, String> entry : chains.entrySet() )
				{
					String url = entry.getKey();
					String chainDefinition = entry.getValue();
					manager.createChain( url, chainDefinition );
				}
			}
		//}
		log.info( "Reset filter chain definitions success." );
	}

	public void setFilterChainDefinitions( String filterChainDefinitions )
	{
		this.filterChainDefinitions = filterChainDefinitions;
	}

	@PostConstruct
	protected void intiPermission()
	{
		shiroFilterFactoryBean.setFilterChainDefinitionMap( obtainPermission() );
		log.info( "initialize shiro permission success." );
	}
	
	
	/**
	 * 读取 XML 配置的默认规则，并动态加载自定义验证规则。
	 * 
	 * 1.不要重复配置
	 * 2.要配置所有权限.因为没有加载资源
	 * 
	 * @return {@link Section}
	 */
	private Section obtainPermission()
	{
		Ini ini = new Ini();
		ini.load( filterChainDefinitions ); // 加载XML配置的默认规则
		Ini.Section section = ini.getSection( IniFilterChainResolverFactory.URLS );
		if( CollectionUtils.isEmpty( section ) )
		{
			section = ini.getSection( Ini.DEFAULT_SECTION_NAME );
		}
		// 循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
		// 里面的键就是链接URL,值就是存在什么条件才能访问该链接
		Map<String, List<String>> resourceMap = new HashMap<String, List<String>>();
		List<SecurityPermissions> permissionses = securityPermissionsService.queryAll( null );
		for( SecurityPermissions permissions : permissionses )
		{
			if( VerifyUtil.isNotBlank( permissions.getResourceUri() ) )
			{
				String uri = permissions.getResourceUri().startsWith( "/" ) ? permissions.getResourceUri() : "/" + permissions.getResourceUri();
				if( resourceMap.containsKey( uri ) )
				{
					resourceMap.get( uri ).add( permissions.getSystemModuleId() + "-" + permissions.getRoleId() );
				}
				else
				{
					List<String> value = new ArrayList<String>();
					value.add( permissions.getSystemModuleId() + "-" +permissions.getRoleId() );
					resourceMap.put( uri, value );
				}
			}
		}
		for( String uri : resourceMap.keySet() )
		{
			List<String> roles = resourceMap.get( uri );
			String roleStr = "";
			for( int i = 0; i < roles.size(); i++ )
			{
				roleStr += roles.get( i );
				if( i + 1 < roles.size() )
				{
					roleStr += ",";
				}
			}
			section.put( uri, MessageFormat.format( ROLE_STRING, roleStr ) );
		}
		log.info( "Obtain permission success." );
		return section;
	}

	@Autowired private ShiroFilterFactoryBean shiroFilterFactoryBean;
	@Autowired private SecurityPermissionsService securityPermissionsService;
	@Autowired private SecurityResourceService resourceService;
	
	private static final Logger log = LogManager.getLogger( SimpleFilterChainDefinitions.class );
}