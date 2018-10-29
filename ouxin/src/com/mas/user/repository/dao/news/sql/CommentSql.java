/**    
 * 文件名：CommentSql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.news.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.news.Comment;
import com.mas.user.repository.dao.news.CommentDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class CommentSql extends CrudSql<Comment> implements CommentDao{

    @Override
    public boolean delete(Object commentId, String userId, String tag) {
        return 0 <
                getJdbcTemplate().update(
                    "DELETE FROM " + tableName() + " WHERE ID = ? AND MEMBER_ID = ? OR MEMBER_ID = ? AND TAG = ? "
                    , new Object[] { commentId, userId,userId,tag}
                    );
    }
    
    @Override
    protected InsertSql insertSql( Comment bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "ARTICLE_ID", bean.getArticleId() )
                    .addField( "ACCOUNT", bean.getAccount() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "TAG", bean.getTag() )
                    .addField( "TOKEN", bean.getToken() );
    }

    @Override
    protected UpdateSql updateSql( Comment bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "ARTICLE_ID", bean.getArticleId() )
                    .addField( "ACCOUNT", bean.getAccount() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "TAG", bean.getTag() )
                    .addField( "TOKEN", bean.getToken() )
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return Comment.TABLE_NAME;
    }

    
    
    
}
