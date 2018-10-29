/**    
 * 文件名：CommentQueryDao.java    
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
import com.mas.user.domain.entity.news.Comment;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface CommentQueryDao extends QueryDao<Comment>{
    
    /**
     * 
     * @param query 检索对象。
     * 
     * @return {@link List} or null
     */
    List<Comment> queryAll( Object uId );

    /**
     * 获取文章评论
     * @param userId 用户ID。
     * @param articleId 文章ID。
     * 
     */
    List<Comment> queryByArticleId(String userId,Object articleId);

}