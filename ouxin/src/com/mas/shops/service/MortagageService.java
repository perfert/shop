/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.shops.domain.entity.Mortagage;
import com.mas.shops.repository.dao.MortagageDao;
import com.mas.shops.repository.query.MortagageQueryDao;


/**
 * 商家压金service
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class MortagageService  extends BaseServiceImpl<Mortagage>{

    /**
     * 获取没有退款的[没有退款的只有一个]
     * @param memberId
     * @return
     */
    public Mortagage getNoBackMortagage(String memberId) {
        if(StringUtils.isEmpty(memberId))
            return null;
        return queryDao.getNoBackMortagage(memberId);
    }
    
    /**
     * 更新押金状态
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(Object id, int status) {
        return dao.getNoBackMortagage(id,status);
    }
    
    @Override
    protected CrudDao<Mortagage> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<Mortagage> queryDao() {
        return queryDao;
    }


   
    @Autowired
    private MortagageDao dao;
    @Autowired
    private MortagageQueryDao queryDao;
    
    
}