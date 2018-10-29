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
import com.mas.system.domain.entity.SmsConfig;
import com.mas.system.repository.dao.SmsConfigDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-11-22  
 */
@Repository
public class SmsConfigSql extends CrudSql<SmsConfig> implements SmsConfigDao
{

    @Override
    protected InsertSql insertSql( SmsConfig bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "IS_DEFAULT", bean.getName() )
                    .addField( "ACCOUNT", bean.getName() )
                    .addField( "PASSWORD", bean.getName() )
                    .addField( "NONCE_STRING", bean.getName() )
                    .addField( "SMS_API", bean.getName() )
                    .addField( "SMS_MODULE_CODE", bean.getName() )
                    .addField( "MESSAGE_API", bean.getName() )
                    .addField( "MESSAGE_MODULE_CODE", bean.getName() );
    }

    @Override
    protected UpdateSql updateSql( SmsConfig bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "IS_DEFAULT", bean.getName() )
                    .addField( "ACCOUNT", bean.getName() )
                    .addField( "PASSWORD", bean.getName() )
                    .addField( "NONCE_STRING", bean.getName() )
                    .addField( "SMS_API", bean.getName() )
                    .addField( "SMS_MODULE_CODE", bean.getName() )
                    .addField( "MESSAGE_API", bean.getName() )
                    .addField( "MESSAGE_MODULE_CODE", bean.getName() );
    }

    @Override
    protected String tableName()
    {
        return SmsConfig.TABLE_NAME;
    }
}