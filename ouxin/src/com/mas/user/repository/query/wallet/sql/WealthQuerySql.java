/**    
 * 文件名：WealthQuerySql.java    
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
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.vo.MemberWealthVo;
import com.mas.user.domain.entity.wallet.Wealth;
import com.mas.user.domain.entity.wallet.WealthType;
import com.mas.user.repository.query.wallet.WealthQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class WealthQuerySql extends QuerySql<Wealth> implements WealthQueryDao{

    @Override
    protected SimpleQuerySql querySql(Wealth query) {
        return null;
    }

    @Override
    protected String tableName() {
        return Wealth.TABLE_NAME;
    }

    @Override
    public Wealth getByMemberId(String mid,boolean includPay) {
        String sql = null;
        if(includPay)
            sql = " SELECT bean.*,u.PAY_PASSWORD AS PAY_PASSWORD FROM " + tableName() + " bean LEFT JOIN  " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
            		" WHERE bean.MEMBER_ID = ?";
        else
            sql = " SELECT bean.* FROM " + tableName() + " bean WHERE MEMBER_ID = ?";
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        sql
                        , new Object[] { mid }
                        , new BeanPropertyRowMapper<Wealth>(Wealth.class)
                        ));
    }
    
    @Override
    public Wealth getByMemberId(String memberId, String wealthTypeId, boolean containPayPwd) {
        String sql = null;
        if(containPayPwd)
            sql = " SELECT bean.*,u.PAY_PASSWORD AS PAY_PASSWORD FROM " + tableName() + " bean LEFT JOIN  " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
                    " WHERE bean.MEMBER_ID = ? AND bean.WEALTH_TYPE = ? ";
        else
            sql = " SELECT bean.* FROM " + tableName() + " bean WHERE MEMBER_ID = ? AND bean.WEALTH_TYPE = ? " ;
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        sql
                        , new Object[] { memberId,wealthTypeId }
                        , new BeanPropertyRowMapper<Wealth>(Wealth.class)
                        ));
    }

    @Override
    public boolean exixt(String mid) {
        return 0 < DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                "SELECT COUNT(*) FROM " + tableName() + " WHERE MEMBER_ID = ? "
                , new Object[] { mid }
                , new SingleColumnRowMapper<Long>(Long.class)
                ));
    }

    @Override
    public Member getMemberByAddress(String address,boolean containMemberId) {
        String sql = null;
        if(containMemberId)
            sql = " SELECT u.ID AS ID,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
                    " WHERE bean.ADDRESS = ?";
        else
            sql = " SELECT u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
            " WHERE bean.ADDRESS = ?";
        
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        sql 
                        , new Object[] { address }
                        , new BeanPropertyRowMapper<MemberWealthVo>(MemberWealthVo.class)
                        ));
    }

    @Override
    public Wealth getByAddress(String address) {
        String sql = " SELECT bean.*,u.NICK_NAME AS NICK_NAME,u.AVATAR AS AVATAR,u.PAY_PASSWORD AS PAY_PASSWORD,t.NAME AS WEALTH_TYPE_NAME   FROM " + tableName() + " bean " +
                " LEFT JOIN " + WealthType.TABLE_NAME + " t ON bean.WEALTH_TYPE = t.ID " +
        		" LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
        		" WHERE bean.ADDRESS = ?";
        
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        sql 
                        , new Object[] { address }
                        , new BeanPropertyRowMapper<Wealth>(Wealth.class)
                        ));
    }

    
}