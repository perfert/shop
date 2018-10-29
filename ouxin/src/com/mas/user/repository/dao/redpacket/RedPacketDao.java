/**    
 * 文件名：RedPacketDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.dao.redpacket;

import java.math.BigDecimal;
import java.util.Date;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.rp.RedPacket;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface RedPacketDao extends CrudDao<RedPacket> {

    /**
     * 更新红包
     * @param receiveNum  领取红包数
     * @param receiveCash
     * @param max
     * @return
     */
    boolean updateRePacket(String rpId,int receiveNum, BigDecimal receiveCash, BigDecimal max);

    boolean updatePaySuccess(String rpId, String payTypeId, Date payDate);

    boolean updateBack(String rpId);
    

}

