/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.security.domain.entity.SecurityAuthorization;
import com.mas.security.repository.dao.SecurityAuthorizationDao;

/**
 * Security user authorization SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityAuthorizationSql extends CrudSql<SecurityAuthorization> implements SecurityAuthorizationDao
{
	@Override
	public int deleteAllByUserId( Object userId )
	{
		return
				getJdbcTemplate().update(
					"DELETE FROM " + tableName() + " WHERE USER_ID = ?"
					, new Object[] { userId }
					);
	}
	
	@Override
	protected InsertSql insertSql( SecurityAuthorization bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "SYSTEM_MODULE_ID", bean.getSystemModuleId() )
					.addField( "ROLE_ID", bean.getRoleId() )
					.addField( "USER_ID", bean.getUserId() );
	}

	@Override
	protected UpdateSql updateSql( SecurityAuthorization bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "ROLE_ID", bean.getRoleId() )
					.addField( "USER_ID", bean.getUserId() )
					.andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return SecurityAuthorization.TABLE_NAME;
	}
}