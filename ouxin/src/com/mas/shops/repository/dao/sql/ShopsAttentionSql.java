/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.shops.domain.entity.ShopsAttention;
import com.mas.shops.repository.dao.ShopsAttentionDao;

/**
 * 商家Sql
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
@Repository
public class ShopsAttentionSql extends CrudSql<ShopsAttention> implements ShopsAttentionDao {

    @Override
    protected InsertSql insertSql( ShopsAttention bean )
    {
        return 
                new InsertSql( tableName() )  
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "SHOPS_ID", bean.getShopsId() )
                    .addField( "ATTENTION", bean.getAttention() )
                    .addField( "LOGO", bean.getLogo() )
                    .addField( "LINK", bean.getLink() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "TITLE", bean.getTitle() );
    }

    @Override
    protected UpdateSql updateSql( ShopsAttention bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "SHOPS_ID", bean.getShopsId() )
                    .addField( "TITLE", bean.getTitle() )
                    .addField( "ATTENTION", bean.getAttention() )
                    .addField( "LOGO", bean.getLogo() )
                    .addField( "LINK", bean.getLink() )
                    .addField( "DETAIL", bean.getDetail() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return ShopsAttention.TABLE_NAME;
    }

}