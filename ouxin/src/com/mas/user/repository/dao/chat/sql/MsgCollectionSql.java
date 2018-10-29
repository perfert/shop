/**    
 * 文件名：MsgRecordSql.java    
 *    
 * 版本信息：    
 * 日期：2016-01-14  
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.chat.MsgCollection;
import com.mas.user.repository.dao.chat.MsgCollectionDao;

/**  
 * 项目名称：chat    
 * 类名称：    MsgCollectionSql
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class MsgCollectionSql extends CrudSql<MsgCollection> implements MsgCollectionDao{

    @Override
    public boolean delete(Object msgCollectionId, String memberId) {
        return 0 < getJdbcTemplate().update("DELETE FROM " + tableName() + " WHERE ID = ? AND MEMBER_ID = ? ", new Object[] { msgCollectionId, memberId});
    }
    
    @Override
    protected InsertSql insertSql(MsgCollection bean) {
        return
                new InsertSql( tableName() )
                    .addField("TYPE", bean.getType())
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "COVER_MEMBER_ID", bean.getCoverMemberId() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "IMAGE_URL", bean.getImageUrl() )
                    .addField( "VIDEO_URL", bean.getVideoUrl() )
                    .addField( "VIDEO_TIME", bean.getVideoTime() )
                    .addField( "THUMB", bean.getThumb() )
                    .addField( "VOICE_URL", bean.getVoiceUrl() )
                    .addField( "VOICE_TIME", bean.getVoiceTime() )
                    .addField( "LAT", bean.getLat() )
                    .addField( "LNG", bean.getLng() )
                    .addField( "LOCATION_URL", bean.getLocationUrl() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .addField( "FILE_URL", bean.getFileUrl() )
                    .addField( "FILE_SIZE", bean.getFileSize() );
    }
    
    @Override
    protected UpdateSql updateSql(MsgCollection bean) {
        return
                new UpdateSql( tableName() )
                    .addField("TYPE", bean.getType())
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "COVER_MEMBER_ID", bean.getCoverMemberId() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "IMAGE_URL", bean.getImageUrl() )
                    .addField( "VIDEO_URL", bean.getVideoUrl() )
                    .addField( "VIDEO_TIME", bean.getVideoTime() )
                    .addField( "THUMB", bean.getThumb() )
                    .addField( "VOICE_URL", bean.getVoiceUrl() )
                    .addField( "VOICE_TIME", bean.getVoiceTime() )
                    .addField( "LAT", bean.getLat() )
                    .addField( "LNG", bean.getLng() )
                    .addField( "LOCATION_URL", bean.getLocationUrl() )
                    .addField( "ADDRESS", bean.getAddress() )
                    .addField( "FILE_RUL", bean.getFileUrl() )
                    .addField( "FILE_SIZE", bean.getFileSize() )
        
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return MsgCollection.TABLE_NAME;
    }

    
}
