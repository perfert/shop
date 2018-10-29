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

import java.util.Date;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.common.util.DateTimeUtil;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.system.domain.entity.SmsCode;
import com.mas.system.repository.query.SmsCodeQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-22  
 */
@Repository
public class SmsCodeQuerySql extends QuerySql<SmsCode> implements SmsCodeQueryDao
{

    @Override
    public SmsCode get(int type, String mobile, String mobileCode, String code) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean exist(int type, String ituTCode, String mobile, String code) {
        String sql = " SELECT COUNT(ID) FROM " + tableName() + " bean WHERE TYPE = ? AND MOBILE_CODE = ?  AND MOBILE = ? AND  CODE = ? " +
                " AND bean.CREATE_DATE > ? ";       
        return
                0 < DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                sql
                                , new Object[] { type,ituTCode, mobile,code, DateTimeUtil.addMinutes(new Date(), -10)}
                                , new SingleColumnRowMapper<Long>( Long.class )
                                )
                        );
    }
    
    /*@Override
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
    }*/

   
    @Override
    protected String tableName()
    {
        return SmsCode.TABLE_NAME;
    }

   
    @Override
    protected SimpleQuerySql querySql(SmsCode query) {
        // TODO Auto-generated method stub
        return null;
    }


    
}
