/**    
 * 文件名：RedPacketConfigService.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.service.redpacket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.rp.RedPacketConfig;
import com.mas.user.repository.dao.redpacket.RedPacketConfigDao;
import com.mas.user.repository.query.redpacket.RedPacketConfigQueryDao;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class RedPacketConfigService extends BaseServiceImpl<RedPacketConfig> {
    
    /**
     * 获取默认红包配置
     * @return
     */
    public RedPacketConfig getDefault(){
        return queryDao.getDefault();
    }

    @Override
    protected CrudDao<RedPacketConfig> crudDao() {
        // TODO Auto-generated method stub
        return dao;
    }

    @Override
    protected QueryDao<RedPacketConfig> queryDao() {
        // TODO Auto-generated method stub
        return queryDao;
    }
    
    @Autowired private RedPacketConfigDao dao;
    @Autowired private RedPacketConfigQueryDao queryDao;
}
