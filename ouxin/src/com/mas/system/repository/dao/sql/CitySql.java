/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.system.domain.entity.City;
import com.mas.system.repository.dao.CityDao;

/**
 * 市。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class CitySql extends CrudSql<City> implements CityDao
{
	@Override
	protected InsertSql insertSql( City bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "CODE", bean.getCode() )
					.addField( "PROVINCE_CODE", bean.getProvinceCode() );
	}

	@Override
	protected UpdateSql updateSql( City bean )
	{
		return
				new UpdateSql( tableName() )
					.addField( "NAME", bean.getName() )
					.addField( "CODE", bean.getCode() )
					.addField( "PROVINCE_CODE", bean.getProvinceCode() )
					.andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return City.TABLE_NAME;
	}
}