/**    
 * 文件名：MsgRecordDao.java    
 *    
 * 版本信息：    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.chat.MsgCollection;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    MsgCollectionDao
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface MsgCollectionDao extends CrudDao<MsgCollection>{

    boolean delete(Object msgCollectionId, String meberId);

}
