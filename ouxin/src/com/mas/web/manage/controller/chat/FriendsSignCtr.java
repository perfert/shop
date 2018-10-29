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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mas.common.verify.VerifyUtil;
import com.mas.common.web.ErrorMsg;
import com.mas.common.web.ErrorMsg.STATUS;
import com.mas.core.service.BaseService;
import com.mas.user.domain.entity.chat.Friends;
import com.mas.user.service.chat.FriendsService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.chat.FriendsCtrDto;

/**
 * 好友 Controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( FriendsSignCtr.URI_PREFIX )
public class FriendsSignCtr extends ManageCtr<Friends, FriendsCtrDto>
{
	public static final String URI_PREFIX = "manage/chat/{id}/sign";
	
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
	public String page(@PathVariable int pageNo, FriendsCtrDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if( null == dto.getQuery() ){
			    Friends query = new Friends();
				dto.setQuery( query );
			}
			Object id = pathValue( params, "id" );
            if( VerifyUtil.isBlank( id ) ) 
                id = "0";
            dto.getQuery().setMemberId( id );
			service.queryPage( dto.getPager(pageNo), dto.getQuery() );
		} catch (Exception ex) {
			handlerException( ex, log, model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	/**
	 * 好友删除.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@RequestMapping("delete/{member}/{friend}")
	public String delete(@PathVariable String member,@PathVariable String friend,FriendsCtrDto dto,  HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			service.delete(member, friend);
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			ex.printStackTrace();
			return REDIRECT_VIEW_CREATE( dto, model, params );
		}
		bindMessage( model, new ErrorMsg( STATUS.success, "删除成功！" ) );
		return REDIRECT_VIEW_LIST( dto, model, params );
	}
	
	@Override
    protected String VIEW_PREFIX( Map<String, Object> params ){
        return "manage/chat/friends";
    }

	@Override
    protected String REDIRECT_VIEW_PREFIX( Map<String, Object> params ) 
    {
        return "manage/chat"+ SPLIT + pathValue( params, "id" ) + SPLIT + "friends";
    }
	
	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired
	private FriendsService service;
	
	private static final Logger log = LogManager.getLogger( FriendsSignCtr.class );

    @Override
    protected BaseService<Friends> service() {
        return service;
    }
}