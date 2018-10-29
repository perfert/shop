/**    
 * 文件名：ArticleGoodSql.java    
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
import com.mas.user.domain.entity.news.ArticleGood;
import com.mas.user.repository.dao.news.ArticleGoodDao;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Repository
public class ArticleGoodSql extends CrudSql<ArticleGood> implements ArticleGoodDao{

    @Override
    protected InsertSql insertSql( ArticleGood bean )
    {
        return
                new InsertSql( tableName() )
                    .addField( "MEMBER_ID", bean.getMemberId() )
                    .addField( "ARTICLE_ID", bean.getArticleId() )
                    .addField( "ACCOUNT", bean.getAccount() )
                    .addField( "TOKEN", bean.getToken() )
                    .addField( "GOOD_STATE", bean.getGoodState() ) ;
    }

    @Override
    protected UpdateSql updateSql( ArticleGood bean )
    {
        return
                new UpdateSql( tableName() )
                    .addField( "GOOD_STATE", bean.getGoodState() ) 
                    .andEqWhere( "ID", bean.getId() );
    }
    
    @Override
    protected String tableName() {
        return ArticleGood.TABLE_NAME;
    }
    
    
}
