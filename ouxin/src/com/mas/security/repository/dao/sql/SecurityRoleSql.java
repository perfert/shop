/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.security.domain.entity.SecurityRole;
import com.mas.security.repository.dao.SecurityRoleDao;

/**
 * Security role SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityRoleSql extends CrudSql<SecurityRole> implements SecurityRoleDao
{
	@Override
	protected InsertSql insertSql( SecurityRole bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "SYSTEM_MODULE_ID", bean.getSystemModuleId() )
					.addField( "NAME", bean.getName() )
					.addField( "REMARK", bean.getRemark() );
	}

	@Override
	protected UpdateSql updateSql( SecurityRole bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "REMARK", bean.getRemark() )
					.andEqWhere( "ID", bean.getId() );
	}
	
	@Override
	protected String tableName()
	{
		return SecurityRole.TABLE_NAME;
	}
}