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
import com.mas.user.domain.entity.wallet.WithdrawBack;
import com.mas.user.repository.dao.wallet.WithdrawBackDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class WithdrawBackSql extends CrudSql<WithdrawBack> implements WithdrawBackDao{

    @Override
    protected InsertSql insertSql( WithdrawBack bean ){
        return new InsertSql(tableName())
        .addField("SN", bean.getSn())
        .addField("STATUS", bean.getStatus())
        .addField("CASH", bean.getCash())
        .addField("WD_ID", bean.getWdId())
        .addField("MEMBER_ID", bean.getMemberId());
    }
    

    @Override
    protected UpdateSql updateSql( WithdrawBack bean )
    {
        return
                null;
    }
    
    @Override
    protected String tableName() {
        return WithdrawBack.TABLE_NAME;
    }

    
    
    
    
}
