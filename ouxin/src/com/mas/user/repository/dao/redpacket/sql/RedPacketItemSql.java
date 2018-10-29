/**    
 * 文件名：RedPacketItemSql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.redpacket.sql;

import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.rp.RedPacketItem;
import com.mas.user.domain.entity.rp.RedPacketNum;
import com.mas.user.repository.dao.redpacket.RedPacketItemDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */

@Repository
public class RedPacketItemSql extends CrudSql<RedPacketItem> implements RedPacketItemDao{

    @Override
    public boolean updateLuck(Object rpId) {
        return  0 < getJdbcTemplate().update(
            " UPDATE " + tableName() + " bean LEFT JOIN " + RedPacketNum.TABLE_NAME + " r ON bean.RP_NUM_ID = r.ID " +
            " SET bean.IS_LUCK = 1 " +
            " WHERE bean.RP_ID = ? AND r.IS_LUCK = 1 "
            , new Object[] {rpId}
            );
    }
    
    @Override
    protected InsertSql insertSql( RedPacketItem bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "IS_LUCK", bean.getIsLuck() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "SN", bean.getSn() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "RP_NUM_ID", bean.getRpNumId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                    .addField( "RP_ID", bean.getRpId() );
    }

    @Override
    protected UpdateSql updateSql( RedPacketItem bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "IS_LUCK", bean.getIsLuck() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "SN", bean.getSn() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "RP_ID", bean.getRpId() )
                    .addField( "RP_NUM_ID", bean.getRpNumId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return RedPacketItem.TABLE_NAME;
    }
    
}
