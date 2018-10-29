/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query.sql;


import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.shops.domain.entity.ShopsAttention;
import com.mas.shops.repository.query.ShopsAttentionQueryDao;

/**
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Repository
public class ShopsAttentionQuerySql extends QuerySql<ShopsAttention> implements ShopsAttentionQueryDao{
    
    
	@Override
	protected SimpleQuerySql querySql( ShopsAttention query )
	{
		return
				new SimpleQuerySql( tableName() )
					.addDesc( "ID" );
	}

	@Override
	protected String tableName()
	{
		return ShopsAttention.TABLE_NAME;
	}

    @Override
    public List<ShopsAttention> queryAttention(String mid) {
        String selectSql = " SELECT bean.* FROM " + tableName() + " bean WHERE bean.MEMBER_ID = ?  ORDER BY bean.MODIFY_DATE DESC ";
        Object[] objects = new Object[] {mid};
    return getJdbcTemplate().query(
            selectSql
            ,  objects 
            , new BeanPropertyRowMapper<ShopsAttention>(ShopsAttention.class)
            ); 
    }

    @Override
    public boolean exist(String memberId, String shopsId) {
        return 0 < DataAccessUtils
                .uniqueResult(getJdbcTemplate().query(
                        "SELECT COUNT(*) FROM " + tableName() + "  WHERE MEMBER_ID = ? AND SHOPS_ID = ? ",
                        new Object[] { memberId, shopsId }, new SingleColumnRowMapper<Long>(Long.class)));
    }

    @Override
    public boolean updateAttention(String memberId, String shopsId, int look) {
        return 0 < getJdbcTemplate().update("UPDATE " + tableName() + " SET ATTENTION = ? WHERE MEMBER_ID = ? AND SHOPS_ID = ? ",
                new Object[] { look ,memberId, shopsId });
    }
}