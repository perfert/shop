/**    
 * 文件名：MortagageConfigSql.java    
 *    
 * 版本信息：    
 * 日期：2018-3-29    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.repository.dao.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.shops.domain.entity.MortagageConfig;
import com.mas.shops.repository.dao.MortagageConfigDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-29  
 */
@Repository
public class MortagageConfigSql  extends CrudSql<MortagageConfig> implements MortagageConfigDao {

    @Override
    protected InsertSql insertSql( MortagageConfig bean )
    {

        return 
                new InsertSql( tableName() )  
                    .addField( "NAME", bean.getName() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "WEALTH_ID", bean.getWealthId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() );
    }

    @Override
    protected UpdateSql updateSql( MortagageConfig bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "WEALTH_ID", bean.getWealthId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return MortagageConfig.TABLE_NAME;
    }
    
    

}