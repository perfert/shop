/**    
 * 文件名：Ｍortagage.java    
 *    
 * 版本信息：    
 * 日期：2018-3-29    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.domain.entity;

import java.math.BigDecimal;

import com.mas.core.domain.entity.Entity;

/**
 * 类名称：压金 创建人：yixuan
 * 
 * @version v1.00 2018-3-29
 */
public class Mortagage extends Entity {

    private static final long serialVersionUID = 1058376900474293548L;

    public static final String TABLE_NAME = "SH_MORTAGAGE";
    
    private BigDecimal cash;

    private Object wealthId;

    private Object wealthTypeId;

    private Object memberId;
    
    private Integer status;

    public enum Status{
        /** 创建 */
        CREATE(0)
        /** 确认 */
        , RETURN(1)
        ;
        public int value() { return this.value; }
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private Status(int value)
        {
            this.value = value;
        }
    }
    
    //view
    private String typeName;
    
    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Object getWealthId() {
        return wealthId;
    }

    public void setWealthId(Object wealthId) {
        this.wealthId = wealthId;
    }

    public Object getWealthTypeId() {
        return wealthTypeId;
    }

    public void setWealthTypeId(Object wealthTypeId) {
        this.wealthTypeId = wealthTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    
}
