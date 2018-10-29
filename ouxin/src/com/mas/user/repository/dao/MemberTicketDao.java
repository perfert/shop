/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.repository.dao;

import com.mas.core.repository.dao.CrudDao;
import com.mas.user.domain.entity.MemberTicket;

/**
 * 
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public interface MemberTicketDao extends CrudDao<MemberTicket>
{
	MemberTicket queryByMemberId(Object memberId);

    MemberTicket queryByMemberId(String memberId, int type);

    MemberTicket queryByWealthId(Object wealthId, int type);
}
