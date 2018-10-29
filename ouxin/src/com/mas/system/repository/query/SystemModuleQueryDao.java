/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.query;

import java.util.List;

import com.mas.core.domain.vo.StateVo;
import com.mas.core.repository.query.QueryDao;
import com.mas.system.domain.entity.SystemModule;

/**
 * 系统模块 Query DAO.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface SystemModuleQueryDao extends QueryDao<SystemModule>
{
	/**
	 * @param state 数据状态（为空则忽略数据状态检索）。
	 * 
	 * @return 检索所有数据对象。
	 */
	List<SystemModule> queryAll(StateVo state);
}