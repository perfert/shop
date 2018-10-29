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
import com.mas.user.domain.entity.chat.Msg;
import com.mas.user.service.chat.MsgService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.chat.MsgCtrDto;

/**
 * 信息 Controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
@Controller
@RequestMapping( MsgCtr.URI_PREFIX )
public class MsgCtr extends ManageCtr<Msg, MsgCtrDto>
{
	public static final String URI_PREFIX = "manage/chat/msg";

	
	@RequestMapping("view/{id}")
	public String edit(@PathVariable String id, MsgCtrDto dto, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if ( VerifyUtil.isNotBlank(id) ) 
			{
				dto.setBean(service.get(id));
			}
		} catch (Exception ex) {
			handlerException( ex, log(), model );
		}
		return VIEW_INPUT( dto, model, params );
	}
	
	/**
	 * 添加数据.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@Override
	public String create(MsgCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			dto.getBean().setId( null );
			service.persistence( dto.getBean() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			return REDIRECT_VIEW_CREATE( dto, model, params );
		}
		bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		return REDIRECT_VIEW_LIST( dto, model, params );
	}
	
	/**
	 * 更新数据.
	 * 
	 * @param dto Controller DTO Context.
	 * @param model 模型参数.
	 * @param params {@link PathVariable} 参数集
	 * 
	 * @return view uri
	 */
	@Override
	public String modify(MsgCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		Object id = pathValue(params, "id");
		try {
			if( null == id || VerifyUtil.isBlank( id.toString() ) )
			{
				throw new Exception("更新数据ID不能为空！");
			}
			service.persistence( dto.getBean() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			return REDIRECT_VIEW_EDIT( dto, model, params, id );
		}
		bindMessage( model, new ErrorMsg( STATUS.success, "操作成功！" ) );
		return REDIRECT_VIEW_LIST( dto, model, params );
	}

	@Override
	protected BaseService<Msg> service()
	{
		return service;
	}
	
	@Override
    protected String VIEW_PREFIX( Map<String, Object> params ){
        return URI_PREFIX;
    }

	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired
	private MsgService service;
	
	private static final Logger log = LogManager.getLogger( MsgCtr.class );
}