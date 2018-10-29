/**    
 * 文件名：RedPacketBackQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-21    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.redpacket.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.rp.RedPacketBack;
import com.mas.user.repository.query.redpacket.RedPacketBackQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-21  
 */
@Repository
public class RedPacketBackQuerySql extends QuerySql<RedPacketBack> implements RedPacketBackQueryDao{
    
    @Override
    protected SimpleQuerySql querySql(RedPacketBack query) {
        return null;
    }

    @Override
    protected String tableName() {
        return RedPacketBack.TABLE_NAME;
    }
   
}