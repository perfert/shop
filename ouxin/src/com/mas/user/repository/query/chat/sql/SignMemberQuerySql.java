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
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Sign;
import com.mas.user.domain.entity.chat.SignMember;
import com.mas.user.repository.query.chat.SignMemberQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class SignMemberQuerySql extends QuerySql<SignMember> implements SignMemberQueryDao{

    @Override
    public List<SignMember> queryAll(String userId, String friendId) {
        String queryString = "SELECT bean.* from "+ tableName() + " bean LEFT JOIN " + Sign.TABLE_NAME + " sign ON bean.SIGN_ID = sign.ID " +
                " WHERE sign.MEMBER_ID = ? AND bean.MEMBER_ID = ? ";
        return getJdbcTemplate().query(
                queryString,new Object[]{userId,friendId}
                , new BeanPropertyRowMapper<SignMember>(SignMember.class)
                );
    }
    
    @Override
    public SignMember exist(Object signId, String userId) {
        String hql = " select bean.* FROM " + tableName() + " bean where bean.SIGN_ID = ? AND bean.MEMBER_ID = ? ";
        return DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                hql,new Object[] { signId, userId}
                , new BeanPropertyRowMapper<SignMember>(SignMember.class)
                ));
    }
    
    @Override
    public List<SignMember> queryAll(Object signId) {
        String queryString = "SELECT bean.*,u.AVATAR AS AVATAR,u.NICK_NAME AS NICK_NAEM from "+ tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " 
                    + "WHERE bean.SIGN_ID = ? ";
        return getJdbcTemplate().query(
                queryString,new Object[]{signId}
                , new BeanPropertyRowMapper<SignMember>(SignMember.class)
                );
    }

    @Override
    protected SimpleQuerySql querySql(SignMember query) {
        return
                new SimpleQuerySql( tableName() )
                    .andEqWhereIfNotBlankValue( "USERNAME", query.getUsername() );
    }

    @Override
    protected String tableName() {
        return SignMember.TABLE_NAME;
    }

    

    

}
