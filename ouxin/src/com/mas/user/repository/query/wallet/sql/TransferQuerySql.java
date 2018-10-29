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
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.wallet.Transfer;
import com.mas.user.repository.query.wallet.TransferQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class TransferQuerySql extends QuerySql<Transfer> implements TransferQueryDao{

    @Override
    protected SimpleQuerySql querySql(Transfer query) {
        SimpleQuerySql sql =
                new SimpleQuerySql( tableName() )
                    .andEqWhereIfNotBlankValue( "MEMBER_ID", query.getMemberId() )
                    .addDesc( "ID" );
        return sql;
    }

    @Override
    protected String tableName() {
        return Transfer.TABLE_NAME;
    }

    @Override
    public Transfer getByReceiveInfo(Object transferId) {
        String sql = " SELECT bean.*,u.NICK_NAME AS NICK_NAME FROM " + tableName() + " bean LEFT JOIN  " + Member.TABLE_NAME + " u ON bean.RECEIVE_ID = u.ID " +
                    " WHERE bean.ID = ?";
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        sql
                        , new Object[] { transferId }
                        , new BeanPropertyRowMapper<Transfer>(Transfer.class)
                        ));
    }

    
}