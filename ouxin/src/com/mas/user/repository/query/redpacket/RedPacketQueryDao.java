/**    
 * 文件名：RedPacketQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.redpacket;

import java.util.Date;
import java.util.List;

import com.mas.common.orm.util.Page;
import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.rp.RedPacket;
import com.mas.user.domain.entity.vo.StatisSendVo;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface RedPacketQueryDao extends QueryDao<RedPacket>{

    RedPacket getRedPacketAndUserInfo(Object id);
    
    void queryPage(String mid, Page page,String wealthTypeId);

    StatisSendVo statisticsSend(String memberId,String wealthTypeId);

    List<RedPacket> getExpire(Date date, int pageNo, int pageSize);

    void getExpire(Date date, Page page);
}
