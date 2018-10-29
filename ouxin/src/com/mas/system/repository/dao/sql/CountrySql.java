package com.mas.system.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.system.domain.entity.Country;
import com.mas.system.repository.dao.CountryDao;

/**
 * 国家
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Repository
public class CountrySql extends CrudSql<Country> implements CountryDao
{
	@Override
	protected InsertSql insertSql( Country bean )
	{
		return
				new InsertSql( tableName() )
		            .addField( "LANG", bean.getLang() )
		            .addField( "ISO", bean.getIso() )
					.addField( "NAME", bean.getName() )
					.addField( "CODE", bean.getCode() )
					.addField( "CN_NAME", bean.getCnName() );
	}

	@Override
	protected UpdateSql updateSql( Country bean )
	{
		return
				new UpdateSql( tableName() )
                    .addField( "LANG", bean.getLang() )
                    .addField( "ISO", bean.getIso() )
					.addField( "NAME", bean.getName() )
					.addField( "CODE", bean.getCode() )
					.addField( "CN_NAME", bean.getCnName() )
					.andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return Country.TABLE_NAME;
	}
}