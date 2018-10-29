/**    
 * 文件名：RedPacketItemQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.redpacket.sql;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.util.Page;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.rp.RedPacketItem;
import com.mas.user.domain.entity.vo.StatisReceiveVo;
import com.mas.user.repository.query.redpacket.RedPacketItemQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class RedPacketItemQuerySql extends QuerySql<RedPacketItem> implements RedPacketItemQueryDao{
    
    @Override
    public List<RedPacketItem> getList(String rpId) {
        return getJdbcTemplate().query(
                " SELECT bean.*,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " + 
                " WHERE bean.RP_ID  = ? ORDER BY bean.CREATE_DATE DESC "
                , new Object[] { rpId }
                , new BeanPropertyRowMapper<RedPacketItem>(RedPacketItem.class)
                );
    }
    
    @Override
    public void queryPage(String rpId, Page page) {
        String sql = " FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " + 
                " WHERE bean.RP_ID  = ?  ";
        
        if( page.isAutoCount() ){
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                                getJdbcTemplate().query(
                                        " SELECT COUNT(bean.ID) " + sql
                                        , new Object[] { rpId }
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        
        page.setResult(getJdbcTemplate().query(
                " SELECT bean.*,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR " + sql + " ORDER BY bean.CREATE_DATE DESC " + pageSql( page )
                , new Object[] { rpId } 
                , new BeanPropertyRowMapper<RedPacketItem>(RedPacketItem.class)
                ));
    }
    
    @Override
    public StatisReceiveVo statisticsReceive(String memberId,String wealthTypeId) {
        /*String selectSql = " SELECT SUM(bean.IS_LUCK) AS RECEIVE_LUCK_COUNT,COUNT(bean.ID) AS RECEIVE_COUNT,SUM(bean.CASH) AS RECEIVE_MONEY,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT INNER JOIN  " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " + 
                " WHERE bean.MEMBER_ID  = ?  ";*/
        
        String selectSql = " SELECT SUM(bean.IS_LUCK) AS RECEIVE_LUCK_COUNT,COUNT(bean.ID) AS RECEIVE_COUNT,SUM(bean.CASH) AS RECEIVE_MONEY,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR FROM " + Member.TABLE_NAME  + " u LEFT JOIN  " + tableName() + " bean ON bean.MEMBER_ID = u.ID " + 
                " WHERE bean.MEMBER_ID  = ? AND bean.WEALTH_TYPE_ID = ? ";
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        selectSql
                        , new Object[] {memberId,wealthTypeId}
                        , new BeanPropertyRowMapper<StatisReceiveVo>(StatisReceiveVo.class)
                        ));
    }

    @Override
    public void queryPageByMemberId(String memberId, Page page,String wealthTypeId) {
        String sql = " FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " + 
                " WHERE bean.MEMBER_ID  = ? AND bean.WEALTH_TYPE_ID = ? ";
        
        if( page.isAutoCount() ){
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                                getJdbcTemplate().query(
                                        " SELECT COUNT(bean.ID) " + sql
                                        , new Object[] { memberId,wealthTypeId }
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        
        page.setResult(getJdbcTemplate().query(
                " SELECT bean.*,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR " + sql + " ORDER BY bean.CREATE_DATE DESC " + pageSql( page )
                , new Object[] { memberId,wealthTypeId } 
                , new BeanPropertyRowMapper<RedPacketItem>(RedPacketItem.class)
                ));
        
    }
    
    @Override
    public RedPacketItem get(String rpId, String openId) {
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        " SELECT bean.*  FROM " + tableName() + " bean WHERE bean.RP_ID = ? AND bean.MEMBER_ID = ? "
                        , new Object[] { rpId, openId}
                        , new BeanPropertyRowMapper<RedPacketItem>(RedPacketItem.class)
                        ));
    }
    
    @Override
    public boolean isExist(String rpId, Object openId) {
        return
                0 < DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                " SELECT COUNT(bean.ID)  FROM " + tableName() + " bean WHERE bean.RP_ID = ? AND bean.MEMBER_ID = ? "
                                , new Object[] { rpId, openId }
                                , new SingleColumnRowMapper<Long>(Long.class)
                                )
                        );
    }
   
    @Override
    protected SimpleQuerySql querySql(RedPacketItem query) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String tableName() {
        return RedPacketItem.TABLE_NAME;
    }
   
}