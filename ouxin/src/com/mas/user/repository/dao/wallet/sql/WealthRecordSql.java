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


import java.util.Date;

import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.wallet.WealthRecord;
import com.mas.user.repository.dao.wallet.WealthRecordDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class WealthRecordSql extends CrudSql<WealthRecord> implements WealthRecordDao{

    @Override
    protected InsertSql insertSql( WealthRecord bean )
    {
        
        return
                new InsertSql( tableName() )
                    .addField( "OLD_CASH", bean.getOldCash() )
                    .addField( "NOW_CASH", bean.getNowCash() )
                    .addField( "OLD_FREEZE", bean.getOldFreeze() )
                    .addField( "NOW_FREEZE", bean.getNowFreeze() )
                    .addField( "EXPENSE", bean.getExpense() )
                    .addField( "INCOME", bean.getIncome() )
                    .addField( "STATUS", bean.getStatus() )
                    .addField( "REMARK", bean.getRemark() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "PAY_TYPE", bean.getPayType() )
                    .addField( "OPERATE_TYPE", bean.getOperateType() )
                    .addField( "SN", bean.getSn() )
                    .addField( "TIPS", bean.getTips() )
                    .addField( "OFC_ID", bean.getOfcId() )
                    .addField( "TRANSFER_ID", bean.getTransferId() )
                    .addField( "RP_ID", bean.getRpId() )
                    .addField( "RP_ITEM_ID", bean.getRpItemId() )
                    .addField( "RP_BACK_ID", bean.getRpBackId() )
                    .addField( "WD_ID", bean.getWdId() )
                    .addField( "WD_BACK_ID", bean.getWdBackId() )
                    .addField( "ADMIN_ID", bean.getAdminId() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "WEALTH_ID", bean.getWealthId() )
                    .addField( "GUARANTEE_ID", bean.getGuaranteeId() );
    }

    @Override
    protected UpdateSql updateSql( WealthRecord bean )
    {
        return
                null;
    }
    
    @Override
    protected String tableName() {
        return WealthRecord.TABLE_NAME;
    }

    @Override
    public boolean updateStatusByWithdrawId(int status, String withdrawId, Date date) {
        return  0 < getJdbcTemplate().update(
                " UPDATE " + tableName() + " bean " +
                " SET bean.STATUS = ?,bean.MODIFY_DATE = ? " +
                " WHERE bean.WD_ID = ? "
                , new Object[] {status,date,withdrawId}
                );
    }

}
