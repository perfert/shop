/**    
 * 文件名：RedPacketItemQueryDao.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.repository.query.redpacket;

import java.util.List;

import com.mas.common.orm.util.Page;
import com.mas.core.repository.query.QueryDao;
import com.mas.user.domain.entity.rp.RedPacketItem;
import com.mas.user.domain.entity.vo.StatisReceiveVo;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public interface RedPacketItemQueryDao extends QueryDao<RedPacketItem>{

    RedPacketItem get(String rpId, String openId);

    boolean isExist(String rpId, Object openId);

    List<RedPacketItem> getList(String rpId);

    void queryPage(String rpId, Page page);

    StatisReceiveVo statisticsReceive(String memberId,String wealthTypeId);

    void queryPageByMemberId(String memberId, Page page,String wealthTypeId);

}
