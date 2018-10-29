/**    
 * 文件名：Wthdraw.java    
 *    
 * 版本信息：    
 * 日期：2017-12-27    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.wallet;

import java.math.BigDecimal;

import com.mas.core.domain.entity.Entity;

/**
 * 类名称： 提现 
 * 创建人：yixuan
 * 
 * 现在是改交易记录
 * 正常应该是生产2条交易记录,对应一条提现表;但是有2条记录,收入和支出又不明确
 * 
 * 
 * @version v1.00 2017-12-27
 */
public class Withdraw extends Entity {

    private static final long serialVersionUID = 523060002351237790L;

    public static final String TABLE_NAME = "WAL_WITHDRAW";
    
    public enum Status {
        WITHDRAW_IN_HAND(0,""),//提币申请提交
        WITHDRAW_SUCCESS(1,""),//提币到账
        WITHDRAW__FAIL(2,"");  //提币失败  
        
        public int value() { return this.value; }
        public String label() { return this.label; }
        
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private final String label;
        private Status(int value, String label)
        {
            this.value = value;
            this.label = label;
        }
    }
    
    private String address;

    private BigDecimal num;

    private Integer status;

    private String sn;

    private Object memberId;
    
    private Object wealthTypeId;
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Object getWealthTypeId() {
        return wealthTypeId;
    }

    public void setWealthTypeId(Object wealthTypeId) {
        this.wealthTypeId = wealthTypeId;
    }

    
}
