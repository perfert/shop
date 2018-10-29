/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query.sql;


import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.util.Page;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.shops.domain.entity.Certificate;
import com.mas.shops.domain.entity.Shops;
import com.mas.shops.domain.entity.ShopsAttention;
import com.mas.shops.repository.query.ShopsQueryDao;

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
public class ShopsQuerySql extends QuerySql<Shops> implements ShopsQueryDao{
    
    
	
	@Override
	protected SimpleQuerySql querySql( Shops query )
	{
		return
				new SimpleQuerySql( tableName() )
					.andLkWhereIfNotBlankValue( "TITLE", query.getTitle() )
					.addDesc( "ID" );
	}

	@Override
	protected String tableName()
	{
		return Shops.TABLE_NAME;
	}

    @Override
    public Shops getByMemberId(String mid,boolean containCertificate) {
        String sql = null;
        if(containCertificate){
            sql = "SELECT bean.*,cer.NAME AS NAME,cer.LEGAL_PERSON AS LEGAL_PERSON,cer.CARD_REVERSE_PATH AS CARD_REVERSE_PATH,cer.CARD_NO AS CARD_NO,cer.CARD_FACE_PATH AS CARD_FACE_PATH,cer.BUSINESS_LICENSE_PATH AS BUSINESS_LICENSE_PATH FROM " + tableName() + " bean " +
                  " LEFT JOIN " + Certificate.TABLE_NAME  + " cer ON bean.ID = cer.SHOPS_ID    WHERE MEMBER_ID = ? ";
        }else{
            sql = "SELECT bean.* FROM " + tableName() + " bean WHERE MEMBER_ID = ? ";
        }
        
        return DataAccessUtils.uniqueResult( getJdbcTemplate().query(
                sql
                , new Object[] { mid }
                , new BeanPropertyRowMapper<Shops>(Shops.class)
                )
                );
    }
    
    @Override
    public Shops get(String memberId, String shopsId) {
        String sql = "SELECT bean.*,at.ATTENTION AS ATTENTION FROM " + tableName() + " bean " +
                  " LEFT JOIN " + ShopsAttention.TABLE_NAME  + " at ON bean.ID = at.SHOPS_ID    WHERE bean.ID = ? AND at.MEMBER_ID = ? ";
        
        return DataAccessUtils.uniqueResult( getJdbcTemplate().query(
                sql
                , new Object[] { shopsId,memberId }
                , new BeanPropertyRowMapper<Shops>(Shops.class)
                )
                );
    }

    @Override
    public void queryPage(Page page, String query) {
        String selectSql = " FROM " + tableName() + " bean  WHERE bean.TITLE LIKE ? ";
        Object[] objects = new Object[] {"%" + query + "%"};
        if( page.isAutoCount() ){
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                                getJdbcTemplate().query(
                                        " SELECT COUNT(bean.ID) " + selectSql
                                        , objects
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        page.setResult(getJdbcTemplate().query(
                " SELECT bean.* " + selectSql + " ORDER BY bean.CREATE_DATE DESC " + pageSql( page )
                , objects
                , new BeanPropertyRowMapper<Shops>(Shops.class)
                ));
        
    }

    
    @Override
    public boolean isExist(String mid) {
        return 0 < DataAccessUtils
                .uniqueResult(getJdbcTemplate().query(
                        "SELECT COUNT(*) FROM " + tableName() + "  WHERE MEMBER_ID = ?  ",
                        new Object[] { mid}, new SingleColumnRowMapper<Long>(Long.class)));
    }

    @Override
    public boolean checkOrder(String memberId, String shopsId) {
        return 0 < DataAccessUtils
                .uniqueResult(getJdbcTemplate().query(
                        "SELECT COUNT(*) FROM " + tableName() + "  WHERE MEMBER_ID = ? AND THIRD_SHOPS_ID = ? ",
                        new Object[] { memberId,shopsId}, new SingleColumnRowMapper<Long>(Long.class)));
    }

    

}