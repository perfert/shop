/**    
 * 文件名：CreateRedPacketVo.java    
 *    
 * 版本信息：    
 * 日期：2017-12-8    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.shops.domain.vo;

import java.math.BigDecimal;

import com.mas.shops.domain.entity.MortagageConfig;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-8  
 */
public class MortagageConfigVo extends MortagageConfig{

    private static final long serialVersionUID = 6342236938438335322L;
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    
}
