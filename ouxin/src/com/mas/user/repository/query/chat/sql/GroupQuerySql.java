/**    
 * 文件名：GroupQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.chat.sql;

import java.util.List;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Group;
import com.mas.user.domain.entity.chat.GroupMember;
import com.mas.user.repository.query.chat.GroupQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class GroupQuerySql extends QuerySql<Group> implements GroupQueryDao{

    @Override
    public List<Group> getListByUserId(String userId) {
        String selectSql = " SELECT bean.* FROM " + tableName() + " bean LEFT JOIN " + GroupMember.TABLE_NAME + " g ON bean.ID = g.GROUP_ID " + 
                " WHERE  g.MEMBER_ID = ?";
        return getJdbcTemplate().query(
                selectSql
                ,  new Object[] { userId }
                , new BeanPropertyRowMapper<Group>(Group.class)
                ); 
    }

    /*@Override
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
    }*/
    
    @Override
    public boolean deleteContain(String groupId, String[] memberIds, int size) {
        StringBuilder buf = new StringBuilder(" SELECT gm.* FROM " + tableName() + " bean LEFT JOIN " + GroupMember.TABLE_NAME + " gm ON bean.ID = gm.GROUP_ID" +
        		" WHERE bean.ID = " + groupId +" AND gm.MEMBER_ID IN (");
        for(int i = 0; i < memberIds.length; i++) {  
            if (i > 0) buf.append(",");  
            buf.append(memberIds[i]);  
        }  
        buf.append(")");  
        buf.append(" ORDER BY gm.CREATE_DATE DESC " + pageSql(1, 2));
       List<GroupMember> list = getJdbcTemplate().query(
                buf.toString()
                ,  new Object[] { }
                , new BeanPropertyRowMapper<GroupMember>(GroupMember.class)
                );
       for(GroupMember g : list){
           System.out.println(g.getMemberAccount());
       }
        return false;
    }
    
    /**
     * 获取群组会员头像,根据群组会员创建时间排序
     */
    @Override
    public List<String> getGroupMemberImages(String groupId, int size) {
        // TODO Auto-generated method stub
        return getJdbcTemplate().query(
                "SELECT u.AVATAR FROM " + tableName() + " bean LEFT JOIN " + GroupMember.TABLE_NAME + " gm ON bean.ID = gm.GROUP_ID" +
                        " LEFT JOIN " + Member.TABLE_NAME + " u ON gm.MEMBER_ID = u.ID " + 
                		" WHERE bean.id = ? " + 
                		" ORDER BY gm.CREATE_DATE DESC " + pageSql(1, size)
                , new Object[] { groupId }
                , new SingleColumnRowMapper<String>(String.class)
                );
    }
    
    @Override
    public List<Member> getGroupMember(String groupId, int size) {
        return getJdbcTemplate().query(
                "SELECT u.ID AS ID,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN " + GroupMember.TABLE_NAME + " gm ON bean.ID = gm.GROUP_ID" +
                        " LEFT JOIN " + Member.TABLE_NAME + " u ON gm.MEMBER_ID = u.ID " + 
                        " WHERE bean.id = ? " + 
                        " ORDER BY gm.CREATE_DATE DESC " + pageSql(1, size)
                , new Object[] { groupId }
                , new BeanPropertyRowMapper<Member>(Member.class)
                );
    }

    @Override
    public Long count(Object groupId) {
        String selectSql = " select COUNT(*) FROM " + GroupMember.TABLE_NAME + " bean " +
        		" where bean.GROUP_ID = ? ";
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(selectSql, new Object[] { groupId}, new SingleColumnRowMapper<Long>(Long.class)) );
    }

    
    @Override
    public Group queryByPushGroupId(String pushGroupId) {
        String sql = "SELECT bean.* FROM " + tableName() + " bean WHERE bean.PUSH_GROUP_ID = ?";
        return
                DataAccessUtils.uniqueResult(
                        getJdbcTemplate().query(
                                sql
                                , new Object[] { pushGroupId }
                                , new BeanPropertyRowMapper<Group>( Group.class )
                                )
                        );
    }

    
    @Override
    public List<Group> queryAll(Object uId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(Group query) {
        return
                new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "NAME", query.getName() );
    }

    @Override
    protected String tableName() {
        return Group.TABLE_NAME;
    }

    
}
