/**    
 * 文件名：ArticleGoodDao.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.news;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.news.ArticleGood;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface ArticleGoodQueryDao extends QueryDao<ArticleGood>{
    
    /**
     * 
     * @param query 检索对象。
     * 
     * @return {@link List} or null
     */
    List<ArticleGood> queryAll( Object uId );

    /**
     * 获取与文章ID有关的点赞
     * @param articleId 文章ID。
     * 
     */
    List<ArticleGood> queryByArticleId(String userId,Object articleId);

    /**
     * 是否存在点赞
     * @param memberId 会员ID。
     * @param articleId 文章ID。
     */
    boolean exit(Object memberId, Object articleId);

    /**
     * 获取与文章ID有关的点赞
     * @param memberId 会员ID。
     * @param articleId 文章ID。
     * 
     */
    ArticleGood queryByMemberArticleId(Object memberId, Object articleId);

}