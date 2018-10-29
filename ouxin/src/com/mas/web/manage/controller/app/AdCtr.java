/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.app;

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
import com.mas.user.domain.entity.app.Ad;
import com.mas.user.service.app.AdPositionService;
import com.mas.user.service.app.AdService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.app.AdDto;

/**
 * 评论 Controller.
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping( AdCtr.URI_PREFIX )
public class AdCtr extends ManageCtr<Ad, AdDto>
{
	public static final String URI_PREFIX = "manage/app/{position}/ad";

	@Autowired private AdPositionService positionService;
    private static final String upladPath = "upload/ad/";
	
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
	public String page(@PathVariable int pageNo, AdDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if( null == dto.getQuery() ){
			    Ad query = new Ad();
				dto.setQuery( query );
			}
			Object id = pathValue( params, "position" );
            if( VerifyUtil.isBlank( id ) ) 
                id = "0";
            dto.getQuery().setPositionId(id);
			service.queryPage( dto.getPager(pageNo), dto.getQuery() );
		} catch (Exception ex) {
			handlerException( ex, log, model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	@Override
	public String edit(@PathVariable String id, AdDto dto, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
		    dto.setAdPositionList(positionService.getAll());
		    dto.setTypes(com.mas.user.domain.entity.app.Ad.Type.values());
			if ( VerifyUtil.isNotBlank(id) ) {
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
	public String create(AdDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			dto.getBean().setId( null );
			String uri = super.uploadUri( request, upladPath, "file" );
            if( VerifyUtil.isNotBlank( uri ) ) {
                dto.getBean().setFilePath( uri );
            }
			service.persistence( dto.getBean() );
		} catch (Exception ex) {
			handlerException( ex, log(), model );
			ex.printStackTrace();
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
	public String modify(AdDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		Object id = pathValue(params, "id");
		try {
			if( null == id || VerifyUtil.isBlank( id.toString() ) )
			{
				throw new Exception("更新数据ID不能为空！");
			}
			String uri = super.uploadUri( request, upladPath, "file" );
            if( VerifyUtil.isNotBlank( uri ) ) {
                dto.getBean().setFilePath( uri );
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
	protected BaseService<Ad> service()
	{
		return service;
	}
	
	@Override
    protected String VIEW_PREFIX( Map<String, Object> params ){
        return "manage/app/ad";
    }

    @Override
    protected String REDIRECT_VIEW_PREFIX( Map<String, Object> params ) 
    {
        return "manage/app"+ SPLIT + pathValue( params, "position" ) + SPLIT + "ad";
    }
    
	@Override 
	protected Logger log()
	{
		return log;
	}
	
	@Autowired
	private AdService service;
	
	private static final Logger log = LogManager.getLogger( AdCtr.class );
}