/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.manage.controller.news;

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
import com.mas.user.domain.entity.news.ArticleGood;
import com.mas.user.service.news.ArticleGoodService;
import com.mas.web.manage.controller.ManageCtr;
import com.mas.web.manage.controller.dto.news.ArticleGoodCtrDto;

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
@RequestMapping( ArticleGoodCtr.URI_PREFIX )
public class ArticleGoodCtr extends ManageCtr<ArticleGood, ArticleGoodCtrDto>
{
	public static final String URI_PREFIX = "manage/news/{article}/good";

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
	public String page(@PathVariable int pageNo, ArticleGoodCtrDto dto, HttpServletRequest request, ModelMap model, @PathVariable Map<String, Object> params)
	{
		try {
			if( null == dto.getQuery() ){
			    ArticleGood query = new ArticleGood();
				dto.setQuery( query );
			}
			Object id = pathValue( params, "article" );
            if( VerifyUtil.isBlank( id ) ) 
                id = "0";
            dto.getQuery().setArticleId(id);
			service.queryPage( dto.getPager(pageNo), dto.getQuery() );
		} catch (Exception ex) {
			handlerException( ex, log, model );
		}
		return VIEW_LIST( dto, model, params );
	}
	
	@Override
	public String edit(@PathVariable String id, ArticleGoodCtrDto dto, ModelMap model, @PathVariable Map<String, Object> params)
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
	public String create(ArticleGoodCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
	{
		try {
			dto.getBean().setId( null );
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
	public String modify(ArticleGoodCtrDto dto, HttpServletRequest request, RedirectAttributes model, @PathVariable Map<String, Object> params)
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
	protected BaseService<ArticleGood> service()
	{
		return service;
	}
	
	@Override
    protected String VIEW_PREFIX( Map<String, Object> params ){
        return "manage/news/good";
    }

    @Override
    protected String REDIRECT_VIEW_PREFIX( Map<String, Object> params ) 
    {
        return "manage/news"+ SPLIT + pathValue( params, "article" ) + SPLIT + "good";
    }
    
	@Override
	protected Logger log()
	{
		return log;
	}
	
	@Autowired
	private ArticleGoodService service;
	
	private static final Logger log = LogManager.getLogger( ArticleGoodCtr.class );
}