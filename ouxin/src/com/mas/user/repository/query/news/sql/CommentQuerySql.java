/**    
 * 文件名：CommentQuerySql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.news.sql;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Friends;
import com.mas.user.domain.entity.news.Comment;
import com.mas.user.repository.query.news.CommentQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class CommentQuerySql extends QuerySql<Comment> implements CommentQueryDao{

    @Override
    public List<Comment> queryByArticleId(String userId,Object articleId) {
        String selectSql = null;
        Object[] object=  null;
        if(null != userId){
            selectSql = " SELECT distinct bean.MODIFY_DATE,bean.*,u.NICK_NAME AS NICK,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
                    " LEFT JOIN " + Friends.TABLE_NAME + " f ON f.FRIEND_ID = u.ID " + 
                    " WHERE bean.ARTICLE_ID = ? AND ( bean.MEMBER_ID =? OR f.MEMBER_ID =? )  ORDER BY bean.MODIFY_DATE ASC ";
            object = new Object[] {articleId , userId, userId};
        }else{
            selectSql = " SELECT bean.* FROM " + tableName() + " bean WHERE bean.ARTICLE_ID = ?  ORDER BY bean.MODIFY_DATE ASC ";
            object = new Object[] { articleId};
        }
            
        return getJdbcTemplate().query(
                selectSql
                ,  object
                , new BeanPropertyRowMapper<Comment>(Comment.class)
                ); 
    }
    
    @Override
    public List<Comment> queryAll(Object uId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(Comment query) {
        return
                new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "ACCOUNT", query.getAccount() )
                .andEqWhereIfNotBlankValue( "ARTICLE_ID", query.getArticleId() );
    }

    @Override
    protected String tableName() {
        return Comment.TABLE_NAME;
    }

   
}
