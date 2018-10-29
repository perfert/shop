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
import com.mas.user.service.news.CommentService;
import com.mas.web.member.controller.JsonCtr;
import com.mas.web.member.controller.dto.news.ArticleCommentDto;

/**
 * 评论Ctr
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author yixuan
 */
@Controller
@RequestMapping(MemberCommentCtr.URI_PREFIX)
public class MemberCommentCtr extends JsonCtr<ArticleCommentDto>{
	
    public static final String URI_PREFIX = MemberArticleCtr.URI_PREFIX + "/comment";
    
    @Autowired private CommentService commentService;
    
    private static final Logger log = LogManager.getLogger( MemberCommentCtr.class );
    
       /* "code":200,
        "message":"成功",
        "data":{
            "time":时间,
            "id":评论ID,
            "userId":评论人ID,
            "nickname":评论人昵称,
            "content":评论内容,
            "cbNickname":被评论人昵称,
            "cbUserId":被评论人ID
        }*/

    /**
     * 发表评论
     * @param   username    账号
     * @param   sID   文章ID
     */
    @RequestMapping( value = "add", method = RequestMethod.POST )
    public void publish(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
        doInCallBack(response,body,ArticleCommentDto.class,log,new DoYourBuniness<ArticleCommentDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ArticleCommentDto dto) {
                Object id = commentService.create(dto.getMid(),dto.getArticleId(),dto.getContent(),dto.getTag());
                JSONObject result = new JSONObject();
                result.put("id",id);
                jsonObject.put(KEY_RESULT, result);
                success(jsonObject,null,dto.getLang());
            }
        });
    }
    
    /**
     * 删除评论
     * @param body
     * @param request
     * @param response
     */
	@RequestMapping( value = "delete", method = RequestMethod.POST )
    public void delete(@RequestBody String body,HttpServletRequest request, HttpServletResponse response){
	    doInCallBack(response,body,ArticleCommentDto.class,log,new DoYourBuniness<ArticleCommentDto>() {
            
            @Override
            public void handleBusiness(JSONObject jsonObject,ArticleCommentDto dto) {
                String commentId = null;
                if(dto.getCommentId().equalsIgnoreCase("LOCAL")){
                    commentId = "0";
                }else
                    commentId = dto.getCommentId();
                boolean success = commentService.delete(dto.getMid(),commentId,dto.getTag());
                if( success ){
                    success(jsonObject,null,dto.getLang());
                } else {
                    error(jsonObject,"delete.error",dto.getLang());
                }
            }
        });
    }
	
	
}