/*
 * Copyright (c) 2010, 2015, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.web.member.controller.news;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.mas.user.service.news.ArticleGoodService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.news.ArticleGoodDto;

/**
 * 点赞Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(ArticleLikeCtr.URI_PREFIX)
public class ArticleLikeCtr extends JsonCtr<ArticleGoodDto>{
	
    public static final String URI_PREFIX = MemberArticleCtr.URI_PREFIX + "/like";
    
    @Autowired private ArticleGoodService articleGoodService;
    
    private static final Logger log = LogManager.getLogger( ArticleLikeCtr.class );
    
    /**
     * 点赞
     * @param   sID   文章ID
     * @param   acctount    账号
     */
    @RequestMapping( value = "add", method = RequestMethod.POST )
    public void add(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ArticleGoodDto.class,log,new DoYourBuniness<ArticleGoodDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ArticleGoodDto dto)  {
                Object success = articleGoodService.good(dto.getMid(),dto.getArticleId());
                if( null != success ){
                    JSONObject result = new JSONObject();
                    result.put("id",success);
                    jsonObject.put(KEY_RESULT, result);
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,"delete.error",dto.getLang());
                }
            }
        });
    }
    
    /**
     * 取消点赞
     * @param   sID   文章ID
     * @param   acctount    账号
     */
    @RequestMapping( value = "cancel", method = RequestMethod.POST )
    public void cancel(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ArticleGoodDto.class,log,new DoYourBuniness<ArticleGoodDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ArticleGoodDto dto)  {
                Object success = articleGoodService.cancel(dto.getMid(),dto.getArticleId());
                if( null != success ){
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,"delete.error",dto.getLang());
                }
            }
        });
    }
	
	
}