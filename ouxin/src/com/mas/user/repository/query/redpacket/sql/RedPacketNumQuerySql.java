/**    
 * 文件名：RedPacketItemQuerySql.java    
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
import com.mas.user.domain.entity.rp.RedPacketNum;
import com.mas.user.repository.query.redpacket.RedPacketNumQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Repository
public class RedPacketNumQuerySql extends QuerySql<RedPacketNum> implements RedPacketNumQueryDao{
    
    @Override
    public RedPacketNum getNotValidAndAsc(String rpId) {
        String selectSql = " SELECT *  FROM " + tableName() + " where RP_ID = ? AND VALID = ? " +
        		" ORDER BY PRIORITY ASC " + pageSql(0, 1);
        
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        selectSql
                        , new Object[] {rpId,0}
                        , new BeanPropertyRowMapper<RedPacketNum>(RedPacketNum.class)
                        ));
    }
   
    @Override
    protected SimpleQuerySql querySql(RedPacketNum query) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String tableName() {
        return RedPacketNum.TABLE_NAME;
    }
   
}