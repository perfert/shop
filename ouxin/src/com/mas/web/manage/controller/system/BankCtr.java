/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.system;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mas.core.service.BaseService;
import com.mas.system.domain.entity.Bank;
import com.mas.system.service.impl.BankService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.system.BankCtrDto;

/**
 * 
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( BankCtr.URI_PREFIX )
public class BankCtr extends ManageCtr<Bank, BankCtrDto>
{
	public static final String URI_PREFIX = "manage/system/bank";

	@Override
	protected String VIEW_PREFIX( Map<String, Object> params )
	{
		return URI_PREFIX;
	}

	@Override
	protected BaseService<Bank> service()
	{
		return service;
	}

	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired private BankService service;
	
	private static final Logger log = LogManager.getLogger( BankCtr.class );
}
