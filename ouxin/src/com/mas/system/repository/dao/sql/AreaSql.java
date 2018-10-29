/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.system.domain.entity.Area;
import com.mas.system.repository.dao.AreaDao;

/**
 * 地区。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class AreaSql extends CrudSql<Area> implements AreaDao
{
	@Override
	protected InsertSql insertSql( Area bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "CODE", bean.getCode() )
					.addField( "CITY_CODE", bean.getCityCode() );
	}

	@Override
	protected UpdateSql updateSql( Area bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "CODE", bean.getCode() )
					.addField( "CITY_CODE", bean.getCityCode() )
					.andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return Area.TABLE_NAME;
	}
}