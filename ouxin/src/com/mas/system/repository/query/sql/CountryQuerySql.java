/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.system.repository.query.sql;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.util.Page;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.system.domain.entity.Country;
import com.mas.system.repository.query.CountryQueryDao;

/**
 * 国家
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class CountryQuerySql extends QuerySql<Country> implements CountryQueryDao
{
    @Override
    public boolean exist(String code) {
        return DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                "SELECT COUNT(*) FROM " + tableName() + " WHERE CODE = ? "
                , new Object[] { code },new SingleColumnRowMapper<Long>(Long.class)
                )) > 0;
    }
    
	@Override
	public List<Country> queryAll(  )
	{
	    return
                getJdbcTemplate().query(
                        "SELECT * FROM " + tableName()
                        , new BeanPropertyRowMapper<Country>(Country.class)
                        );
	}

	@Override
    public void queryPage( Page page, Country query )
    {
        SimpleQuerySql sql = null == query ? new SimpleQuerySql( tableName() ).addAsc( "ID" )
                    : querySql( query ).andEqWhereIfNotBlankValue( "STATE", query.getState() );
        String queryString = sql.sql();
        String fromSql = getFromToOrderBySql( queryString );
        
        if( page.isAutoCount() )
        {
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                            VerifyUtil.isEmpty( sql.values() ) ?
                                getJdbcTemplate().query(
                                        "SELECT COUNT(*) " + fromSql
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                : getNamedParameterJdbcTemplate().query(
                                        "SELECT COUNT(*) " + fromSql
                                        , sql.values()
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        String pageSql = pageSql( page );
        page.setResult(
                VerifyUtil.isEmpty( sql.values() ) ?
                    getJdbcTemplate().query(
                            queryString + pageSql
                            , new BeanPropertyRowMapper(super.ormClass)
                            )
                    : getNamedParameterJdbcTemplate().query(
                            queryString + pageSql
                            , sql.values()
                            , new BeanPropertyRowMapper(super.ormClass)
                            )
                    );
    }
    
	
	@Override
	protected SimpleQuerySql querySql( Country query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andLkWhereIfNotBlankValue( "NAME", query.getName() );
	}

	@Override
	protected String tableName()
	{
		return Country.TABLE_NAME;
	}

    
}