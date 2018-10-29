/**    
 * 文件名：FriendsApply.java    
 *    
 * 版本信息：    
 * 日期：2016-12-7    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.chat;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.chat.FriendsApply;

/**    
 *     
 * 项目名称：chat    
 * 类名称：    好友申请表
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
public interface FriendsApplyDao extends CrudDao<FriendsApply>{

    /**
     * 更新好友申请表状态
     */
    boolean updateType(String applyId, String receiveId,Integer type);

}
