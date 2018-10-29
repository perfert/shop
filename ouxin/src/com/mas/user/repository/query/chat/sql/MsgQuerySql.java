/**    
 * 文件名：MsgQuerySql.java    
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
import com.mas.user.domain.entity.chat.Msg;
import com.mas.user.domain.entity.chat.MsgRecord;
import com.mas.user.repository.query.chat.MsgQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class MsgQuerySql extends QuerySql<Msg> implements MsgQueryDao{

    @Override
    public void queryPage( Page page, Msg query ){
        String selectSql = "SELECT bean.*,m.FROM_ACCOUNT AS FROM_ACCOUNT,m.TO_ACCOUNT AS TO_ACCOUNT";
        String fromSql = " FROM " + tableName() + " bean LEFT JOIN " + MsgRecord.TABLE_NAME + " m ON bean.ID = m.MSG_ID ";
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
                            , new BeanPropertyRowMapper<Msg>(Msg.class)
                            )
                    : getNamedParameterJdbcTemplate().query(
                            queryString
                            , sql.values()
                            , new BeanPropertyRowMapper<Msg>(Msg.class)
                            )
                    );
    }
    
    
    @Override
    public List<Msg> queryAll(Object uId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(Msg query) {
        return
                new SimpleQuerySql( tableName() )
                    .andLkWhereIfNotBlankValue( "CONTENT", query.getContent() )
                    .andEqWhereIfNotBlankValue( "TYPE", query.getType() );
    }

    @Override
    protected String tableName() {
        return Msg.TABLE_NAME;
    }

}
