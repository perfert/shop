/**    
 * 文件名：MsgRecordSql.java    
 *    
 * 版本信息：    
 * 日期：2016-01-21 
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.chat.MsgCollectionContent;
import com.mas.user.repository.dao.chat.MsgCollectionContentDao;

/**  
 * 项目名称：chat    
 * 类名称：    MsgCollectionSql
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class MsgCollectionContentSql extends CrudSql<MsgCollectionContent> implements MsgCollectionContentDao{

    @Override
    public boolean delete(Object msgCollectionId) {
        return 0 < getJdbcTemplate().update("DELETE FROM " + tableName() + " WHERE COLLECTION_ID = ?  ", new Object[] { msgCollectionId});
    }
    
    @Override
    protected InsertSql insertSql(MsgCollectionContent bean) {
        return
                new InsertSql( tableName() )
                    .addField("COLLECTION_ID", bean.getCollectionId())
                    .addField( "MSG_TYPE", bean.getMsgType() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "URI", bean.getUri() )
                    .addField( "THUMB", bean.getThumb() )
                    .addField( "LAT", bean.getLat() )
                    .addField( "LNG", bean.getLng() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .addField( "HEIGHT", bean.getHeight() )
                    .addField( "WIDTH", bean.getWidth() )
                    .addField( "LENGTH", bean.getLength() );
    }
    
    @Override
    protected UpdateSql updateSql(MsgCollectionContent bean) {
        return
                new UpdateSql( tableName() )
        
                    .addField("COLLECTION_ID", bean.getCollectionId())
                    .addField( "MSG_TYPE", bean.getMsgType() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "URI", bean.getUri() )
                    .addField( "THUMB", bean.getThumb() )
                    .addField( "LAT", bean.getLat() )
                    .addField( "LNG", bean.getLng() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .addField( "HEIGHT", bean.getHeight() )
                    .addField( "WIDTH", bean.getWidth() )
                    .addField( "LENGTH", bean.getLength() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return MsgCollectionContent.TABLE_NAME;
    }

    
}
