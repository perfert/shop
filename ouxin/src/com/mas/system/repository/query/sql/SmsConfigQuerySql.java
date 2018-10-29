/**    
 * 文件名：SmsConfigQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2017-11-22    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.system.repository.query.sql;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.system.domain.entity.SmsConfig;
import com.mas.system.repository.query.SmsConfigQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-22  
 */
@Repository
public class SmsConfigQuerySql extends QuerySql<SmsConfig> implements SmsConfigQueryDao
{


    @Override
    public SmsConfig getDefault() {
        String sql = "SELECT bean.* FROM " + tableName() + " bean WHERE bean.IS_DEFAULT = ?";
        return
                DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                sql
                                , new Object[] { true }
                                , new BeanPropertyRowMapper<SmsConfig>( SmsConfig.class )
                                )
                        );
    }

   
    @Override
    protected String tableName()
    {
        return SmsConfig.TABLE_NAME;
    }

   
    @Override
    protected SimpleQuerySql querySql(SmsConfig query) {
        // TODO Auto-generated method stub
        return null;
    }
}
