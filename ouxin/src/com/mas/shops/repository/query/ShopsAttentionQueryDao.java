/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.shops.repository.query;

import java.util.List;

import com.mas.core.repository.query.QueryDao;
import com.mas.shops.domain.entity.ShopsAttention;

/**
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 */
public interface ShopsAttentionQueryDao extends QueryDao<ShopsAttention>{

    List<ShopsAttention> queryAttention(String mid);

    boolean exist(String memberId, String shopsId);

    boolean updateAttention(String memberId, String shopsId, int look);

}