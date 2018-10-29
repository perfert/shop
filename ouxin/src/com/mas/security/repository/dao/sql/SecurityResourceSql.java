/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao.sql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.repository.dao.SecurityResourceDao;

/**
 * Security resource SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityResourceSql extends CrudSql<SecurityResource> implements SecurityResourceDao
{
	@Override
	public Object persistence( SecurityResource entity )
	{
		if ( null == entity.getSystemModuleId() || VerifyUtil.isBlank( entity.getSystemModuleId().toString() ) ) 
		{
			log.error( "System module can not be empty or null."  );
			throw new RuntimeException( "System module can not be empty or null." );
		}
		
		if( VerifyUtil.isBlank( entity.getParentId() ) || "0".equals( entity.getParentId().toString() ) ) 
		{
			insertTop(entity);
		} else {
			insertChild(entity);
		}	
		return super.persistence( entity );
	}
	
	/**
	 * 删除节点及其所有子节点.
	 */
	@Override
	public boolean delete(Object id) 
	{
		SecurityResource bean = get( id );
		if( null != bean )
		{
			return deleteNode(bean, true);
		} else {
			return false;
		}
	}
	
	/**
	 * 仅仅删除节点下面的所有子节点.
	 */
	@Override
	public boolean deleteChildren(Object id) 
	{
		SecurityResource bean = get( id );
		if( null != bean )
		{
			return deleteNode(bean, false);
		} else {
			return false;
		}
	}
	
	/**
	 * 检索顶层最右边的一个节点
	 * 
	 * @param systemModuleId 系统模块ID
	 */
	@Override
	public SecurityResource searchLastNode(Object systemModuleId) 
	{
		String sql = "SELECT * FROM " + tableName() + " WHERE PARENT_ID = 0 AND SYSTEM_MODULE_ID = ? ORDER BY LFT DESC LIMIT 0, 1";
		
		return
				DataAccessUtils.uniqueResult(
						getJdbcTemplate().query(
								sql
								, new Object[] { systemModuleId }
								, new BeanPropertyRowMapper<SecurityResource>(SecurityResource.class)
								)
						);
	}
	
	@Override
	protected InsertSql insertSql( SecurityResource bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "SYSTEM_MODULE_ID", bean.getSystemModuleId() )
					.addField( "PARENT_ID", null == bean.getParentId() ? 0 : bean.getParentId() )
					.addField( "NAME", bean.getName() )
					.addField( "URI", bean.getUri() )
					.addField( "LFT", bean.getLft() )
					.addField( "RGT", bean.getRgt() )
					.addField( "IS_MENU", bean.getIsMenu() )
					.addField( "ICON", bean.getIcon() );
	}

	@Override
	protected UpdateSql updateSql( SecurityResource bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "URI", bean.getUri() )
					.addField( "IS_MENU", bean.getIsMenu() )
					.addField( "ICON", bean.getIcon() )
					.andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return SecurityResource.TABLE_NAME;
	}

	/**
	 * 添加顶层资源
	 * @param entity
	 */
	private void insertTop(SecurityResource entity)
	{
		entity.setParentId(0L);		// 顶层不能有 parent 节点.
		
		SecurityResource rightTreeInTopLayer = searchLastNode(entity.getSystemModuleId());
		
		if ( null == rightTreeInTopLayer )	// 空表, 直接添加为第一个节点
		{
			entity.setLft(1);
			entity.setRgt(2);
		} 
		else	// 添加为顶层最右节点
		{
			entity.setLft(rightTreeInTopLayer.getRgt() + 1);
			entity.setRgt(rightTreeInTopLayer.getRgt() + 2);
		}	
	}
	
	/**
	 * 添加子资源
	 * @param entity
	 */
	private void insertChild(SecurityResource entity){
		
		SecurityResource parent = this.get(entity.getParentId());
		if( null == parent ) 
		{
			log.error( "dot not exist ID: " + entity.getParentId() + " of parent."  );
			throw new RuntimeException( "dot not exist ID: " + entity.getParentId() + " of parent." );
		}
		
		entity.setLft(parent.getRgt());
		entity.setRgt(parent.getRgt() + 1);
		
		// 更新所有节点(右值)
		String lftQueryString = "UPDATE " + tableName() + " SET RGT = RGT + 2 WHERE SYSTEM_MODULE_ID = ? AND RGT >= ?";		
		getJdbcTemplate().update( lftQueryString, new Object[] { parent.getSystemModuleId(), parent.getRgt()} );
		
		// 更新所有节点左值
		String rgtQueryString = "UPDATE " + tableName() + " SET LFT = LFT + 2 WHERE SYSTEM_MODULE_ID = ? AND LFT > ?";
		getJdbcTemplate().update( rgtQueryString, new Object[] { parent.getSystemModuleId(), parent.getRgt() } );
	}
	
	/**
	 * 删除节点.
	 * 
	 * @param bean 数据对象
	 * @param deleteSelf 是否删除节点自身
	 */
	private boolean deleteNode(SecurityResource bean, final boolean deleteSelf)
	{		
		if ( null == bean.getLft() || 0 >= bean.getLft() || null == bean.getRgt() || 0 >= bean.getRgt() ) {
			bean = get( bean.getId() );
			if( null == bean ) {
				log.error( "Tree_deleteNode_notExist" );
				return false;
			}
		}
		
		String sql = "DELETE FROM " + tableName() + " WHERE SYSTEM_MODULE_ID = ?";			
		if ( deleteSelf ) {
			sql +=  " AND LFT >= ? AND RGT <= ?";
		} else {
			sql += " AND LFT > ? AND RGT < ?";
		}
		int count = getJdbcTemplate().update( sql, new Object[] { bean.getSystemModuleId(), bean.getLft(), bean.getRgt() } );
		
		afterDeleteNode(bean, deleteSelf);
		
		return 0 < count;
	}
	
	/**
	 * 删除节点之后操作.
	 * 
	 * @param bean 数据对象
	 * @param deleteSelf 是否删除节点自身
	 */
	private void afterDeleteNode(SecurityResource bean, final boolean deleteSelf)
	{
		int deleteNodSpace = (bean.getRgt() - bean.getLft() + ( deleteSelf ? 1 : -1 ));		// 所有将要删除的节点总占位数
		
		// 更新节点左值
		String lftQueryString = "UPDATE " + tableName() + " SET LFT = LFT - ? WHERE SYSTEM_MODULE_ID = ? AND LFT > ? ";
		getJdbcTemplate().update( lftQueryString, new Object[] { deleteNodSpace, bean.getSystemModuleId(), bean.getRgt() } );
				
		// 更新节点(右值)
		String rgtQueryString = "UPDATE " + tableName() + " SET RGT = RGT - ? WHERE  SYSTEM_MODULE_ID = ?";
		if ( deleteSelf ) {
			rgtQueryString += " AND RGT > ?";
		} else {
			rgtQueryString += " AND RGT >= ?";
		}
		getJdbcTemplate().update( rgtQueryString, new Object[] { deleteNodSpace, bean.getSystemModuleId(), bean.getRgt() } );
	}
	
	private static final Logger log = LogManager.getLogger( SecurityResourceSql.class );
}