/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.system.domain.entity.Province;
import com.mas.system.repository.dao.ProvinceDao;

/**
 * 省。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class ProvinceSql extends CrudSql<Province> implements ProvinceDao
{
	@Override
	protected InsertSql insertSql( Province bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "CODE", bean.getCode() );
	}

	@Override
	protected UpdateSql updateSql( Province bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "CODE", bean.getCode() )
					.andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return Province.TABLE_NAME;
	}
}