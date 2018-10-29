/**    
 * 文件名：MsgRecordQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2017-01-21   
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.chat;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.chat.MsgCollectionContent;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    聊天记录
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface MsgCollectionContentQueryDao extends QueryDao<MsgCollectionContent>{
    
    /**
     * 通过用户名获取用户收藏分页列表
     * @param username 用户名
     * @param pageNo 请求页面
     * @param pageSize 每页显示数量
     */
    List<MsgCollectionContent> queryAll(Object collectionId);
}