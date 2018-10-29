/**    
 * 文件名：Shops.java    
 *    
 * 版本信息：    
 * 日期：2018-3-22    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * 类名称：商家 创建人：yixuan
 * 
 * @version v1.00 2018-3-22
 */
public class ShopsAttention extends Entity {

    private static final long serialVersionUID = 8070285671419794451L;

    public static final String TABLE_NAME = "SH_SHOPS_ATTENTION";

    private Object memberId;

    private Object shopsId;

    private String title;
    private String logo;
    private String detail;// 企业简介
    private String link;
    
    private Integer attention;

    public static final int LOOK = 1;
    public static final int NO_LOOK = 0;
    
    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Object getShopsId() {
        return shopsId;
    }

    public void setShopsId(Object shopsId) {
        this.shopsId = shopsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    
}
