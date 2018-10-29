/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.chat;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mas.common.verify.VerifyUtil;
import com.mas.core.service.BaseService;
import com.mas.user.domain.entity.wallet.WealthRecord;
import com.mas.user.service.wallet.WealthRecordService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.chat.WealthRecordDto;
/**
 * 会员账户。
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( MemberFundsLogCtr.URI_PREFIX )
public class MemberFundsLogCtr extends ManageCtr<WealthRecord, WealthRecordDto>
{
    public static final String URI_PREFIX = "manage/chat/member/{id}/record";
	/**
	 * 根据页码, 页行, 分页检索数据.
	 * 
	 * @param pageNo 页码.
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@Override
	public String page(@PathVariable int pageNo, WealthRecordDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if( null == dto.getQuery() ){
				dto.setQuery( new WealthRecord() );
			}
			Object id = pathValue( params, "id" );
			if( VerifyUtil.isBlank( id ) ) 
			    id = "0";
			dto.getQuery().setMemberId( id );
			
 			service.queryPage( dto.getPager(pageNo), dto.getQuery() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	@Override
	protected String VIEW_PREFIX( Map<String, Object> params )
	{
		return "manage/chat/record";
	}
	
	@Override
	protected String REDIRECT_VIEW_PREFIX( Map<String, Object> params ) 
	{
		return "manage/chat/member"+ SPLIT + pathValue( params, "id" ) + SPLIT + "record";
	}

	@Override
	protected BaseService<WealthRecord> service()
	{
		return null;
	}

	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired private WealthRecordService service;
	
	private static final Logger log = LogManager.getLogger( MemberFundsLogCtr.class );
}