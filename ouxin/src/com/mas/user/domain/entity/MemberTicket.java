/*
 * Copyright (c) 2010, 2016, MAS and/or its affiliates. All rights reserved.
 * MAS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mas.user.domain.entity;

import com.mas.core.domain.entity.Entity;

/**
 * 二维码
 * 
 * @version v1.00
 * 
 * @since JDK 1.7
 * 
 * @author MAS
 */
public class MemberTicket extends Entity {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "U_MEMBER_TICKET";

    private Object memberId;
    
    private Object wealthId;

    private Integer otype;  //二维码类型,0:我的,1:付款,2:收款
    
    private String image;  //二维码存储地址
    
    private String ticket; // 可用作加密

    private String content;
    
    public enum Type {
        MINE(0,""),
        RECEIVE(1,""),
        RECEIVE_NUM(2,"");
        
        public int value() { return this.value; }
        public String label() { return this.label; }
        
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private final String label;
        private Type(int value, String label)
        {
            this.value = value;
            this.label = label;
        }
    }
    /**
     * `ACTION_NAME` varchar(32) NOT NULL COMMENT
     * '二维码类型（QR_SCENE：临时；QR_LIMIT_SCENE：永久(int参数)；QR_LIMIT_STR_SCENE：永久(字符串参数)）'
     * , `TICKET` varchar(255) NOT NULL COMMENT '二维码', `IMAGE` varchar(255) NOT
     * NULL COMMENT '二维码图片', `NAME` varchar(255) NOT NULL COMMENT '二维码名称',
     */

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getOtype() {
        return otype;
    }

    public void setOtype(Integer otype) {
        this.otype = otype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getWealthId() {
        return wealthId;
    }

    public void setWealthId(Object wealthId) {
        this.wealthId = wealthId;
    }

    
}
