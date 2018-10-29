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
import com.mas.user.domain.entity.wallet.Ofc;
import com.mas.user.repository.dao.wallet.OfcDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class OfcSql extends CrudSql<Ofc> implements OfcDao{

    @Override
    protected InsertSql insertSql( Ofc bean )
    {
        
        return
                new InsertSql( tableName() )
                    .addField( "SN", bean.getMemberId() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .addField( "FROM_ADDRESS", bean.getFromAddress() )
                    .addField( "PAY_TYPE", bean.getPayType() )
                    .addField( "TOTAL_FEE", bean.getTotalFee() )
                    .addField( "API_TYPE", bean.getApiType() )
                    .addField( "HAS_PAY", bean.getHasPay() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                    .addField( "MEMBER_ID", bean.getMemberId() );
    }

    @Override
    protected UpdateSql updateSql( Ofc bean )
    {
        return
                null;
    }
    
    @Override
    protected String tableName() {
        return Ofc.TABLE_NAME;
    }

    
    
    
}
