/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.security.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.security.domain.entity.SecurityUser;
import com.mas.security.repository.dao.SecurityUserDao;

/**
 * Security user SQL.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class SecurityUserSql extends CrudSql<SecurityUser> implements SecurityUserDao
{
	@Override
	public boolean updatePassword( Object id, String password )
	{
		return 0 <
				getJdbcTemplate().update(
					"UPDATE " + tableName() + " SET PASSWORD = ? WHERE state <> -1 AND ID = ?"
					, new Object[] { password, id }
					);
	}
	
	@Override
	protected InsertSql insertSql( SecurityUser bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "SYSTEM_MODULE_ID", bean.getSystemModuleId() )
					.addField( "ROLE_ID", bean.getRoleId() )
					.addField( "ACCOUNT", bean.getAccount() )
					.addField( "PASSWORD", bean.getPassword() )
					.addField( "IS_SUPER_MANAGER", bean.getIsSuperManager() )
					.addField( "IS_SYSTEM_MODULE_MANAGER", bean.getIsSystemModuleManager() )
					.addField( "ERROR_LOGIN_COUNT", bean.getErrorLoginCount() )
					.addField( "LAST_LOGIN_IP", bean.getLastLoginIp() )
					.addField( "LAST_LOGIN_DATE", bean.getLastLoginDate() )
					.addField( "USER_STATE", bean.getUserState() );
	}

	@Override
	protected UpdateSql updateSql( SecurityUser bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "ROLE_ID", bean.getRoleId() )
					.addField( "IS_SUPER_MANAGER", bean.getIsSuperManager() )
					.addField( "IS_SYSTEM_MODULE_MANAGER", bean.getIsSystemModuleManager() )
					.addField( "ERROR_LOGIN_COUNT", bean.getErrorLoginCount() )
					.addField( "LAST_LOGIN_IP", bean.getLastLoginIp() )
					.addField( "LAST_LOGIN_DATE", bean.getLastLoginDate() )
					.addField( "USER_STATE", bean.getUserState() )
					.andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return SecurityUser.TABLE_NAME;
	}
}