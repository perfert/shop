/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query;

import com.mas.common.orm.util.Page;
import com.mas.core.repository.query.QueryDao;
import com.mas.shops.domain.entity.Shops;

/**
 * 商家
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
public interface ShopsQueryDao extends QueryDao<Shops>{

    Shops getByMemberId(String mid,boolean containCertificate);

    void queryPage(Page page, String query);

    Shops get(String memberId, String shopsId);

    boolean isExist(String mid);

    boolean checkOrder(String memberId, String shopsId);

}