/**    
 * 文件名：SignQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.chat.sql;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.chat.Sign;
import com.mas.user.domain.entity.chat.SignMember;
import com.mas.user.repository.query.chat.SignQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class SignQuerySql extends QuerySql<Sign> implements SignQueryDao{

    @Override
    public boolean exist(String name) {
        String hql = " select COUNT(*) FROM " + tableName() + " bean where bean.NAME = ?";
        long count = DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(hql, new Object[] { name }, new SingleColumnRowMapper<Long>(Long.class)) );
        return count > 0 ? true : false;
    }
    
    @Override
    public List<Sign> queryAll(String memberId) {
        String queryString = "SELECT bean.* from "+ tableName() + " bean WHERE bean.MEMBER_ID = ? ORDER BY bean.MODIFY_DATE DESC  ";
        return getJdbcTemplate().query(
                queryString,new Object[]{memberId}
                , new BeanPropertyRowMapper<Sign>(Sign.class)
                );
    }
    
    @Override
    public List<Sign> getSignListByUsername(String username, String friends) {
        String queryString = "SELECT bean.* from "+ tableName() + " bean LEFT JOIN " + SignMember.TABLE_NAME + " sm ON bean.id = sm.SIGN_ID " + 
        		" WHERE bean.USERNAME = ? AND sm.USERNAME = ? ORDER BY bean.MODIFY_DATE DESC  ";
        return getJdbcTemplate().query(
                queryString,new Object[]{username,friends}
                , new BeanPropertyRowMapper<Sign>(Sign.class)
                );
    }
    
    @Override
    public List<Sign> getSignListByUserId(String userId, String friendId) {
        String queryString = "SELECT bean.* from "+ tableName() + " bean LEFT JOIN " + SignMember.TABLE_NAME + " sm ON bean.id = sm.SIGN_ID " + 
                " WHERE bean.MEMBER_ID = ? AND sm.MEMBER_ID = ? ORDER BY bean.MODIFY_DATE DESC  ";
        return getJdbcTemplate().query(
                queryString,new Object[]{userId,friendId}
                , new BeanPropertyRowMapper<Sign>(Sign.class)
                );
    }

    @Override
    protected SimpleQuerySql querySql(Sign query) {
        return
                new SimpleQuerySql( tableName() )
                    .andEqWhereIfNotBlankValue( "USERNAME", query.getUsername() );
    }

    @Override
    protected String tableName() {
        return Sign.TABLE_NAME;
    }

    


}
