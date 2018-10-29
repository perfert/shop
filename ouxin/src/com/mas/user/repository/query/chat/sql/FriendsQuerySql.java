/**    
 * 文件名：FriendsQuerySql.java    
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
import com.mas.common.orm.sql.util.Symbol;
import com.mas.common.orm.util.Page;
import com.mas.common.verify.VerifyUtil;
import com.mas.core.domain.vo.StateVo;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Friends;
import com.mas.user.repository.query.chat.FriendsQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 */
@Repository
public class FriendsQuerySql extends QuerySql<Friends> implements FriendsQueryDao{

    @Override
    public boolean exist(String uid, String fid) {
        String hql = " select COUNT(*) FROM " + tableName() + " bean where bean.MEMBER_ID = ? AND bean.FRIEND_ID = ? ";
        long count = DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(hql, new Object[] { uid, fid}, new SingleColumnRowMapper<Long>(Long.class)) );
        return count > 0 ? true : false;
    }
    
    @Override
    public Friends getFriends(Object applyId, Object targetId) {
        SimpleQuerySql sql = new SimpleQuerySql( tableName() )
        .andWhere( "MEMBER_ID", Symbol.eq, applyId)
        .andWhere( "FRIEND_ID", Symbol.eq, targetId);
        return DataAccessUtils.uniqueResult(getNamedParameterJdbcTemplate().query(
                sql.sql()
                , sql.values()
                , new BeanPropertyRowMapper<Friends>(Friends.class)
                ));
    }
    
    @Override
    public Friends getFriendsByUserId(String userId, String friendId) {
        String selectSql = " SELECT bean.* FROM " + tableName() + " bean  " +
                " WHERE bean.MEMBER_ID =? AND bean.FRIEND_ID = ?  ";
        
        return DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                selectSql
                ,  new Object[] { userId ,friendId}
                , new BeanPropertyRowMapper<Friends>(Friends.class)
                )); 
    }
    
    @Override
    public Friends getFriendsByUsername(String username, String friend) {
        String selectSql = " SELECT bean.* FROM " + tableName() + " bean  " +
                " WHERE bean.MEMBER_ACCOUNT =? AND bean.FRIEND_ACCOUNT = ?  ";
        
        return DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                selectSql
                ,  new Object[] { username ,friend}
                , new BeanPropertyRowMapper<Friends>(Friends.class)
                )); 
    }
    
    @Override
    public List<Member> getFriendsList(String userName) {
        String selectSql = " SELECT u.ID,u.ACCOUNT,u.NICK_NAME,u.AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.FRIEND_ACCOUNT = u.ACCOUNT " +
        		" WHERE bean.MEMBER_ACCOUNT =? AND bean.STATE = ?  ";
        
        return getJdbcTemplate().query(
                selectSql
                ,  new Object[] { userName ,StateVo.ENABLE.value()}
                , new BeanPropertyRowMapper<Member>(Member.class)
                ); 
    }
    
    @Override
    public List<Member> getFriendsListByUserId(String mid) {
        String selectSql = " SELECT u.*,bean.ALIAS AS ALIAS  FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.FRIEND_ID = u.ID " +
                " WHERE bean.MEMBER_ID =? AND bean.STATE = ?  ";
        
        return getJdbcTemplate().query(
                selectSql
                ,  new Object[] { mid ,StateVo.ENABLE.value()}
                , new BeanPropertyRowMapper<Member>(Member.class)
                ); 
    }

    
    @Override
    public List<Member> getFriendsList2(String userName) {
        String selectSql = " SELECT u.*,bean.ALIAS AS ALIAS  FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.FRIEND_ACCOUNT = u.USERNAME " +
                " WHERE bean.MEMBER_ACCOUNT =? AND bean.STATE = ?  ";
        
        return getJdbcTemplate().query(
                selectSql
                ,  new Object[] { userName ,StateVo.ENABLE.value()}
                , new BeanPropertyRowMapper<Member>(Member.class)
                ); 
    }
    
    /**
     * BeanPropertyRowMapper类与ParameterizedBeanPropertyRowMapper类的功能完全相同，当POJO对象和数据库表字段完全对应或者驼峰式与下划线式对应时，
     * 该类会根据构造函数中传递的class来自动填充数据。只是ParameterizedBeanPropertyRowMapper类使用泛型需要JDK5+支持。
     * 这里需要注意虽然这两个类提供了便利，但是由于使用反射导致性能下降，所以如果需要高性能则还是需要自己去实现RowMapper接口来包装数据。
     */
    @Override
    public void queryPage( Page page, Friends query ){
        String selectSql = "SELECT bean.*, u.NICK_NAME AS MEMBER_NICK_NAME, u2.NICK_NAME AS FRIEND_NICK_NAME ";
        String fromSql = " FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
        		" LEFT JOIN " + Member.TABLE_NAME + " u2 ON bean.FRIEND_ID = u2.ID ";
        
        SimpleQuerySql sql = null == query ? new SimpleQuerySql( tableName() ).addDesc( "ID" )
                : querySql( query ).andEqWhereIfNotBlankValue( "STATE", query.getState() );
        
        if( page.isAutoCount() ){
            page.setTotalCount(
                    DataAccessUtils.uniqueResult(
                            VerifyUtil.isEmpty( sql.values() ) ?
                                getJdbcTemplate().query(
                                        "SELECT COUNT(*) " + fromSql + sql.where()
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                : getNamedParameterJdbcTemplate().query(
                                        "SELECT COUNT(*) " + fromSql + sql.where()
                                        , sql.values()
                                        , new SingleColumnRowMapper<Long>(Long.class)
                                        )
                                )
                    );
        }
        String queryString = selectSql + fromSql + sql.where() + " " + sql.orderBy() + " " + pageSql( page );
        page.setResult(
                VerifyUtil.isEmpty( sql.values() ) ?
                    getJdbcTemplate().query(
                            queryString
                            , new BeanPropertyRowMapper<Friends>(Friends.class)
                            )
                    : getNamedParameterJdbcTemplate().query(
                            queryString
                            , sql.values()
                            , new BeanPropertyRowMapper<Friends>(Friends.class)
                            )
                    );
    }
    
    @Override
    public List<Friends> queryAll(Object uId) {
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(Friends query) {
        return
                new SimpleQuerySql( tableName() )
                    .andEqWhereIfNotBlankValue( "MEMBER_ID", query.getMemberId() )
                    .andEqWhereIfNotBlankValue( "FRIEND_ACCOUNT", query.getFriendAccount() );
    }

    @Override
    protected String tableName() {
        return Friends.TABLE_NAME;
    }

   
    

   

}
