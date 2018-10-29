/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.security.domain.entity.SecurityPermissions;
import com.mas.security.repository.dao.SecurityPermissionsDao;

/**
 * Security permissions SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityPermissionsSql extends CrudSql<SecurityPermissions> implements SecurityPermissionsDao
{
	@Override
	public int deleteAllByRoleId( Object roleId )
	{
		return
				getJdbcTemplate().update(
					"DELETE FROM " + tableName() + " WHERE ROLE_ID = ?"
					, new Object[] { roleId }
					);
	}
	
	@Override
	protected InsertSql insertSql( SecurityPermissions bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "SYSTEM_MODULE_ID", bean.getSystemModuleId() )
					.addField( "ROLE_ID", bean.getRoleId() )
					.addField( "RESOURCE_ID", bean.getResourceId() );
	}

	@Override
	protected UpdateSql updateSql( SecurityPermissions bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "ROLE_ID", bean.getRoleId() )
					.addField( "RESOURCE_ID", bean.getResourceId() )
					.andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return SecurityPermissions.TABLE_NAME;
	}
}