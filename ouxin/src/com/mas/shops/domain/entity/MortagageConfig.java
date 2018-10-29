/**    
 * 文件名：MortagageConfig.java    
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
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2018-3-29  
 */
public class MortagageConfig extends Entity {

    private static final long serialVersionUID = 3840369174819179742L;

    public static final String TABLE_NAME = "SH_MORTAGAGE_CONFIG";
    
    private String name;
    
    private BigDecimal cash;

    private Object wealthId;

    private Object wealthTypeId;
    
    //view
    private String typeName;
    
    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    
}
