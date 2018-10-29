/**    
 * 文件名：FriendsBlackSql.java    
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
import com.mas.user.domain.entity.chat.FriendsBlack;
import com.mas.user.repository.dao.chat.FriendsBlackDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class FriendsBlackSql extends CrudSql<FriendsBlack> implements FriendsBlackDao{

    @Override
    protected InsertSql insertSql(FriendsBlack bean) {
        return
                new InsertSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "TARGET_ID", bean.getTargetId() )
                    .addField( "MEMBER_ACCOUNT", bean.getMemberAccount() )
                    .addField( "TARGET_ACCOUNT", bean.getTargetAccount() );
    }
    
    @Override
    protected UpdateSql updateSql(FriendsBlack bean) {
        return
                new UpdateSql( tableName() )
                    .addField( "TYPE", bean.getType() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return FriendsBlack.TABLE_NAME;
    }

}