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

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.shops.domain.entity.Mortagage;
import com.mas.shops.repository.dao.MortagageDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-29  
 */
@Repository
public class MortagageSql  extends CrudSql<Mortagage> implements MortagageDao {

    public Mortagage get( Object id )
    {
        String selectSql = "SELECT bean.*,t.NAME AS TYPE_NAME FROM " + tableName() + " bean LEFT JOIN " + Mortagage.TABLE_NAME + " t ON bean.WEALTH_TYPE_ID = t.ID WHERE bean.ID = ? ";
            return
                ( Mortagage ) DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                        selectSql
                            , new Object[] { id }
                            , new BeanPropertyRowMapper<Mortagage>(Mortagage.class)
                            ));
        
    }
    
    
    @Override
    protected InsertSql insertSql( Mortagage bean )
    {

        return 
                new InsertSql( tableName() )  
                    .addField( "CASH", bean.getCash() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                     .addField( "STATUS", bean.getStatus() )
                    .addField( "WEALTH_ID", bean.getWealthId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() );
    }

    @Override
    protected UpdateSql updateSql( Mortagage bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "CASH", bean.getCash() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                      .addField( "STATUS", bean.getStatus() )
                    .addField( "WEALTH_ID", bean.getWealthId() )
                    .addField( "WEALTH_TYPE_ID", bean.getWealthTypeId() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return Mortagage.TABLE_NAME;
    }


    @Override
    public boolean getNoBackMortagage(Object id, int status) {
        return 0 < getJdbcTemplate().update(" UPDATE " + tableName() + " SET  STATUS = ? WHERE ID = ?  ", new Object[] { status, id});
    }
    
    

}