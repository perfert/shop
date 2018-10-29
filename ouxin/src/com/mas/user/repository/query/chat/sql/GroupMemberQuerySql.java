package com.mas.user.repository.query.chat.sql;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.GroupMember;
import com.mas.user.repository.query.chat.GroupMemberQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class GroupMemberQuerySql extends QuerySql<GroupMember> implements GroupMemberQueryDao{

    @Override
    public Long getCounts(Object groupId) {
       return DataAccessUtils.uniqueResult(
             getJdbcTemplate().query(
                               "SELECT COUNT(*) FROM " + tableName() + " WHERE GROUP_ID = ?"
                               , new Object[] { groupId }
                               , new SingleColumnRowMapper<Long>(Long.class)
                               )
                       );
    }
    
    @Override
    public boolean exist(String memberId, String groupId) {
        return
                0 < DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                "SELECT COUNT(bean.ID) FROM " + tableName() + " bean WHERE bean.MEMBER_ID = ? AND bean.GROUP_ID = ? "
                                , new Object[] { memberId, groupId }
                                , new SingleColumnRowMapper<Long>(Long.class)
                                )
                        );
    }

    
    @Override
    public List<Member> queryMembers(Object groupId) {
        String sql = "SELECT u.* FROM " + tableName() + "  bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID" +
        		" WHERE bean.GROUP_ID = ?";
        return
                        getJdbcTemplate().query(
                                sql
                                , new Object[] { groupId }
                                , new BeanPropertyRowMapper<Member>( Member.class )
                                );
    }
    
    @Override
    public List<GroupMember> queryAll(Object uId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(GroupMember query) {
        return
                new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "MEMBER_ACCOUNT", query.getMemberAccount() )
                .andEqWhereIfNotBlankValue( "GROUP_ID", query.getGroupId() );
    }

    @Override
    protected String tableName() {
        return GroupMember.TABLE_NAME;
    }

   
}
