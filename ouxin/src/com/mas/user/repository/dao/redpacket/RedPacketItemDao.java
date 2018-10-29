/**    
 * 文件名：RedPacketItemDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.redpacket;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.rp.RedPacketItem;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface RedPacketItemDao  extends CrudDao<RedPacketItem> {

    boolean updateLuck(Object redPacketNumId);
}

