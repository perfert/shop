/**    
 * 文件名：AdService.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.app.Ad;
import com.mas.user.repository.dao.app.AdDao;
import com.mas.user.repository.query.app.AdQueryDao;

/**    
 * 项目名称：chat    
 * 类名称：    广告Service
 * 创建人：yixuan  
 * @version v1.00
 *     
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class AdService extends BaseServiceImpl<Ad> {

    public List<Ad> queryAdListByPositionId(Object positionId) {
        return queryDao.queryAdListByPositionId(positionId);
    }
    
    
    @Override
    protected CrudDao<Ad> crudDao()
    {
        return dao;
    }

    @Override
    protected QueryDao<Ad> queryDao()
    {
        return queryDao;
    }
    
    @Autowired private AdDao dao;
    @Autowired private AdQueryDao queryDao;
    
    
    
}
