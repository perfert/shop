/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query.sql;


import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.shops.domain.entity.Certificate;
import com.mas.shops.domain.entity.Guarantee;
import com.mas.shops.repository.query.GuaranteeQueryDao;

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
public class GuaranteeQuerySql extends QuerySql<Guarantee> implements GuaranteeQueryDao{
    
	@Override
	protected SimpleQuerySql querySql( Guarantee query )
	{
		return
				new SimpleQuerySql( tableName() )
					.addDesc( "ID" );
	}

	@Override
	protected String tableName()
	{
		return Guarantee.TABLE_NAME;
	}

    @Override
    public Guarantee getByOrderId(String orderId) {
        return DataAccessUtils.uniqueResult( getJdbcTemplate().query(
                "SELECT bean.* FROM " + tableName() + " bean WHERE ORDER_ID = ? "
                , new Object[] { orderId }
                , new BeanPropertyRowMapper<Guarantee>(Guarantee.class)
                )
                );
    }

    

}