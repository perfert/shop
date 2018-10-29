/**    
 * 文件名：Order.java    
 *    
 * 版本信息：    
 * 日期：2018-4-19    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package com.mas.shops.domain.entity;

import java.math.BigDecimal;

import com.mas.core.domain.entity.Entity;

/**
 * 类名称：订单详情（与交易记录相关） 创建人：yixuan
 * 
 * @version v1.00 2018-4-19
 */
public class Order extends Entity {

    private static final long serialVersionUID = 5288894067481538529L;

    public static final String TABLE_NAME = "SH_ORDER";

    private Object shopsId;    //店铺ID

    private Object thirdOrderId;// 第三方订单ID

    private Object wealthTypeId;// 支付方式

    private Object wealthId;
    
    private Object memberId; //支付会员

    private String name; // 订单说明

    private String sn; // 订单编号

    private BigDecimal cash;
    
    private Integer orderState;

    public enum OrderState{
        /** 创建 */
        CREATE(0)
        /** 确认 */
        , CONFIRM(1)
        /** 退款 */
        , RETURN(2)
        ;
        public int value() { return this.value; }
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private OrderState(int value)
        {
            this.value = value;
        }
    }
    
    
    public Object getShopsId() {
        return shopsId;
    }

    public void setShopsId(Object shopsId) {
        this.shopsId = shopsId;
    }

    public Object getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(Object thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public Object getWealthTypeId() {
        return wealthTypeId;
    }

    public void setWealthTypeId(Object wealthTypeId) {
        this.wealthTypeId = wealthTypeId;
    }

    public Object getWealthId() {
        return wealthId;
    }

    public void setWealthId(Object wealthId) {
        this.wealthId = wealthId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    
}
