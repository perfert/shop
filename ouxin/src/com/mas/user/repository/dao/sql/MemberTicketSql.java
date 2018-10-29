/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.repository.dao.sql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.MemberTicket;
import com.mas.user.repository.dao.MemberTicketDao;

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
public class MemberTicketSql extends CrudSql<MemberTicket> implements MemberTicketDao
{
	@Override
	public MemberTicket queryByMemberId( Object memberId )
	{
		return
				DataAccessUtils.uniqueResult(
						getJdbcTemplate().query(
								"SELECT * FROM " + tableName() + " WHERE MEMBER_ID = ?"
								, new Object[] { memberId }
								, new BeanPropertyRowMapper<MemberTicket>( MemberTicket.class )
								)
						);
	}
	
	@Override
    public MemberTicket queryByMemberId(String memberId, int type) {
	    return
                DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                "SELECT * FROM " + tableName() + " WHERE MEMBER_ID = ? AND OTYPE = ? "
                                , new Object[] { memberId,type }
                                , new BeanPropertyRowMapper<MemberTicket>( MemberTicket.class )
                                )
                        );
    }
	 
	@Override
    public MemberTicket queryByWealthId(Object wealthId, int type) {
	    return
                DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                "SELECT * FROM " + tableName() + " WHERE WEALTH_ID = ? AND OTYPE = ? "
                                , new Object[] { wealthId,type }
                                , new BeanPropertyRowMapper<MemberTicket>( MemberTicket.class )
                                )
                        );
    }
	
	@Override
	protected InsertSql insertSql( MemberTicket bean )
	{
		return
				new InsertSql( tableName() )
					.addField( "MEMBER_ID", bean.getMemberId() )
					.addField( "TICKET", bean.getTicket() )
					.addField( "OTYPE", bean.getOtype() )
					.addField( "CONTENT", bean.getContent() )
					.addField( "IMAGE", bean.getImage() );
	}

	@Override
	protected UpdateSql updateSql( MemberTicket bean )
	{
		return
				new UpdateSql( tableName() )
            		.addField( "TICKET", bean.getTicket() )
                    .addField( "OTYPE", bean.getOtype() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "IMAGE", bean.getImage() )   
                    .andEqWhere( "ID", bean.getId() );
	}

	@Override
	protected String tableName()
	{
		return MemberTicket.TABLE_NAME;
	}

    

   
}
