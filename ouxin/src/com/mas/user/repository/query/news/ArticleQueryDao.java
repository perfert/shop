/**    
 * 文件名：ArticleQueryDao.java    
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
import com.mas.user.domain.entity.news.Article;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface ArticleQueryDao extends QueryDao<Article>{
    
    /**
     * 
     * @param query 检索对象。
     * 
     * @return {@link List} or null
     */
    List<Article> queryAll( Object uId );


    /**
     * 通过会员ID获取文章分页列表
     */
    List<Article> queryByMemberId(boolean all,String userId,int pageNo,int pageSize);


    List<Article> queryArticleImgs(Object memberId, Integer num);


}