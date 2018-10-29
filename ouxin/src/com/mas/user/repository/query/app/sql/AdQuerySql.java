/**    
 * 文件名：AdQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.app.sql;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.orm.util.Page;
import com.mas.common.verify.VerifyUtil;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.app.Ad;
import com.mas.user.domain.entity.app.AdPosition;
import com.mas.user.repository.query.app.AdQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class AdQuerySql extends QuerySql<Ad> implements AdQueryDao{
    
    @Override
    public List<Ad> queryAdListByPositionId(Object positionId) {
        String queryString = "SELECT bean.id from "+ tableName() + " bean WHERE bean.POSITION_ID = ? ";
        return getJdbcTemplate().query(
                queryString,new Object[]{positionId}
                , new BeanPropertyRowMapper<Ad>(Ad.class)
                );
    }
    
    @Override
    public void queryPage( Page page, Ad query ){ 
        String selectSql = "SELECT bean.*, p.NAME AS POSITION_NAME ";
        String fromSql = " FROM " + tableName() + " bean LEFT JOIN " + AdPosition.TABLE_NAME + " p ON bean.POSITION_ID = p.ID ";
        
        SimpleQuerySql sql = null == query ? new SimpleQuerySql( tableName() ).addDesc( "MODIFY_DATE" )
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
                            , new BeanPropertyRowMapper<Ad>(Ad.class)
                            )
                    : getNamedParameterJdbcTemplate().query(
                            queryString
                            , sql.values()
                            , new BeanPropertyRowMapper<Ad>(Ad.class)
                            )
                    );
    }

    @Override
    public List<Ad> queryAll(Object uId) {
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(Ad query) {
        return  new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "TITLE", query.getTitle() )
                .andEqWhereIfNotBlankValue( "POSITION_ID", query.getPositionId() );
    }

    @Override
    protected String tableName() {
        return Ad.TABLE_NAME;
    }

    
}
