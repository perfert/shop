/**    
 * 文件名：RedPacketQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.redpacket.sql;

import java.util.Date;
import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.util.Page;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.rp.RedPacket;
import com.mas.user.domain.entity.vo.StatisSendVo;
import com.mas.user.repository.query.redpacket.RedPacketQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class RedPacketQuerySql extends QuerySql<RedPacket> implements RedPacketQueryDao{

    @Override
    protected SimpleQuerySql querySql(RedPacket query) {
        return null;
    }

    @Override
    protected String tableName() {
        return RedPacket.TABLE_NAME;
    }

    @Override
    public List<RedPacket> getExpire(Date date, int pageNo, int pageSize) {
        String selectSql =  " SELECT bean.* FROM " + tableName() + " bean " +
                " WHERE bean.HAS_PAY = 1 AND bean.HAS_BACK = 0 AND bean.NUM <> bean.RECEIVE_NUM AND bean.CREATE_DATE < ? " +
                " ORDER BY bean.CREATE_DATE DESC " + pageSql(pageNo, pageSize);
        return getJdbcTemplate().query(
                selectSql
                ,   new Object[] { date } 
                , new BeanPropertyRowMapper<RedPacket>(RedPacket.class)
                ); 
    }
    
    @Override
    public void getExpire(Date date, Page page) {
        String sql = " FROM " + tableName() + " bean  " +
                " WHERE bean.HAS_PAY = 1 AND bean.HAS_BACK = 0 AND bean.NUM <> bean.RECEIVE_NUM AND bean.CREATE_DATE < ? ";
        
        if( page.isAutoCount() ){
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                                getJdbcTemplate().query(
                                        " SELECT COUNT(bean.ID) " + sql
                                        , new Object[] { date}
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        
        page.setResult(getJdbcTemplate().query(
                " SELECT bean.* " + sql + " ORDER BY bean.CREATE_DATE DESC " + pageSql( page )
                , new Object[] { date } 
                , new BeanPropertyRowMapper<RedPacket>(RedPacket.class)
                ));
        
    }
    
    @Override
    public RedPacket getRedPacketAndUserInfo(Object id) {
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        " SELECT bean.*,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR  FROM " + tableName() + " bean LEFT JOIN  " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
                		" WHERE bean.ID = ? "
                        , new Object[] { id }
                        , new BeanPropertyRowMapper<RedPacket>(RedPacket.class)
                        ));
    }
    

    @Override
    public void queryPage(String mid, Page page,String wealthTypeId) {
        String sql = " FROM " + tableName() + " bean  WHERE bean.MEMBER_ID  = ? AND bean.HAS_PAY = ? AND bean.PAY_TYPE = ?  ";
        
        if( page.isAutoCount() ){
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                                getJdbcTemplate().query(
                                        " SELECT COUNT(bean.ID) " + sql
                                        , new Object[] { mid , 1, wealthTypeId}
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        
        page.setResult(getJdbcTemplate().query(
                " SELECT bean.* " + sql + " ORDER BY bean.CREATE_DATE DESC " + pageSql( page )
                , new Object[] { mid, 1 ,wealthTypeId} 
                , new BeanPropertyRowMapper<RedPacket>(RedPacket.class)
                ));
    }

    @Override
    public StatisSendVo statisticsSend(String memberId,String wealthTypeId) {
        /*String selectSql = " SELECT COUNT(bean.ID) AS SEND_COUNT,SUM(bean.CASH) AS SEND_MONEY,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN  " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " + 
        		" WHERE bean.MEMBER_ID  = ?  AND bean.HAS_PAY = ?  ";*/
        String selectSql = " SELECT COUNT(bean.ID) AS SEND_COUNT,SUM(bean.CASH) AS SEND_MONEY,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR FROM " + Member.TABLE_NAME + " u  LEFT JOIN  " + tableName() + " bean ON bean.MEMBER_ID = u.ID " + 
                " WHERE bean.MEMBER_ID  = ?  AND bean.HAS_PAY = ? AND bean.PAY_TYPE = ? ";
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        selectSql
                        , new Object[] {memberId, 1, wealthTypeId}
                        , new BeanPropertyRowMapper<StatisSendVo>(StatisSendVo.class)
                        ));
    }

    

    
}
