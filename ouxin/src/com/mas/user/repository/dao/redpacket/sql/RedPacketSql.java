/**    
 * 文件名：RedPacketSql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.redpacket.sql;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.rp.RedPacket;
import com.mas.user.repository.dao.redpacket.RedPacketDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class RedPacketSql extends CrudSql<RedPacket> implements RedPacketDao{

    @Override
    public boolean updateRePacket(String rpId,int receiveNum, BigDecimal receiveCash, BigDecimal max) {
        return  0 < getJdbcTemplate().update(
                " UPDATE " + tableName() + " bean " +
                " SET bean.RECEIVE_NUM = ?,bean.RECEIVE_CASH = ? " +
                " WHERE bean.ID = ? AND bean.RECEIVE_CASH <= ? "
                , new Object[] {receiveNum,receiveCash,rpId,max}
                );
    }

    
    @Override
    protected InsertSql insertSql( RedPacket bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "RECEIVE_CASH", bean.getReceiveCash() )
                    .addField( "SN", bean.getSn() )
                    .addField( "REMARK", bean.getRemark() )
                    .addField( "NUM", bean.getNum() )
                    .addField( "RECEIVE_NUM", bean.getReceiveNum() )
                    .addField( "HAS_PAY", bean.getHasPay() )
                    .addField( "HAS_BACK", bean.getHasBack() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "GROUP_ID", bean.getGroupId() )
                    .addField( "RECEIVE_ID", bean.getReceiveId() )
                    .addField( "RECEIVE_TYPE", bean.getReceiveType() );
    } 
    
    @Override
    protected UpdateSql updateSql( RedPacket bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "RECEIVE_TYPE", bean.getReceiveType() )
                    .addField( "PAY_TYPE", bean.getPayType() )
                    //.addField( "CASH", bean.getCash() )
                    .addField( "SN", bean.getSn() )
                    .addField( "RECEIVE_CASH", bean.getReceiveCash() )
                    .addField( "HAS_PAY", bean.getHasPay() )
                    .addField( "HAS_BACK", bean.getHasBack() )
                    .addField( "NUM", bean.getNum() )
                    .addField( "RECEIVE_NUM", bean.getReceiveNum() )
                    .addField( "REMARK", bean.getRemark() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "GROUP_ID", bean.getGroupId() )
                    .addField( "RECEIVE_ID", bean.getReceiveId() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
   
    @Override
    protected String tableName() {
        return RedPacket.TABLE_NAME;
    }


    @Override
    public boolean updatePaySuccess(String rpId, String payType, Date payDate) {
        return  0 < getJdbcTemplate().update(
                " UPDATE " + tableName() + " bean " +
                " SET bean.HAS_PAY = ?,bean.PAY_TYPE = ?,bean.CREATE_DATE = ? " +
                " WHERE bean.ID = ? "
                , new Object[] {1,payType,payDate,rpId}
                );
    }


    @Override
    public boolean updateBack(String rpId) {
        return  0 < getJdbcTemplate().update(
                " UPDATE " + tableName() + " bean " +
                " SET bean.HAS_BACK = ? " +
                " WHERE bean.ID = ? "
                , new Object[] {1,rpId}
                );
    }

}
