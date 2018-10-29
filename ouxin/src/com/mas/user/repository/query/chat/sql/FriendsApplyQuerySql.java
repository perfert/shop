/**    
 * 文件名：FriendsApplyQuerySql.java    
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
import com.mas.common.orm.util.Page;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.FriendsApply;
import com.mas.user.repository.query.chat.FriendsApplyQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class FriendsApplyQuerySql extends QuerySql<FriendsApply> implements FriendsApplyQueryDao{

    @Override
    public List<FriendsApply> getListByUserId(String userId) {
        /*String selectSql = " SELECT bean.*,u.NICK_NAME AS APPLY_NICK_NAME,u2.NICK_NAME AS RECEIVE_NICK_NAME,u.LOGO AS APPLY_AVATAR,u2.LOGO AS RECEIVE_AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME +
                " u ON bean.APPLY_ID = u.ID " + " LEFT JOIN " + Member.TABLE_NAME +
                " u2 ON bean.RECEIVE_ID = u2.ID " +
                " WHERE bean.APPLY_ID =? OR bean.RECEIVE_ID = ? " ;
        
        return getJdbcTemplate().query(
                selectSql
                ,  new Object[] { userId , userId}
                , new BeanPropertyRowMapper<FriendsApply>(FriendsApply.class)
                ); */
        String selectSql = " SELECT bean.*,u.NICK_NAME AS APPLY_NICK_NAME,u.AVATAR AS APPLY_AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME +
        " u ON bean.APPLY_ID = u.ID WHERE bean.RECEIVE_ID = ? " ;

        return getJdbcTemplate().query(
            selectSql
            ,  new Object[] {userId}
            , new BeanPropertyRowMapper<FriendsApply>(FriendsApply.class)
            ); 
    }
    
    @Override
    public FriendsApply get(String applyId, String receiveId) {
        return DataAccessUtils.uniqueResult( getJdbcTemplate().query(
                            "SELECT bean.* FROM " + tableName() + " bean WHERE APPLY_ID = ? AND RECEIVE_ID = ?"
                            , new Object[] { applyId,receiveId }
                            , new BeanPropertyRowMapper<FriendsApply>(FriendsApply.class)
                            )
        );
    }

    
    @Override
    public void queryPage( Page page, FriendsApply query ){
        String selectSql = "SELECT bean.*, u.NICK_NAME AS APPLY_NICK_NAME, u.ACCOUNT AS APPLY_ACCOUNT,u2.NICK_NAME AS RECEIVE_NICK_NAME,u2.ACCOUNT AS RECEIVE_ACCOUNT ";
        String fromSql = " FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.APPLY_ID = u.ID " +
                " LEFT JOIN " + Member.TABLE_NAME + " u2 ON bean.RECEIVE_ID = u2.ID ";
        
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
                            , new BeanPropertyRowMapper<FriendsApply>(FriendsApply.class)
                            )
                    : getNamedParameterJdbcTemplate().query(
                            queryString
                            , sql.values()
                            , new BeanPropertyRowMapper<FriendsApply>(FriendsApply.class)
                            )
                    );
    }
    
    @Override
    public List<FriendsApply> queryAll(Object uId) {
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(FriendsApply query) {
        return
                new SimpleQuerySql( tableName() )
                    .andEqWhereIfNotBlankValue( "APPLY_ACCOUNT", query.getApplyAccount() )
                    .andEqWhereIfNotBlankValue( "RECEIVE_ACCOUNT", query.getReceiveAccount() );
    }

    @Override
    protected String tableName() {
        return FriendsApply.TABLE_NAME;
    }


    

    
}
