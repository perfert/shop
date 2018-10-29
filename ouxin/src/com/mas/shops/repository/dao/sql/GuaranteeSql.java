/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.dao.sql;

import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.shops.domain.entity.Guarantee;
import com.mas.shops.repository.dao.GuaranteeDao;

/**
 * 商家Sql
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
@Repository
public class GuaranteeSql extends CrudSql<Guarantee> implements GuaranteeDao {
    
    @Override
    protected InsertSql insertSql( Guarantee bean )
    {

        return 
                new InsertSql( tableName() )  
                    .addField( "SN", bean.getSn() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "NAME", bean.getName() )
                    .addField( "ORDER_STATE", bean.getOrderState() )
                    .addField( "WEALTH_ID", bean.getWealthId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "ORDER_ID", bean.getOrderId() )
                    .addField( "SHOPS_ID", bean.getShopsId() );
    }

    @Override
    protected UpdateSql updateSql( Guarantee bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "SN", bean.getSn() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "NAME", bean.getName() )
                    .addField( "ORDER_STATE", bean.getOrderState() )
                    .addField( "WEALTH_ID", bean.getWealthId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "ORDER_ID", bean.getOrderId() )
                    .addField( "SHOPS_ID", bean.getShopsId() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return Guarantee.TABLE_NAME;
    }
    
    @Override
    public boolean updateStatus(Object id, int state) {
        return 0 < getJdbcTemplate().update(" UPDATE " + tableName() + " SET  ORDER_STATE = ? WHERE ID = ?  ", new Object[] { state, id});
    }

    

}