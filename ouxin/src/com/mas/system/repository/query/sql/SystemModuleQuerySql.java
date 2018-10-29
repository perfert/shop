/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.query.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.core.domain.vo.StateVo;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.system.domain.entity.SystemModule;
import com.mas.system.repository.query.SystemModuleQueryDao;

/**
 * 系统模块 Query SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SystemModuleQuerySql extends QuerySql<SystemModule> implements SystemModuleQueryDao
{
	@Override
	public List<SystemModule> queryAll( StateVo state )
	{
		return super.queryAll( state );
	}

	@Override
	protected SimpleQuerySql querySql( SystemModule query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andLkWhereIfNotBlankValue( "NAME", query.getName() )
					.andLkWhereIfNotBlankValue( "ACCESS_PREFIX", query.getAccessPrefix() );
	}
	
	@Override
	protected String tableName()
	{
		return SystemModule.TABLE_NAME;
	}
}