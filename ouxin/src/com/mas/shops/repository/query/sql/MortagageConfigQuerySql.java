/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query.sql;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.util.Page;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.shops.domain.entity.MortagageConfig;
import com.mas.shops.repository.query.MortagageConfigQueryDao;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthType;

/**
 * 会员。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class MortagageConfigQuerySql extends QuerySql<MortagageConfig> implements MortagageConfigQueryDao{
    
    @Override
    public List<MortagageConfig> getAll(String memberId) {
        String selectSql = "SELECT bean.*,w.CASH AS BALANCE ";
        String fromSql = "FROM " + tableName() + " bean LEFT JOIN " + WealthType.TABLE_NAME + " t ON bean.WEALTH_TYPE_ID = t.ID " +
        		" LEFT JOIN " + Wealth.TABLE_NAME + " w ON t.ID = w.WEALTH_TYPE " +
        		" WHERE w.MEMBER_ID = :mid ";
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("mid", memberId);
        return getNamedParameterJdbcTemplate().query(
                selectSql + fromSql + " ORDER BY bean.ID DESC "
                , values
                , new BeanPropertyRowMapper<MortagageConfig>(MortagageConfig.class)
                );
    }
    
    @Override
    public boolean existWealthType(Object wealthTypeId) {
        return
                0 < DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                "SELECT COUNT(bean.ID) FROM " + tableName() + " bean where bean.WEALTH_TYPE_ID = ? "
                                , new Object[] { wealthTypeId }
                                , new SingleColumnRowMapper<Long>(Long.class)
                                )
                        );
    }
    
    @Override
    public void queryPage( Page page, MortagageConfig query )
    {
        String selectSql = "SELECT bean.*,t.NAME AS TYPE_NAME ";
        String fromSql = "FROM " + tableName() + " bean LEFT JOIN " + WealthType.TABLE_NAME + " t ON bean.WEALTH_TYPE_ID = t.ID ";
        Map<String, Object> values = new HashMap<String, Object>();
        if( null != query )
        {
            if( VerifyUtil.isNotBlank( query.getName() ) )
            {
                fromSql += " AND bean.NAME LIKE :name";
                values.put( "name", "%" + query.getName() + "%" );
            }
        }
        
        
        if( page.isAutoCount() )
        {
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                            VerifyUtil.isEmpty( values ) ?
                                getJdbcTemplate().query(
                                        "SELECT COUNT(*) " + fromSql
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                : getNamedParameterJdbcTemplate().query(
                                        "SELECT COUNT(*) " + fromSql
                                        , values
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        String pageSql = pageSql( page );
        page.setResult(
                VerifyUtil.isEmpty( values ) ?
                    getJdbcTemplate().query(
                            selectSql + fromSql + " ORDER BY bean.ID DESC " + pageSql
                            , new BeanPropertyRowMapper<MortagageConfig>(MortagageConfig.class)
                            )
                    : getNamedParameterJdbcTemplate().query(
                            selectSql + fromSql + " ORDER BY bean.ID DESC " + pageSql
                            , values
                            , new BeanPropertyRowMapper<MortagageConfig>(MortagageConfig.class)
                            )
                    );
    }
    
	
	@Override
	protected SimpleQuerySql querySql( MortagageConfig query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andLkWhereIfNotBlankValue( "NAME", query.getName() )
					.addDesc( "ID" );
	}

	@Override
	protected String tableName()
	{
		return MortagageConfig.TABLE_NAME;
	}

    

   

}