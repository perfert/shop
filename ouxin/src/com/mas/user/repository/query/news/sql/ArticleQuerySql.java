/**    
 * 文件名：ArticleQuerySql.java    
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
import com.mas.user.domain.entity.news.Article;
import com.mas.user.repository.query.news.ArticleQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    ArticleQuerySql
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class ArticleQuerySql extends QuerySql<Article> implements ArticleQueryDao{

    @Override
    public List<Article> queryArticleImgs(Object memberId, Integer num) {
        String selectSql = " SELECT bean.ID,bean.IMG_PATH,bean.PREFIX  FROM " + tableName() + " bean  WHERE bean.MEMBER_ID =?  ORDER BY bean.MODIFY_DATE DESC " + pageSql(0, num);
        return getJdbcTemplate().query(
                selectSql
                ,  new Object[] { memberId } 
                , new BeanPropertyRowMapper<Article>(Article.class)
                ); 
    }

    @Override
    public List<Article> queryByMemberId(boolean all,String memberId, int pageNo, int pageSize) {
        String selectSql = null;
        Object[] objects = null;
        if(all){
            /*selectSql = " SELECT distinct bean.MODIFY_DATE,bean.*,u.NICK_NAME AS NICK,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
                    " LEFT JOIN " + Friends.TABLE_NAME + " f ON f.FRIEND_ID = u.ID " + 
                    " WHERE bean.MEMBER_ID =? OR f.MEMBER_ID =?  ORDER BY bean.MODIFY_DATE DESC " + pageSql(pageNo, pageSize);
            objects = new Object[] { memberId , memberId};*/
            selectSql = " SELECT ar.* FROM(" +
                " (SELECT bean.*,u.NICK_NAME AS NICK,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID" +
        		" LEFT JOIN "  + Friends.TABLE_NAME + " f ON f.FRIEND_ID = bean.MEMBER_ID WHERE f.MEMBER_ID =? ) " + 
                " UNION ALL (SELECT bean2.*,u2.NICK_NAME AS NICK,u2.AVATAR AS AVATAR FROM " + tableName() + " bean2 LEFT JOIN " + Member.TABLE_NAME + " u2 ON bean2.MEMBER_ID = u2.ID " +
                " WHERE bean2.MEMBER_ID =?)  )" +
                " ar ORDER BY ar.id DESC "+ pageSql(pageNo, pageSize);
            objects = new Object[] { memberId , memberId};
        }else{
            selectSql = " SELECT bean.*,u.NICK_NAME AS NICK,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
                    "  WHERE bean.MEMBER_ID =?  ORDER BY bean.MODIFY_DATE DESC " + pageSql(pageNo, pageSize);
            objects = new Object[] { memberId };
        }
        //查出所有好友,并且好友应该的人是我
        return getJdbcTemplate().query(
                selectSql
                ,  objects 
                , new BeanPropertyRowMapper<Article>(Article.class)
                ); 
    }
    
    @Override
    public List<Article> queryAll(Object uId) {
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(Article query) {
        return
                new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "ACCOUNT", query.getAccount() );
    }

    @Override
    protected String tableName() {
        return Article.TABLE_NAME;
    }

    
   

}
