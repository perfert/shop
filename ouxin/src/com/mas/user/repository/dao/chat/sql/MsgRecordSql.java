/**    
 * 文件名：MsgRecordSql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.chat.MsgRecord;
import com.mas.user.repository.dao.chat.MsgRecordDao;

/**  
 * 项目名称：chat    
 * 类名称：    聊天记录
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class MsgRecordSql extends CrudSql<MsgRecord> implements MsgRecordDao{

    @Override
    protected InsertSql insertSql(MsgRecord bean) {
        return
                new InsertSql( tableName() )
                    .addField("MSG_ID", bean.getMsgId())
                    .addField( "TO_ACCOUNT", bean.getToAccount() )
                    .addField( "FROM_ACCOUNT", bean.getFromAccount() )
                    .addField( "IS_READ", bean.getIsRead() )
                    .addField( "TOKEN", bean.getToken() )
                    .addField( "CHAT_TYPE", bean.getChatType() );
    }
    
    @Override
    protected UpdateSql updateSql(MsgRecord bean) {
        return
                new UpdateSql( tableName() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return MsgRecord.TABLE_NAME;
    }
}
