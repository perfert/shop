/**    
 * 文件名：RedPacket.java    
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
 * 类名称：  红包退款
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-21  
 */
public class WithdrawBack  extends Entity {
    
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "WAL_WITHDRAW_BACK";
    
    private Integer status; // 状态

    private BigDecimal cash; // 余额
    
    private String sn;      //编号

    private Object wdId;   // 提款ID
    
    private Object memberId; //退还人

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Object getWdId() {
        return wdId;
    }

    public void setWdId(Object wdId) {
        this.wdId = wdId;
    }

    
}
