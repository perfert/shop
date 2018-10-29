/**    
 * 文件名：WealthRecordQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.wallet.sql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.util.Page;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.vo.WithDrawVo;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.Withdraw;
import com.mas.user.repository.query.wallet.WithdrawQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class WithdrawQuerySql extends QuerySql<Withdraw> implements WithdrawQueryDao{

    @Override
    protected SimpleQuerySql querySql(Withdraw query) {
        SimpleQuerySql sql =
                new SimpleQuerySql( tableName() )
                    .andEqWhereIfNotBlankValue( "MEMBER_ID", query.getMemberId() )
                    .addDesc( "ID" );
        return sql;
    }

    @Override
    protected String tableName() {
        return Withdraw.TABLE_NAME;
    }

    @Override
    public void queryPage(String memberId, Page page) {
        String sql = " FROM " + tableName() + " bean  WHERE bean.MEMBER_ID  = ? ";
        Object[] objs = new Object[]{memberId};
        
        if( page.isAutoCount() ){
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                                getJdbcTemplate().query(
                                        " SELECT COUNT(bean.ID) " + sql
                                        , objs
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        page.setResult(getJdbcTemplate().query(
                " SELECT bean.* " + sql + " ORDER BY bean.CREATE_DATE DESC " + pageSql( page )
                , objs
                , new BeanPropertyRowMapper<Withdraw>(Withdraw.class)
                ));
    }
    
    @Override
    public void queryPage(String memberId, String wealthTypeId, Page page) {
        String sql = " FROM " + tableName() + " bean  WHERE bean.MEMBER_ID  = ? AND bean.WEALTH_TYPE_ID = ? ";
        Object[] objs = new Object[]{memberId,wealthTypeId};
        
        if( page.isAutoCount() ){
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                                getJdbcTemplate().query(
                                        " SELECT COUNT(bean.ID) " + sql
                                        , objs
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        page.setResult(getJdbcTemplate().query(
                " SELECT bean.* " + sql + " ORDER BY bean.CREATE_DATE DESC " + pageSql( page )
                , objs
                , new BeanPropertyRowMapper<Withdraw>(Withdraw.class)
                ));
        
    }

    @Override
    public Long existCheck() {
        String sql = " FROM " + tableName() + " bean WHERE bean.STATUS = ? ";
        Object[] objs = new Object[]{Withdraw.Status.WITHDRAW_IN_HAND.value()};
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        " SELECT COUNT(bean.ID) " + sql
                        , objs
                        , new SingleColumnRowMapper<Long>(Long.class)
                        )
                );
    }

    @Override
    public void queryPageByAddress(Page page) {
        String sql = " FROM " + tableName() + " bean LEFT JOIN  " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
    		    " LEFT JOIN " + Wealth.TABLE_NAME + " w ON bean.WEALTH_TYPE_ID = w.WEALTH_TYPE AND u.ID = w.MEMBER_ID  " + 
        		" WHERE bean.STATUS  = ? ";
        //String sql = " FROM " + tableName() + " bean WHERE bean.STATUS = ? ";
        Object[] objs = new Object[]{Withdraw.Status.WITHDRAW_IN_HAND.value()};
        
        page.setResult(getJdbcTemplate().query(
                " SELECT bean.*,u.CODE AS CODE,u.ACCOUNT AS ACCOUNT,w.ADDRESS AS MY_ADDRESS " + sql + " ORDER BY bean.CREATE_DATE DESC " + pageSql( page )
                , objs
                , new BeanPropertyRowMapper<WithDrawVo>(WithDrawVo.class)
                ));
    }

    /*@Override
    public void queryPageByAddress(Page page) {
        String sql = " FROM " + tableName() + " bean LEFT JOIN  " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
                " LEFT JOIN " + Wealth.TABLE_NAME + " w ON u.ID = w.MEMBER_ID  " + 
                " WHERE bean.STATUS  = ? ";
        Object[] objs = new Object[]{Withdraw.Status.WITHDRAW_IN_HAND.value()};
        
        page.setResult(getJdbcTemplate().query(
                " SELECT bean.*,u.CODE AS CODE,u.ACCOUNT AS ACCOUNT,w.ADDRESS AS MY_ADDRESS " + sql + " ORDER BY bean.CREATE_DATE DESC " + pageSql( page )
                , objs
                , new BeanPropertyRowMapper<WithDrawVo>(WithDrawVo.class)
                ));
    }*/

}