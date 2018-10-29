/**    
 * 文件名：WealthRule.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.wallet;

import java.math.BigDecimal;

import com.mas.core.domain.entity.Entity;

/**    
 * 类名称：财富规则    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public class WealthRule extends Entity {

    private static final long serialVersionUID = -6135748332435593302L;
    
    public static final String TABLE_NAME = "WAL_WEALTH_RULE";

    private BigDecimal currentDepositFree;
    private BigDecimal free;
    private Integer depositState;
    private Integer wealthType;
    
    public BigDecimal getCurrentDepositFree() {
        return currentDepositFree;
    }
    public void setCurrentDepositFree(BigDecimal currentDepositFree) {
        this.currentDepositFree = currentDepositFree;
    }
    public BigDecimal getFree() {
        return free;
    }
    public void setFree(BigDecimal free) {
        this.free = free;
    }
    public Integer getDepositState() {
        return depositState;
    }
    public void setDepositState(Integer depositState) {
        this.depositState = depositState;
    }
    public Integer getWealthType() {
        return wealthType;
    }
    public void setWealthType(Integer wealthType) {
        this.wealthType = wealthType;
    }
    
    
}
