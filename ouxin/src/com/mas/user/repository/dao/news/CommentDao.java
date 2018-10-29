/**    
 * 文件名：CommentDao.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.news;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.news.Comment;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface CommentDao extends CrudDao<Comment> {
    
    boolean delete(Object commentId, String userId,String tag);

}
