/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.ServiceException;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.shops.domain.entity.MortagageConfig;
import com.mas.shops.repository.dao.MortagageConfigDao;
import com.mas.shops.repository.query.MortagageConfigQueryDao;


/**
 * 会员。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class MortagageConfigService  extends BaseServiceImpl<MortagageConfig>{

    /**
     * 获取所有配置列表并显示用户余额
     * @param mid
     * @return
     */
    public List<MortagageConfig> getList(String mid) {
        return queryDao.getAll(mid);
    }
    
    @Override
    public Object persistence( MortagageConfig bean )
    {
        if(null != bean.getId()){
            MortagageConfig old = dao.get(bean.getId());
            if(!old.getWealthTypeId().toString().equals(bean.getWealthTypeId().toString())){
                if(queryDao.existWealthType(bean.getWealthTypeId()))
                    throw new ServiceException("已存在财富类型设置");
            }
        }else{
            if(queryDao.existWealthType(bean.getWealthTypeId()))
                throw new ServiceException("已存在财富类型设置");
        }
        return super.persistence(bean);
    }
    
    @Override
    protected CrudDao<MortagageConfig> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<MortagageConfig> queryDao() {
        return queryDao;
    }

    @Autowired
    private MortagageConfigDao dao;
    @Autowired
    private MortagageConfigQueryDao queryDao;
   
}