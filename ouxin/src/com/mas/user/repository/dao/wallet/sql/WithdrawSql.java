/**    
 * 文件名：WealthRecordSql.java    
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
import com.mas.user.domain.entity.wallet.Withdraw;
import com.mas.user.repository.dao.wallet.WithdrawDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class WithdrawSql extends CrudSql<Withdraw> implements WithdrawDao{

    @Override
    public boolean updateStatus(int status,String id) {
        return  0 < getJdbcTemplate().update(
                " UPDATE " + tableName() + " bean " +
                " SET bean.STATUS = ? " +
                " WHERE bean.ID = ? "
                , new Object[] {status,id}
                );
    }

    
    @Override
    protected InsertSql insertSql( Withdraw bean ){
        return
                new InsertSql( tableName() )
                    .addField( "SN", bean.getSn() )
                    .addField( "STATUS", bean.getStatus() )
                    .addField( "NUM", bean.getNum() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                    .addField( "MEMBER_ID", bean.getMemberId() );
    }

    @Override
    protected UpdateSql updateSql( Withdraw bean )
    {
        return
                null;
    }
    
    @Override
    protected String tableName() {
        return Withdraw.TABLE_NAME;
    }

    
    
    
    
}
