/**    
 * 文件名：Ad.java    
 *    
 * 版本信息：    
 * 日期：2016-12-29    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mas.core.domain.entity.Entity;

/**
 * 广告处理
 * 
 * @author yixuan
 * @since 2016-12-29
 */
public class Ad extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "APP_AD";

    public enum Type {
        TEXT(0)
        /** 已支付 */
        , IMAGE(1);
        
        public int value() { return this.value; }
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private Type(int value)
        {
            this.value = value;
        }
    }
    
    private Object positionId;
    
    private String title; // 广告标题

    private Integer type; // 类型

    private String filePath; // 资源路径
    
    private String content; //文字内容

    private String url; // 访问URL

    private Date beginDate;

    private Date endDate;
    
    private String positionName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Object getPositionId() {
        return positionId;
    }

    public void setPositionId(Object positionId) {
        this.positionId = positionId;
    }

    
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    
    

}
