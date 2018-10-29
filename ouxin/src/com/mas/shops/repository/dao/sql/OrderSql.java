/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.dao.sql;

import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.shops.domain.entity.Order;
import com.mas.shops.repository.dao.OrderDao;

/**
 * 商家Sql
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
@Repository
public class OrderSql extends CrudSql<Order> implements OrderDao {

    @Override
    protected InsertSql insertSql( Order bean )
    {

        return 
                new InsertSql( tableName() )  
                    .addField( "SN", bean.getSn() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "NAME", bean.getName() )
                    .addField( "ORDER_STATE", bean.getOrderState() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "WEALTH_ID", bean.getWealthId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                     .addField( "THIRD_ORDER_ID", bean.getThirdOrderId() )
                    .addField( "SHOPS_ID", bean.getShopsId() );
    }

    @Override
    protected UpdateSql updateSql( Order bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "SN", bean.getSn() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "NAME", bean.getName() )
                    .addField( "ORDER_STATE", bean.getOrderState() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "WEALTH_ID", bean.getWealthId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                     .addField( "THIRD_ORDER_ID", bean.getThirdOrderId() )
                    .addField( "SHOPS_ID", bean.getShopsId() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return Order.TABLE_NAME;
    }


}