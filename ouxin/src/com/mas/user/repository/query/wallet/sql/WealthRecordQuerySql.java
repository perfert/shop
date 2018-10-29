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
import com.mas.user.domain.entity.wallet.WealthRecord;
import com.mas.user.repository.query.wallet.WealthRecordQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class WealthRecordQuerySql extends QuerySql<WealthRecord> implements WealthRecordQueryDao{

    @Override
    protected SimpleQuerySql querySql(WealthRecord query) {
        SimpleQuerySql sql =
                new SimpleQuerySql( tableName() )
                    .andEqWhereIfNotBlankValue( "MEMBER_ID", query.getMemberId() )
                    .addDesc( "ID" );
        return sql;
    }

    @Override
    protected String tableName() {
        return WealthRecord.TABLE_NAME;
    }

    @Override
    public void queryPage(String mid, Integer dataType, Page page) {
        String sql = null;
        Object[] objs = null;
        if(dataType == 1){
            objs = new Object[]{mid,0};
            sql = " FROM " + tableName() + " bean  WHERE bean.MEMBER_ID  = ? AND bean.TYPE = ?  ";
        }else if(dataType == 2){
            objs = new Object[]{mid,1};
            sql = " FROM " + tableName() + " bean  WHERE bean.MEMBER_ID  = ? AND bean.TYPE = ?  ";
        }else{
            objs = new Object[]{mid};
            sql = " FROM " + tableName() + " bean  WHERE bean.MEMBER_ID  = ? ";
        }
        
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
                , new BeanPropertyRowMapper<WealthRecord>(WealthRecord.class)
                ));
    }

    @Override
    public WealthRecord getMemberInfo(String detailId) {
        // TODO Auto-generated method stub
        return null;
    }

}
