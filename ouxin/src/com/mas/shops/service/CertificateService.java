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
import com.mas.shops.domain.entity.Certificate;
import com.mas.shops.repository.dao.CertificateDao;
import com.mas.shops.repository.query.CertificateQueryDao;

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
public class CertificateService  extends BaseServiceImpl<Certificate>{

    @Override
    protected CrudDao<Certificate> crudDao() {
        return dao;
    }

    @Override
    protected QueryDao<Certificate> queryDao() {
        return queryDao;
    }

    public Certificate getByShopsId(String id){
        if(StringUtils.isNotEmpty(id))
            return queryDao.getByShopsId(id);
        else
            return null;
    }
    
    @Autowired
    private CertificateDao dao;

    @Autowired
    private CertificateQueryDao queryDao;
    
}
