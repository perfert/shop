/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.common.orm.util.Page;
import com.mas.common.verify.VerifyUtil;
import com.mas.core.domain.vo.StateVo;
import com.mas.core.service.ServiceException;
import com.mas.core.tree.domain.vo.TreeNodePosition;
import com.mas.security.cache.CacheMenu;
import com.mas.security.domain.entity.SecurityResource;
import com.mas.security.repository.dao.SecurityResourceDao;
import com.mas.security.repository.dao.sql.LeftDisplacementResourceSql;
import com.mas.security.repository.dao.sql.RightDisplacementResourceSql;
import com.mas.security.repository.query.SecurityResourceQueryDao;
import com.mas.security.shiro.ChangePermissionsNotify;

/**
 * Security resource service.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class SecurityResourceService
{
	/**
	 * 根据ID，获取数据对象。
	 * 
	 * @param id 数据对象ID。
	 * 
	 * @return {@link SecurityResource} or null。
	 * 
	 * @throws ServiceException 
	 */
	public SecurityResource get(Object id) throws ServiceException
	{
			return dao.get( id );
	}
	
	/**
	 * 持久化数据对象。
	 * 
	 * @param bean 数据对象。
	 * 
	 * @return 持久化数据的数据ID。
	 * 
	 * @throws ServiceException 
	 */
	public Object persistence(SecurityResource bean) throws ServiceException
	{
			Object result = dao.persistence( bean );
			cacheMenu.notifyHasChange();
			if( VerifyUtil.isNotBlank( bean.getId() ) && ! "0".equals( bean.getId().toString() ) )
				changePermissionsNotify.hasChangePermissions();
			return result;
	}
	
	/**
	 * 移动节点。
	 * 
	 * @param displacementNode 需要移动的节点。
	 * @param referenceNode 参照节点。
	 * @param position 终点相对于参照节点的位置。
	 * 
	 * @throws ServiceException 
	 */
	public boolean displacement(Long displacementNodeId, Long referenceNodeId, TreeNodePosition position) throws ServiceException 
	{
		
		SecurityResource displacementNode = get(displacementNodeId);
		SecurityResource referenceNode = get(referenceNodeId);
		if( null == displacementNode || null == referenceNode ){
			return false;
		}
		
		int referenceValue = -1;
		
		// 左边位置
		if( TreeNodePosition.LEFT == position ) 
		{
			referenceValue = referenceNode.getLft() - 1;
			displacementNode.setParentId(referenceNode.getParentId());
		}
		else if( TreeNodePosition.RIGHT == position ) 
		{
			referenceValue = referenceNode.getRgt();
			displacementNode.setParentId(referenceNode.getParentId());
		}
		else if( TreeNodePosition.LEFTDOWN == position ) 
		{
			referenceValue = referenceNode.getLft();
			displacementNode.setParentId(referenceNode.getId());
		}
		else if( TreeNodePosition.RIGHTDOWN == position )
		{
			referenceValue = referenceNode.getRgt() - 1;
			displacementNode.setParentId(referenceNode.getId());
		} else {
			return false;
		}
		
			if( canDisplacement(displacementNode, referenceNode) ) {
				if( -1 != referenceValue ) {
					boolean displacement = false;
					if ( displacementNode.getLft().intValue() < referenceValue ) {
						displacement = rightDisplacementResourceDao.displacement(displacementNode, referenceValue);
					} else if ( displacementNode.getLft().intValue() > referenceValue ) {
						displacement = leftDisplacementResourceDao.displacement(displacementNode, referenceValue);
					}
					if( displacement ) {
						cacheMenu.notifyHasChange();
						changePermissionsNotify.hasChangePermissions();
					}
					return displacement;
				}
			}
		return false;
	}

	/**
	 * 物理删除节点及其所有子节点。
	 * 
	 * @return 物理删除数据对象的数量。
	 * 
	 * @throws ServiceException 
	 */
	public boolean delete( Object id ) throws ServiceException
	{
			if( dao.delete( id ) )
			{
				cacheMenu.notifyHasChange();
				changePermissionsNotify.hasChangePermissions();
				return true;
			} else {
				return false;
			}
	}

	/**
	 * 仅仅删除节点下面的所有子节点。（物理删除）
	 * 
	 * @return 物理删除数据对象的数量。
	 * 
	 * @throws ServiceException 
	 */
	public boolean deleteChildren( Object id ) throws ServiceException
	{
			if( dao.deleteChildren( id ) )
			{
				cacheMenu.notifyHasChange();
				changePermissionsNotify.hasChangePermissions();
				return true;
			} else {
				return false;
			}
	}
	
	/**
	 * @param query 检索条件。
	 * 
	 * @return 检索所有数据对象。
	 */
//	public List<SecurityResource> queryAlIntegerl( SecurityResource query )
//	{
//		return queryDao.queryAll( query );
//	}

	/**
	 * 分页获取数据对象。
	 * 
	 * @param page 分页对象。
	 * @param state 数据状态（为空则忽略数据状态检索）。
	 * 
	 * @throws ServiceException 
	 */
	public void queryPage( Page page, StateVo state ) throws ServiceException
	{
			queryDao.queryPage( page, state );
	}

	/**
	 * 分页获取数据对象。
	 * 
	 * @param page 分页对象。
	 * @param query 检索条件。
	 * 
	 * @throws ServiceException 
	 */
	public void queryPage( Page page, SecurityResource query ) throws ServiceException
	{
			queryDao.queryPage( page, query );
	}
	
	/**
	 * 检索所有节点
	 * 
	 * @param query 检索条件。
	 * 
	 * @param {@link List}
	 * 
	 * @throws ServiceException 
	 */
	public List<SecurityResource> queryAll(SecurityResource query) throws ServiceException
	{
			return queryDao.queryAll( query );
	}
	
	/**
	 * 检索所有节点（以树状显示结果）。
	 * 
	 * @param query 检索条件。
	 * 
	 * @param {@link List}
	 */
//	public List<SecurityResourceTree> queryTree(SecurityResource query)
//	{
//		return queryDao.queryTree( query );
//	}
	
	public List<SecurityResource> queryByRole( Object roleId )
	{
		return queryDao.queryByRole( roleId );
	}
	
	public List<SecurityResource> queryThisAndParentBy( Object... resourceIds )
	{
		return queryDao.queryThisAndParentBy( resourceIds );
	}
	
	/**
	 * 验证节点是否可以移动.
	 * 
	 * @param displacementDto 树节点的移动 VO.
	 * 
	 * @return true / false
	 */
	private boolean canDisplacement(SecurityResource displacementNode, SecurityResource referenceNode)
	{		
		if ( referenceNode.getLft().intValue() >= displacementNode.getLft().intValue() 
				&& referenceNode.getRgt().intValue() <= displacementNode.getRgt().intValue() )
		{
			return  false; // 父节点不能直接移动成其子节点.
		}
		if ( referenceNode.getId().equals(displacementNode.getId()) ) 			// 移动节点与参照节点为同一个节点. 直接返回 false.
		{
			return false;
		}
		return true;
	}
	
	@Autowired
	private SecurityResourceDao dao;
	@Autowired
	private SecurityResourceQueryDao queryDao;
	@Autowired
	private LeftDisplacementResourceSql leftDisplacementResourceDao;
	@Autowired
	private RightDisplacementResourceSql rightDisplacementResourceDao;
	@Autowired
	private CacheMenu cacheMenu;
	
	@Autowired private ChangePermissionsNotify changePermissionsNotify;
}