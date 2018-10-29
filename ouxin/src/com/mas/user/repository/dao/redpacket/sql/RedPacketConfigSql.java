/**    
 * 文件名：RedPacketConfigSql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.redpacket.sql;


import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.rp.RedPacketConfig;
import com.mas.user.repository.dao.redpacket.RedPacketConfigDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class RedPacketConfigSql extends CrudSql<RedPacketConfig> implements RedPacketConfigDao{

    @Override
    protected InsertSql insertSql( RedPacketConfig bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "IS_DEFAULT", bean.getIsDefault() )
                    .addField( "MAX", bean.getMax() )
                    .addField( "MIN", bean.getMin() )
                    .addField( "NAME", bean.getName() );
    }

    @Override
    protected UpdateSql updateSql( RedPacketConfig bean )
    {
        return
                new UpdateSql( tableName() )
                    //.addField( "IS_DEFAULT", bean.getIsDefault() )
                    .addField( "MAX", bean.getMax() )
                    .addField( "MIN", bean.getMin() )
                    .addField( "NAME", bean.getName() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return RedPacketConfig.TABLE_NAME;
    }

    
    
    
}
