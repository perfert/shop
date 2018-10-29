/**    
 * 文件名：RedPacketConfigQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.redpacket;

import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.rp.RedPacketConfig;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface RedPacketConfigQueryDao extends QueryDao<RedPacketConfig>{

    RedPacketConfig getDefault();

}
