/**    
 * 文件名：Article.java    
 *    
 * 版本信息：    
 * 日期：2016-12-16    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.news;

import java.util.List;

import com.mas.core.domain.entity.Entity;

/**
 * 
 * 项目名称：chat 类名称： 文章 创建人：yixuan
 * 
 * @version v1.00
 * 
 */
public class Article extends Entity {

    private static final long serialVersionUID = 1L;
    
    public static final String TABLE_NAME = "NEWS_ARTICLE";

    public static final String BIG_IMAGE_PATH_PREFIX = "big_";
    
    public static final String Article_IMAGE_PATH_PREFIX = "news";
    
    public static final String Article_IMAGE_PATH_SPLIT = "split";
    
    public enum Type{
        TEXT(0), IMAGE(1), VIDEO(2);
     
        private int value;
     
        private Type(int value) {
            this.value = value;
        }
     
        public int getValue() {
            return value;
        }
     
        public boolean isRest() {
            return false;
        }
    }
    
    private Object memberId;
    
    private String account;
    
    private Integer type;   //文章类型,0文本消息,1图文,2视频

    private String content;
    
    //图片集合,真正地址为 prefix + imgPath[0]
    private String imgPath; 

    private String location;

    private String videoPath;

    private String thumbPath;
    
    private Long good;

    private String token;

    private String prefix;
    
    private List<Comment> comments;
    
    private List<ArticleGood> articleGoods;
    
    private String nick;
    
    private String avatar;
    
    
    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getGood() {
        return good;
    }

    public void setGood(Long good) {
        this.good = good;
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

    public List<ArticleGood> getArticleGoods() {
        return articleGoods;
    }

    public void setArticleGoods(List<ArticleGood> articleGoods) {
        this.articleGoods = articleGoods;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
}
