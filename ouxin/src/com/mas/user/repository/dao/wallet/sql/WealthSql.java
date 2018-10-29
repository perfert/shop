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


import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.repository.dao.wallet.WealthDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class WealthSql extends CrudSql<Wealth> implements WealthDao{

    protected InsertSql insertSql( Wealth bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "FREEZE", bean.getFreeze() )
                    .addField( "WEALTH_TYPE", bean.getWealthType() )
                    .addField( "MEMBER_ID", bean.getMemberId() );
    }

    @Override
    protected UpdateSql updateSql( Wealth bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "FREEZE", bean.getFreeze() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return Wealth.TABLE_NAME;
    }

    @Override
    public boolean updateCash(Object id, BigDecimal operateCash) {
        return  0 < getJdbcTemplate().update(
                " UPDATE " + tableName() + " bean " +
                " SET bean.CASH = bean.CASH + ? " +
                " WHERE bean.ID = ? AND bean.CASH + ? >= 0 "
                , new Object[] {operateCash,id,operateCash}
                );
    }

    
}
