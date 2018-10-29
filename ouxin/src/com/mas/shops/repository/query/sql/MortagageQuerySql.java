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
import com.mas.shops.domain.entity.Mortagage;
import com.mas.shops.repository.query.MortagageQueryDao;

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
public class MortagageQuerySql extends QuerySql<Mortagage> implements MortagageQueryDao{
    
    @Override
    public Mortagage getNoBackMortagage(String memberId) {
        return DataAccessUtils.uniqueResult( getJdbcTemplate().query(
                "SELECT bean.* FROM " + tableName() + " bean WHERE bean.MEMBER_ID = ? AND bean.STATUS = ?  "
                , new Object[] { memberId,Mortagage.Status.CREATE }
                , new BeanPropertyRowMapper<Mortagage>(Mortagage.class)
                )
                );
    }
    
	
	@Override
	protected SimpleQuerySql querySql( Mortagage query )
	{
		/*return
				new SimpleQuerySql( tableName() )
					.andLkWhereIfNotBlankValue( "TITLE", query.getTitle() )
					.addDesc( "ID" );*/
	    return null;
	}

	@Override
	protected String tableName()
	{
		return Mortagage.TABLE_NAME;
	}
    

}