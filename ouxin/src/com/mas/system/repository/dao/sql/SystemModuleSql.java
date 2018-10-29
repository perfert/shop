/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.system.domain.entity.SystemModule;
import com.mas.system.repository.dao.SystemModuleDao;

/**
 * 系统模块 SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SystemModuleSql extends CrudSql<SystemModule> implements SystemModuleDao
{
	@Override
	protected InsertSql insertSql( SystemModule bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "ACCESS_PREFIX", bean.getAccessPrefix() )
					.addField( "REMARK", bean.getRemark() );
	}

	@Override
	protected UpdateSql updateSql( SystemModule bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "ACCESS_PREFIX", bean.getAccessPrefix() )
					.addField( "REMARK", bean.getRemark() )
					.andEqWhere( "ID", bean.getId() );
	}
	
	@Override
	protected String tableName()
	{
		return SystemModule.TABLE_NAME;
	}
}