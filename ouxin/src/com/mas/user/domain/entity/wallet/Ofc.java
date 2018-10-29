/**    
 * 文件名：Ofc.java    
 *    
 * 版本信息：    
 * 日期：2017-12-20    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.wallet;

import java.math.BigDecimal;

import com.mas.core.domain.entity.Entity;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-20  
 */
public class Ofc  extends Entity {
    
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "WAL_OFC";
    
    private String sn;       // 订单编号（唯一）
    private String address;  //被充值地址
    private String fromAddress;//充值地址用户
    private String payType; // 充值类型(支付方式)
    private BigDecimal totalFee;   // 充值金额
    private String apiType;    // aip 类型
    private Integer hasPay;
    
    private Object memberId; //被充值用户
    private Object wealthTypeId;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getHasPay() {
        return hasPay;
    }

    public void setHasPay(Integer hasPay) {
        this.hasPay = hasPay;
    }

    public Object getWealthTypeId() {
        return wealthTypeId;
    }

    public void setWealthTypeId(Object wealthTypeId) {
        this.wealthTypeId = wealthTypeId;
    }
    
    
}
