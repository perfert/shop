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
import com.mas.user.domain.entity.wallet.Transfer;
import com.mas.user.repository.dao.wallet.TransferDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class TransferSql extends CrudSql<Transfer> implements TransferDao{

    @Override
    protected InsertSql insertSql( Transfer bean ){
        
        return
                new InsertSql( tableName() )
                    .addField( "SN", bean.getSn() )
                    .addField( "RECEIVE_TYPE", bean.getReceiveType() )
                    .addField( "PAY_TYPE", bean.getPayType() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "HAS_PAY", bean.getHasPay() )
                    .addField( "RECEIVE_ID", bean.getReceiveId() )
                    .addField( "MEMBER_ID", bean.getMemberId() );
    }

    @Override
    protected UpdateSql updateSql( Transfer bean )
    {
        return
                null;
    }
    
    @Override
    protected String tableName() {
        return Transfer.TABLE_NAME;
    }

    
    
    
}
