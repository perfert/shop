/**    
 * 文件名：MsgRecordQuerySql.java    
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
import com.mas.user.repository.query.chat.MsgRecordQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class MsgRecordQuerySql extends QuerySql<MsgRecord> implements MsgRecordQueryDao{

    @Override
    public MsgRecord getByToken(String from, String token) {
        String hql = " select bean.* FROM " + tableName() + " bean where bean.FROM_ACCOUNT = ? AND bean.TOKEN = ? ";
        return DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                hql,new Object[] { from, token}
                , new BeanPropertyRowMapper<MsgRecord>(MsgRecord.class)
                ));
    }
    
    @Override
    public MsgRecord getAllInfo(Object id) {
        //如何能转为对象的对象
        String selectSql = "SELECT bean.*,m.TYPE AS MSG_TYPE,m.CONTENT AS CONTENT,m.URI AS URI,m.LENGTH AS LENGTH,m.THUMB AS THUMB," +
        		"m.FILE_NAME AS FILE_NAME,m.WIDTH AS WIDTH,m.HEIGHT AS HEIGHT,m.ADDRESS AS ADDRESS,m.LAT AS LAT,m.LNG AS LNG  FROM " 
                + tableName() + " bean LEFT JOIN " + Msg.TABLE_NAME + " m ON bean.MSG_ID = m.ID WHERE bean.ID = ?  ";
        return DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                                selectSql
                                , new Object[]{id}
                                , new BeanPropertyRowMapper<MsgRecord>(MsgRecord.class)
                                )
                        );
    }

    
    @Override
    public void queryPage( Page page, MsgRecord query ){
        String selectSql = "SELECT bean.*,m.TYPE AS MSG_TYPE ";
        String fromSql = " FROM " + tableName() + " bean LEFT JOIN " + Msg.TABLE_NAME + " m ON bean.MSG_ID = m.ID ";
        
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
                            , new BeanPropertyRowMapper<MsgRecord>(MsgRecord.class)
                            )
                    : getNamedParameterJdbcTemplate().query(
                            queryString
                            , sql.values()
                            , new BeanPropertyRowMapper<MsgRecord>(MsgRecord.class)
                            )
                    );
    }
    
    @Override
    public List<MsgRecord> queryAll(Object uId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(MsgRecord query) {
        return
                new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "FROM_ACCOUNT", query.getFromAccount() );
    }

    @Override
    protected String tableName() {
        return MsgRecord.TABLE_NAME;
    }

    
    

}
