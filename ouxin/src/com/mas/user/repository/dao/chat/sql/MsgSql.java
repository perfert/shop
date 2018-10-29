/**    
 * 文件名：MsgSql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat.sql;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.chat.Msg;
import com.mas.user.domain.entity.chat.MsgRecord;
import com.mas.user.repository.dao.chat.MsgDao;

/**    
 * MsgSql    
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class MsgSql extends CrudSql<Msg> implements MsgDao{

    @SuppressWarnings( { "unchecked", "rawtypes" } )
    @Override
    public Msg get( Object id ){
        String selectSql = "SELECT bean.*,m.FROM_ACCOUNT AS FROM_ACCOUNT,m.TO_ACCOUNT AS TO_ACCOUNT ";
        String fromSql = " FROM " + tableName() + " bean LEFT JOIN " + MsgRecord.TABLE_NAME + " m ON bean.ID = m.MSG_ID ";
        
            return
                ( Msg ) DataAccessUtils.uniqueResult(getJdbcTemplate().query(
                        selectSql +  fromSql + " WHERE bean.ID = ?"
                            , new Object[] { id }
                            , new BeanPropertyRowMapper(super.ormClass)
                            ));
        
    }
    
    @Override
    protected InsertSql insertSql(Msg bean) {
        return
                new InsertSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "URI", bean.getUri() )
                    .addField( "THUMB", bean.getThumb() )
                    .addField( "FILE_NAME", bean.getFileName() )
                    .addField( "LAT", bean.getLat() )
                    .addField( "LNG", bean.getLng() )
                    .addField( "LENGTH", bean.getLength() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .addField( "WIDTH", bean.getWidth() )
                    .addField( "HEIGHT", bean.getHeight() );
    }
    
    @Override
    protected UpdateSql updateSql(Msg bean) {
        return
                new UpdateSql( tableName() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return Msg.TABLE_NAME;
    }
}
