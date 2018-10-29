/**    
 * 文件名：FriendsApplySql.java    
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
import com.mas.user.domain.entity.chat.FriendsApply;
import com.mas.user.repository.dao.chat.FriendsApplyDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class FriendsApplySql extends CrudSql<FriendsApply> implements FriendsApplyDao{

    @Override
    public boolean updateType(String applyId, String receiveId, Integer type) {
        return 0 <
                getJdbcTemplate().update(
                    "UPDATE " + tableName() + " SET TYPE = ? WHERE APPLY_ID = ? AND RECEIVE_ID = ?"
                    , new Object[] { type, applyId, receiveId }
                    );
    }
    
    @Override
    protected InsertSql insertSql(FriendsApply bean) {
        return
                new InsertSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "MSG", bean.getMsg() )
                    .addField( "APPLY_ID", bean.getApplyId() )
                    .addField( "RECEIVE_ID", bean.getReceiveId() )
                    .addField( "APPLY_ACCOUNT", bean.getApplyAccount() )
                    .addField( "RECEIVE_ACCOUNT", bean.getReceiveAccount() );
    }
    
    @Override
    protected UpdateSql updateSql(FriendsApply bean) {
        return
                new UpdateSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "MSG", bean.getMsg() )
                    .andEqWhere( "ID", bean.getId() );
    }


    @Override
    protected String tableName() {
        return FriendsApply.TABLE_NAME;
    }

}
