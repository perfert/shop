/**    
 * 文件名：ArticleDao.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.news;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.news.Article;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface ArticleDao extends CrudDao<Article> {

    /**
     * 根据ID，获取数据对象。
     * 
     * @param articleId 文章对象ID。
     * @param memberAccount 会员账号。
     * 
     * @return boolean。
     */
    boolean delete(Object articleId, String memberAccount);

}
