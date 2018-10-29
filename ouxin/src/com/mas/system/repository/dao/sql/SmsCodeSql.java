/**    
 * 文件名：SmsConfigSql.java    
 *    
 * 版本信息：    
 * 日期：2017-11-22    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.system.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.system.domain.entity.SmsCode;
import com.mas.system.repository.dao.SmsCodeDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-22  
 */
@Repository
public class SmsCodeSql extends CrudSql<SmsCode> implements SmsCodeDao
{

    @Override
    protected InsertSql insertSql( SmsCode bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "MOBILE", bean.getMobile() )
                    .addField( "MOBILE_CODE", bean.getMobileCode() )
                    .addField( "CODE", bean.getCode() );
    }

    @Override
    protected UpdateSql updateSql( SmsCode bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "MOBILE", bean.getMobile() )
                    .addField( "MOBILE_CODE", bean.getMobileCode() )
                    .addField( "CODE", bean.getCode() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName()
    {
        return SmsCode.TABLE_NAME;
    }
}