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
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.wallet.Ofc;
import com.mas.user.repository.query.wallet.OfcQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class OfcQuerySql extends QuerySql<Ofc> implements OfcQueryDao{

    @Override
    public boolean exist(String sn) {
        return
                0 < DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                "SELECT COUNT(bean.ID) FROM " + tableName() + " bean WHERE bean.SN = ? "
                                , new Object[] { sn }
                                , new SingleColumnRowMapper<Long>(Long.class)
                                )
                        );
    }
    
    @Override
    protected SimpleQuerySql querySql(Ofc query) {
        SimpleQuerySql sql =
                new SimpleQuerySql( tableName() )
                    .andEqWhereIfNotBlankValue( "MEMBER_ID", query.getMemberId() )
                    .addDesc( "ID" );
        return sql;
    }

    @Override
    protected String tableName() {
        return Ofc.TABLE_NAME;
    }

    

    
}
