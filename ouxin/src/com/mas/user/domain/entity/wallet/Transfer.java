/**    
 * 文件名：Transfer.java    
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
 * 类名称： 转账
 * 创建人：yixuan
 * 
 * @version v1.00 2017-12-20
 */
public class Transfer extends Entity {

    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "WAL_TRANS_FER";

    private String sn;

    private BigDecimal cash; // 金额

    private Integer hasPay;

    private Integer receiveType; // 收款方类型,0:用户

    private Object payType; // 充值类型(支付方式)

    private Object memberId; // 充值用户

    private Object receiveId; // 收款方
    
    //View(收款人昵称)
    private String nickName;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Integer getHasPay() {
        return hasPay;
    }

    public void setHasPay(Integer hasPay) {
        this.hasPay = hasPay;
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Object getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Object receiveId) {
        this.receiveId = receiveId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    
}
