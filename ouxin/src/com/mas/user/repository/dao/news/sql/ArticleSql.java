/**    
 * 文件名：ArticleSql.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.news.sql;

import org.springframework.stereotype.Repository;

import com.mas.common.file.util.FileUtil;
import com.mas.common.orm.sql.domain.InsertSql;
import com.mas.common.orm.sql.domain.UpdateSql;
import com.mas.reposiroty.springjdbc.dao.CrudSql;
import com.mas.user.domain.entity.news.Article;
import com.mas.user.domain.entity.news.ArticleGood;
import com.mas.user.domain.entity.news.Comment;
import com.mas.user.repository.dao.news.ArticleDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class ArticleSql extends CrudSql<Article> implements ArticleDao{

    @Override
    public boolean delete( Object id ){
        deleteFile(id);
        return 0 <
                getJdbcTemplate().update(
                    "DELETE FROM " + tableName() + " WHERE ID = ?"
                    , new Object[] { id }
                    );
    }
    
    @Override
    public boolean delete(Object userId, String articleId) {
        deleteFile(articleId);
        return 0 < getJdbcTemplate().update("DELETE FROM " + tableName() + " WHERE ID = ? AND MEMBER_ID = ? ", new Object[] { articleId, userId});
    }
    
    
    @Override
    protected InsertSql insertSql( Article bean )
    {
        return
                new InsertSql( tableName() )
                  
                    .addField( "ACCOUNT", bean.getAccount() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "IMG_PATH", bean.getImgPath() )
                    .addField( "THUMB_PATH", bean.getThumbPath() )
                    .addField( "VIDEO_PATH", bean.getVideoPath() )
                    .addField( "LOCATION", bean.getLocation() )
                    .addField( "PREFIX", bean.getPrefix() )
                    .addField( "GOOD", bean.getGood() )
                    .addField( "TOKEN", bean.getToken() ) ;
    }

    @Override
    protected UpdateSql updateSql( Article bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "CONTENT", bean.getContent() )
                    .addField( "ACCOUNT", bean.getAccount() )
                    .addField( "TYPE", bean.getType() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "LOCATION", bean.getLocation() )
                    .addField( "PREFIX", bean.getPrefix() )
                    .addField( "IMG_PATH", bean.getImgPath() )
                    .addField( "THUMB_PATH", bean.getThumbPath() )
                    .addField( "VIDEO_PATH", bean.getVideoPath() )
                    .addField( "GOOD", bean.getGood() )
                    .addField( "TOKEN", bean.getToken() ) 
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return Article.TABLE_NAME;
    }

    private void deleteFile(Object articleId){
        getJdbcTemplate().update("DELETE FROM " + Comment.TABLE_NAME + " WHERE ARTICLE_ID = ?", new Object[] { articleId });
        getJdbcTemplate().update("DELETE FROM " + ArticleGood.TABLE_NAME + " WHERE ARTICLE_ID = ?", new Object[] { articleId });
        
        try {
            Article article = get(articleId);
            String[] images = article.getImgPath().split("split");
            if(images.length > 0){
                String[] fileUri = new String[images.length * 2];
                for (int i = 0; i < images.length; i++) {
                    fileUri[i] = article.getPrefix() + images[i];
                    fileUri[i + images.length ] = article.getPrefix() + Article.BIG_IMAGE_PATH_PREFIX + images[i];
                }
                FileUtil.remove(fileUri);
            }
        } catch (Exception e) {
        }
       
    }
}
