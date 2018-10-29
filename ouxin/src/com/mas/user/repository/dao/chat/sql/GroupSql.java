/**    
 * 文件名：GroupSql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat.sql;

import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.chat.Group;
import com.mas.user.domain.entity.chat.GroupMember;
import com.mas.user.repository.dao.chat.GroupDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class GroupSql extends CrudSql<Group> implements GroupDao{

    @Override
    public boolean delete(Object id) {
        super.delete(id);
        //删除群和群的会员
        return 0 < getJdbcTemplate().update("DELETE FROM " + GroupMember.TABLE_NAME + " WHERE GROUP_ID = ? ", new Object[] { id});
    }
    
    @Override
    public boolean updateAvatar(String outPath, Object groupId) {
        return 0 <
                getJdbcTemplate().update(
                    "UPDATE " + tableName() + " SET AVATAR = ? WHERE ID = ?"
                    , new Object[] { outPath,groupId }
                    );
    }
    
    @Override
    public boolean updateGroupCount(Object groupId, int count) {
        return 0 <
                getJdbcTemplate().update(
                    "UPDATE " + tableName() + " SET NOW_COUNT = ? WHERE ID = ?"
                    , new Object[] { count,groupId }
                    );
    }
    
    
    @Override
    protected InsertSql insertSql( Group bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "AVATAR", bean.getAvatar() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "OPEN", bean.getOpen() )
                    .addField( "ALLOW", bean.getAllow() )
                    .addField( "MAXUSERS", bean.getMaxusers() )
                    .addField( "NOW_COUNT", bean.getNowCount() )
                    .addField( "PUSH_GROUP_ID", bean.getPushGroupId() )
                    .addField( "OWNER", bean.getOwner() )
                    .addField( "OWNER_ACCOUNT", bean.getOwnerAccount() );
    }

    @Override
    protected UpdateSql updateSql( Group bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "NAME", bean.getName() )
                    .addField( "AVATAR", bean.getAvatar() )
                    .addField( "DETAIL", bean.getDetail() )
                    .addField( "OPEN", bean.getOpen() )
                    .addField( "ALLOW", bean.getAllow() )
                    .addField( "MAXUSERS", bean.getMaxusers() )
                    .addField( "NOW_COUNT", bean.getNowCount() )
                    .addField( "PUSH_GROUP_ID", bean.getPushGroupId() )
                    .addField( "OWNER", bean.getOwner() )
                    .addField( "OWNER_ACCOUNT", bean.getOwnerAccount())
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return Group.TABLE_NAME;
    }


    
    
}
