/**    
 * 文件名：RedPacketItemService.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.service.redpacket;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.common.orm.util.Page;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.rp.RedPacketItem;
import com.mas.user.domain.entity.vo.StatisReceiveVo;
import com.mas.user.repository.dao.redpacket.RedPacketItemDao;
import com.mas.user.repository.query.redpacket.RedPacketItemQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class RedPacketItemService extends BaseServiceImpl<RedPacketItem> {

    /**
     * 获取领红包记录
     * @param rpId   红包ID
     * @param openId 开红包人ID
     */
    public RedPacketItem get(String rpId, String openId) {
        return queryDao.get(rpId,openId);
    }
    
    /**
     * 是否领取红包
     * @param prId 红包ID
     * @param memberId 会员ID
     * @return
     */
    public boolean isExist(String rpId, Object openId) {
        return queryDao.isExist(rpId,openId);
    }

    /**
     * 设置最好运红包
     * @param id
     * @return
     */
    public boolean updateLuck(Object rpId) {
        return dao.updateLuck(rpId);
    }

    /**
     * 获取红包领取记录[关联用户的nickName,avatar]
     * @param rpId
     * @return
     */
    public List<RedPacketItem> getList(String rpId) {
        return queryDao.getList(rpId);
    }
    
    /**
     * 获取红包领取记录[关联用户的nickName,avatar]
     * @param rpId 红包ID
     * @return
     */
    public void queryPage(String rpId, Page page) {
        if(!StringUtils.isNotEmpty(rpId))
            throw new ServiceException("para error");
        queryDao.queryPage(rpId,page);
    }
    
    /**
     * 获取红包领取记录[关联用户的nickName,avatar]
     * @param memberId 会员ID
     * @return
     */
    public void queryPageByMemberId(String memberId, Page page,String wealthTypeId) {
        if(!StringUtils.isNotEmpty(memberId))
            throw new ServiceException("para error");
        queryDao.queryPageByMemberId(memberId,page,wealthTypeId);
    }
    
    public StatisReceiveVo statisticsReceive(String memberId,String wealthTypeId) {
        if(!StringUtils.isNotEmpty(memberId))
            throw new ServiceException("para error");
        return queryDao.statisticsReceive(memberId,wealthTypeId);
    }
    
    @Override
    protected CrudDao<RedPacketItem> crudDao() {
        // TODO Auto-generated method stub
        return dao;
    }

    @Override
    protected QueryDao<RedPacketItem> queryDao() {
        // TODO Auto-generated method stub
        return queryDao;
    }
    
    
    @Autowired private RedPacketItemDao dao;
    @Autowired private RedPacketItemQueryDao queryDao;
   
    
}
