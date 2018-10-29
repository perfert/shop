/**    
 * 文件名：WealthType.java    
 *    
 * 版本信息：    
 * 日期：2018-2-27    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.wallet;

import java.math.BigDecimal;

import com.mas.core.domain.entity.Entity;

/**
 * 类名称： 财富类型 创建人：yixuan
 * 
 * @version v1.00 2018-2-27
 */
public class WealthType extends Entity {

    private static final long serialVersionUID = -3199608671768948231L;
    public static final String TABLE_NAME = "WAL_WEALTH_TYPE";
    
    private String name;

    private Boolean isDefault;
    
    private String domainUrl;
    
    private String addressUrl;

    private BigDecimal init; // 初始值
    
    //View
    private BigDecimal balance; //会员可用金额
    private BigDecimal cost;    //订单花费
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public BigDecimal getInit() {
        return init;
    }

    public void setInit(BigDecimal init) {
        this.init = init;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAddressUrl() {
        return addressUrl;
    }

    public void setAddressUrl(String addressUrl) {
        this.addressUrl = addressUrl;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    
}
