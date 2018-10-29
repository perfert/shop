/**    
 * 文件名：GroupMemberSql.java    
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
import com.mas.user.domain.entity.chat.GroupMember;
import com.mas.user.repository.dao.chat.GroupMemberDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class GroupMemberSql extends CrudSql<GroupMember> implements GroupMemberDao{

    @Override
    public boolean deleteGroupMember(Object groupId,String userName) {
        return 0 <
                getJdbcTemplate().update(
                    "DELETE FROM " + tableName() + " WHERE GROUP_ID = ? AND MEMBER_ACCOUNT = ?"
                    , new Object[] { groupId,userName }
                    );
    }
    
    /**
     * SELECT * FROM Persons WHERE LastName IN ('Adams','Carter')
     */
    @Override
    public boolean deleteGroupMember(Object groupId, String[] memberIds) {
        StringBuilder buf = new StringBuilder("DELETE FROM " + tableName() + " WHERE GROUP_ID = " + groupId +" AND MEMBER_ID IN (");
        for(int i = 0; i < memberIds.length; i++) {  
            if (i > 0) buf.append(",");  
            buf.append(memberIds[i]);  
        }  
        buf.append(")");  
        return 0 < getJdbcTemplate().update(
                buf.toString()
            , new Object[] {  }
            );
    }

    
    @Override
    protected InsertSql insertSql(GroupMember bean) {
        return
                new InsertSql( tableName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "MEMBER_ACCOUNT", bean.getMemberAccount() )
                    .addField( "GROUP_ID", bean.getGroupId() );
    }
    
    @Override
    protected UpdateSql updateSql(GroupMember bean) {
        return
                new UpdateSql( tableName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "MEMBER_ACCOUNT", bean.getMemberAccount() )
                    .addField( "TARGET_ID", bean.getGroupId() )
                    .andEqWhere( "ID", bean.getId() );
    }

    @Override
    protected String tableName() {
        return GroupMember.TABLE_NAME;
    }

    
    
    
}
