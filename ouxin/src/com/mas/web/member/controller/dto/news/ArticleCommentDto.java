/**    
 * 文件名：LoginDto.java    
 *    
 * 版本信息：    
 * 日期：2017-11-1    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.web.member.controller.dto.news;

import com.mas.web.member.controller.dto.BaseDto;

/**
 * 类名称：登录DTO 创建人：yixuan
 * 
 * @version v1.00 2017-11-1
 */
public class ArticleCommentDto extends BaseDto {

    private String mid;

    private String articleId;
    
    private String commentId;
    
    private String content;
    
    private String tag;
    
    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    
}
