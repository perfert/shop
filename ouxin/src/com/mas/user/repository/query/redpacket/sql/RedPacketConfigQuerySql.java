/**    
 * 文件名：RedPacketConfigQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.redpacket.sql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.rp.RedPacketConfig;
import com.mas.user.repository.query.redpacket.RedPacketConfigQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class RedPacketConfigQuerySql extends QuerySql<RedPacketConfig> implements RedPacketConfigQueryDao{

    
    @Override
    protected SimpleQuerySql querySql(RedPacketConfig query) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String tableName() {
        return RedPacketConfig.TABLE_NAME;
    }

    @Override
    public RedPacketConfig getDefault() {
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        " SELECT bean.* FROM " + tableName() + " bean WHERE IS_DEFAULT = ?"
                        , new Object[] { true }
                        , new BeanPropertyRowMapper<RedPacketConfig>(RedPacketConfig.class)
                        ));
    }
}