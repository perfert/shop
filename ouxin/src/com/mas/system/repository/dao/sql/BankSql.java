/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.system.domain.entity.Bank;
import com.mas.system.repository.dao.BankDao;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;

/**
 * 
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class BankSql extends CrudSql<Bank> implements BankDao
{

	@Override
	protected InsertSql insertSql( Bank bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "NAME", bean.getName() );
	}

	@Override
	protected UpdateSql updateSql( Bank bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "NAME", bean.getName() )
					.andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return Bank.TABLE_NAME;
	}
}
