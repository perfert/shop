/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.shops.domain.entity.Shops;
import com.mas.shops.repository.dao.ShopsDao;

/**
 * 商家Sql
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
@Repository
public class ShopsSql extends CrudSql<Shops> implements ShopsDao {

    @Override
    protected InsertSql insertSql( Shops bean )
    {

        return 
                new InsertSql( tableName() )  
                    .addField( "AUTH_STATUS", bean.getAuthStatus() )
                    .addField( "STORE_STATUS", bean.getStoreStatus() )
                    .addField( "LEGAL_PERSON", bean.getLegalPerson() )
                    .addField( "CARD_NO", bean.getCardNo() )
                    .addField( "LINK", bean.getLink() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .addField( "TITLE", bean.getTitle() )
                    .addField( "MOBILE", bean.getMobile() )
                    .addField( "LOGO", bean.getLogo() )
                    .addField( "NAME", bean.getName() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "THIRD_SHOPS_ID", bean.getThirdShopsId() )
                    .addField( "MEMBER_ID", bean.getMemberId() );
    }

    
    @Override
    protected UpdateSql updateSql( Shops bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "AUTH_STATUS", bean.getAuthStatus() )
                    .addField( "STORE_STATUS", bean.getStoreStatus() )
                    .addField( "LEGAL_PERSON", bean.getLegalPerson() )
                    .addField( "CARD_NO", bean.getCardNo() )
                    .addField( "LINK", bean.getLink() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .addField( "TITLE", bean.getTitle() )
                    .addField( "MOBILE", bean.getMobile() )
                    .addField( "LOGO", bean.getLogo() )
                    .addField( "NAME", bean.getName() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "THIRD_SHOPS_ID", bean.getThirdShopsId() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return Shops.TABLE_NAME;
    }

    @Override
    public boolean updateAuthStatus(Object id, Integer verify, String reason) {
        if(StringUtils.isNotEmpty(reason))
            return 0 < getJdbcTemplate().update("UPDATE " + tableName() + " SET AUTH_STATUS = ?,REASON = ? WHERE ID = ?",
                new Object[] { verify,reason, id });
        else
            return 0 < getJdbcTemplate().update("UPDATE " + tableName() + " SET AUTH_STATUS = ? WHERE ID = ?",
                    new Object[] { verify, id });
    }
    
    

}