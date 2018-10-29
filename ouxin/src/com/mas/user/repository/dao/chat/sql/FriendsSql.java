/**    
 * 文件名：FriendsSql.java    
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
import com.mas.user.domain.entity.chat.Friends;
import com.mas.user.repository.dao.chat.FriendsDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class FriendsSql extends CrudSql<Friends> implements FriendsDao{

    @Override
    public boolean deleteFriends(String userId, String friendId) {
        return 0 <
                getJdbcTemplate().update(
                    "DELETE FROM " + tableName() + " WHERE (MEMBER_ID = ? and FRIEND_ID = ?) or (MEMBER_ID = ? and FRIEND_ID = ?)"
                    , new Object[] { userId,friendId,friendId,userId }
                    );
    }
    
    @Override
    protected InsertSql insertSql(Friends bean) {
        return
                new InsertSql( tableName() )
                    .addField( "SING_ID", bean.getSingId() )
                    .addField( "ALIAS", bean.getAlias() )
                    
                    .addField( "PHONE", bean.getPhone() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "IMG", bean.getImg() )
                    
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "FRIEND_ID", bean.getFriendId() )
                    .addField( "MEMBER_ACCOUNT", bean.getMemberAccount() )
                    .addField( "FRIEND_ACCOUNT", bean.getFriendAccount() );
    }
    
    @Override
    protected UpdateSql updateSql(Friends bean) {
        return
                new UpdateSql( tableName() )
                    .addField( "SING_ID", bean.getSingId() )
                    .addField( "ALIAS", bean.getAlias() )
                    .addField( "PHONE", bean.getPhone() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "IMG", bean.getImg() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return Friends.TABLE_NAME;
    }

    
    
}
