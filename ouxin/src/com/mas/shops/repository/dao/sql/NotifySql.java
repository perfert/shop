/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.shops.domain.entity.Notify;
import com.mas.shops.repository.dao.NotifyDao;

/**
 * 商家Sql
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
@Repository
public class NotifySql extends CrudSql<Notify> implements NotifyDao {

    @Override
    protected InsertSql insertSql( Notify bean )
    {
        return 
                new InsertSql( tableName() )  
                    .addField( "OBJECT_ID", bean.getObjectId() )
                    .addField( "TYPE_ID", bean.getTypeId() )
                    .addField( "PARAMETERS", bean.getParameters() )
                    .addField( "STATUS", bean.getStatus() );
    }

    @Override
    protected UpdateSql updateSql( Notify bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "OBJECT_ID", bean.getObjectId() )
                    .addField( "TYPE_ID", bean.getTypeId() )
                    .addField( "PARAMETERS", bean.getParameters() )
                    .addField( "STATUS", bean.getStatus() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return Notify.TABLE_NAME;
    }

    @Override
    public boolean updateStatus(Object notifyId, int status) {
        return 0 < getJdbcTemplate().update(" UPDATE " + tableName() + " SET  STATUS = ? WHERE ID = ?  ", new Object[] { status, notifyId});
    }


}