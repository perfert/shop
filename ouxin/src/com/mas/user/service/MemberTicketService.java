/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mas.core.repository.dao.CrudDao;
import com.mas.core.repository.query.QueryDao;
import com.mas.core.service.impl.BaseServiceImpl;
import com.mas.user.domain.entity.MemberTicket;
import com.mas.user.repository.dao.MemberTicketDao;
import com.mas.user.repository.query.MemberTicketQueryDao;

/**
 * 
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class MemberTicketService extends BaseServiceImpl<MemberTicket>
{
	public MemberTicket queryByMemberId(Object memberId)
	{
		return dao.queryByMemberId( memberId );
	}
	
	public MemberTicket queryByMemberId(String memberId, int type) {
        return dao.queryByMemberId( memberId,type );
    }
	
	/**
	 * 根据财富类型获取用户二维码
	 * @param wealthId
	 * @param type
	 * @return
	 */
	public MemberTicket queryByWealthId(Object wealthId, int type) {
        return dao.queryByWealthId( wealthId,type );
    }
	
	@Override
	protected CrudDao<MemberTicket> crudDao()
	{
		return dao;
	}

	@Override
	protected QueryDao<MemberTicket> queryDao()
	{
		return queryDao;
	}
	
	@Autowired private MemberTicketDao dao;
	@Autowired private MemberTicketQueryDao queryDao;
    
    
}
