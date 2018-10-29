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

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;
import com.mas.common.orm.sql.domain.SimpleQuerySql;
import com.mas.reposiroty.springjdbc.query.QuerySql;
import com.mas.user.domain.entity.Member;
import com.mas.user.domain.entity.chat.Friends;
import com.mas.user.domain.entity.news.ArticleGood;
import com.mas.user.repository.query.news.ArticleGoodQueryDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class ArticleGoodQuerySql extends QuerySql<ArticleGood> implements ArticleGoodQueryDao{

    @Override
    public ArticleGood queryByMemberArticleId(Object memberId, Object articleId) {
        return DataAccessUtils.uniqueResult(
                getJdbcTemplate().query(
                        " SELECT bean.* FROM " + tableName() + " bean WHERE MEMBER_ID = ? AND ARTICLE_ID = ? "
                        , new Object[] { memberId,articleId }
                        , new BeanPropertyRowMapper<ArticleGood>(ArticleGood.class)
                        ));
    }

    @Override
    public boolean exit(Object memberId, Object articleId) {
        long count = DataAccessUtils.uniqueResult(
                    getJdbcTemplate().query(
                            " SELECT COUNT(*) FROM " + tableName() + " WHERE MEMBER_ID = ? AND ARTICLE_ID = ? "
                            , new Object[] { memberId,articleId }
                            , new SingleColumnRowMapper<Long>(Long.class)
                            ));
        return 0 < count;
    }
    
    @Override
    public List<ArticleGood> queryByArticleId(String userId,Object articleId) {
        String selectSql = null;
        Object[] object=  null;
        if(null != userId){
            selectSql = " SELECT distinct bean.MODIFY_DATE,bean.*,u.NICK_NAME AS NICK,u.AVATAR AS AVATAR FROM " + tableName() + " bean LEFT JOIN " + Member.TABLE_NAME + " u ON bean.MEMBER_ID = u.ID " +
                    " LEFT JOIN " + Friends.TABLE_NAME + " f ON f.FRIEND_ID = u.ID " + 
                    " WHERE bean.ARTICLE_ID = ? and bean.GOOD_STATE = ? and (bean.MEMBER_ID =? OR f.MEMBER_ID =?)  ORDER BY bean.MODIFY_DATE ASC ";
            object = new Object[] { articleId ,ArticleGood.GOOD, userId,userId};
        }else{
            selectSql = " SELECT bean.* FROM " + tableName() + " bean WHERE bean.ARTICLE_ID = ? AND bean.GOOD_STATE = ? ORDER BY bean.MODIFY_DATE ASC ";
            object = new Object[] { articleId,ArticleGood.GOOD };
        }
            
        return getJdbcTemplate().query(
                selectSql
                ,  object
                , new BeanPropertyRowMapper<ArticleGood>(ArticleGood.class)
                ); 
    }

    @Override
    public List<ArticleGood> queryAll(Object uId) {
        return null;
    }

    @Override
    protected SimpleQuerySql querySql(ArticleGood query) {
        return  new SimpleQuerySql( tableName() )
                .andEqWhereIfNotBlankValue( "ACCOUNT", query.getAccount() )
                .andEqWhereIfNotBlankValue( "ARTICLE_ID", query.getArticleId() );
    }

    @Override
    protected String tableName() {
        return ArticleGood.TABLE_NAME;
    }

    

    
}
