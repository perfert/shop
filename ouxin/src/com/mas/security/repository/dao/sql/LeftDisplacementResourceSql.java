/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao.sql;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.dao.BaseSpringJdbcDao;
import com.mas.security.domain.entity.SecurityResource;

/**
 * Left displacement SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class LeftDisplacementResourceSql extends BaseSpringJdbcDao
{
	/**
	 * 移动节点。
	 * 
	 * @param displacementNode 需要移动的节点。
	 * @param referenceValue 参照值。
	 */
	public boolean displacement(SecurityResource displacementNode, int referenceValue)
	{
		if ( displacementNode.getLft().intValue() > referenceValue + 1 )			// 不能移动到其子节点中, 且移动节点与参照节点不能相同.
		{
			int affectNodeChangeCount = displacementNode.getRgt() - displacementNode.getLft() + 1;			// 影响的节点(移动节点到参照值间的所有节点)需要改变的左/右值.
			int displacementChangeCount = displacementNode.getLft() - referenceValue - 1;			// 移动节点及其所有子节点需要改变的值.
			
			updateRgt(displacementNode, referenceValue, affectNodeChangeCount);
			updateDisplacementNode(displacementNode, displacementChangeCount);
			updateLft(displacementNode, referenceValue, affectNodeChangeCount, displacementChangeCount);
			getJdbcTemplate().update(
					updateHql + "SET bean.PARENT_ID = ? WHERE bean.SYSTEM_MODULE_ID = ? AND bean.ID = ?"
					, new Object[] { null == displacementNode.getParentId() || VerifyUtil.isBlank( displacementNode.getParentId().toString() ) ? 0 : displacementNode.getParentId(), displacementNode.getSystemModuleId(), displacementNode.getId() }
					);
			return true;
		}
		return false;
	}
	
	/**
	 * 更新移动节点到参照值间的所有节点的右值.<br>
	 * 算法: 其右值 + 移动节点及其所有子节点的数量 * 2.<br>
	 * 范围: rgt > referenceValue (参照值) AND rgt < 移动节点的左值.
	 */
	private void updateRgt(SecurityResource displacementNode, int referenceValue, int affectNodeChangeCount)
	{
		String hql = updateHql + "SET bean.RGT = bean.RGT +:addRgt WHERE bean.SYSTEM_MODULE_ID =:systemModuleId AND bean.RGT >:gtRgt AND bean.RGT <:ltRgt";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put( "systemModuleId", displacementNode.getSystemModuleId() );
		values.put("addRgt", affectNodeChangeCount);
		values.put("gtRgt", referenceValue);
		values.put("ltRgt", displacementNode.getLft());
		update(hql, values);
	}
	
	/**
	 * 更新移动节点及其所有子节点的左右值.<br>
	 * 算法: 左/右值 - 移动节点的左值 - reference (参照值) - 1.<br>
	 * 范围: lft >= 移动节点的左值 AND rgt <= 移动节点的右值.
	 */
	private void updateDisplacementNode(SecurityResource displacementNode, int displacementChangeCount)
	{
		String hql = updateHql + "SET bean.LFT = bean.LFT -:minusLft, bean.RGT = bean.RGT -:minusRgt WHERE bean.SYSTEM_MODULE_ID =:systemModuleId AND bean.LFT >=:geLft AND bean.RGT <=:leRgt";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put( "systemModuleId", displacementNode.getSystemModuleId() );
		values.put("minusLft", displacementChangeCount);
		values.put("minusRgt", displacementChangeCount);
		values.put("geLft", displacementNode.getLft());
		values.put("leRgt", displacementNode.getRgt());
		update(hql, values);
	}
	
	/**
	 * 更新移动节点到参照值间的所有节点的左值.<br>
	 * 算法: 其左值 + 移动节点及其所有子节点的数量 * 2.<br>
	 * 范围: lft > referenceValue (参照值) AND lft < 移动节点的左值. AND rgt > 移动节点最终位置的右值(即移动节点右值 - displacementChangeCount)
	 */
	private void updateLft(SecurityResource displacementNode, int referenceValue, int affectNodeChangeCount, int displacementChangeCount)
	{
		String hql = updateHql + "SET bean.lft = bean.lft +:addLft WHERE bean.SYSTEM_MODULE_ID =:systemModuleId AND bean.lft >:gtLft AND bean.lft <:ltLft AND bean.rgt >:gtRgt";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put( "systemModuleId", displacementNode.getSystemModuleId() );
		values.put("addLft", affectNodeChangeCount);
		values.put("gtLft", referenceValue);
		values.put("ltLft", displacementNode.getLft());
		values.put("gtRgt", displacementNode.getRgt() - displacementChangeCount);
		update(hql, values);
	}
	
	/**
	 * 执行更新
	 */
	private void update(String hqlString, Map<String, Object> values)
	{
		getNamedParameterJdbcTemplate().update(hqlString, values);
	}
	
	private final String updateHql = "UPDATE " + SecurityResource.TABLE_NAME + " bean ";
}