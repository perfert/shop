/**    
 * 文件名：MsgRecordQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.chat;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.chat.MsgRecord;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    聊天记录
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface MsgRecordQueryDao extends QueryDao<MsgRecord>{
    
    /**
     * 
     * @param query 检索对象。
     * 
     * @return {@link List} or null
     */
    List<MsgRecord> queryAll( Object uId );

    MsgRecord getByToken(String from, String token);

    MsgRecord getAllInfo(Object id);
}