/**    
 * 文件名：WealthSql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.wallet.sql;


import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.repository.dao.wallet.WealthTypeDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class WealthTypeSql extends CrudSql<WealthType> implements WealthTypeDao{

    @Override
    public boolean updateNoDefault() {
        return  0 < getJdbcTemplate().update(
                " UPDATE " + tableName() + " bean " +
                " SET bean.IS_DEFAULT = ? "
                , new Object[] {false}
                );
    }
    
    protected InsertSql insertSql( WealthType bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "DOMAIN_URL", bean.getDomainUrl() )
                    .addField( "ADDRESS_URL", bean.getAddressUrl() )
                    .addField( "IS_DEFAULT", bean.getIsDefault() )
                    .addField( "INIT", bean.getInit() );
    }

    @Override
    protected UpdateSql updateSql( WealthType bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "DOMAIN_URL", bean.getDomainUrl() )
                    .addField( "ADDRESS_URL", bean.getAddressUrl() )
                    .addField( "IS_DEFAULT", bean.getIsDefault() )
                    .addField( "INIT", bean.getInit() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return WealthType.TABLE_NAME;
    }

    
    
}
