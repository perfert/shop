/**    
 * 文件名：Comment.java    
 *    
 * 版本信息：    
 * 日期：2016-12-16    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.news;

import com.mas.core.domain.entity.Entity;

/**
 * 
 * 项目名称：chat 类名称： 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class Comment extends Entity {

    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "NEWS_COMMENT";
    
    private Object memberId;

    private Object articleId;
    
    private String account;

    private String content;

    private String tag;

    private String token;
    
    //view展示,因为nickName是随时变动,没有写死
    private String nick;
    
    private String avatar;

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Object getArticleId() {
        return articleId;
    }

    public void setArticleId(Object articleId) {
        this.articleId = articleId;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    
}
