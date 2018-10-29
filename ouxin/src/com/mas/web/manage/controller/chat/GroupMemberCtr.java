/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
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
import com.mas.user.domain.entity.chat.GroupMember;
import com.mas.user.service.chat.GroupMemberService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.chat.GroupMemberCtrDto;

/**
 * 群聊天 Controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( GroupMemberCtr.URI_PREFIX )
public class GroupMemberCtr extends ManageCtr<GroupMember, GroupMemberCtrDto>
{
	public static final String URI_PREFIX = "manage/chat/{group}/gmembers";
	
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
	public String page(@PathVariable int pageNo, GroupMemberCtrDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if( null == dto.getQuery() )
			{
			    GroupMember query = new GroupMember();
				dto.setQuery( query );
			}
			Object id = pathValue( params, "group" );
            if( VerifyUtil.isBlank( id ) ) 
                id = "0";
            dto.getQuery().setGroupId( id );
			service.queryPage( dto.getPager(pageNo), dto.getQuery() );
		} catch (Exception ex) {
			handlerException( ex, log, model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	@Override
	protected BaseService<GroupMember> service()
	{
		return service;
	}
	
	@Override
    protected String VIEW_PREFIX( Map<String, Object> params ){
        return "manage/chat/gmembers";
    }

    @Override
    protected String REDIRECT_VIEW_PREFIX( Map<String, Object> params ) 
    {
        return "manage/chat"+ SPLIT + pathValue( params, "group" ) + SPLIT + "gmembers";
    }


	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired
	private GroupMemberService service;
	@Autowired
	
	private static final Logger log = LogManager.getLogger( GroupMemberCtr.class );
}