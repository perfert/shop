/**    
 * 文件名：RedPacket.java    
 *    
 * 版本信息：    
 * 日期：2017-12-2    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.rp;

import java.math.BigDecimal;

import com.mas.core.domain.entity.Entity;

/**    
 * 类名称： 红包  
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public class RedPacket  extends Entity {
    
    private static final long serialVersionUID = -7808323061351444890L;
    
    public static final String TABLE_NAME = "RP_RED_PACKET";
    
    private Integer type;       //0:普通红包,1:拼手气红包(普通红包自己不能领取,拼手气红包自己能)
    private Integer receiveType; //0:单个,1:多个
    private BigDecimal cash;    // 总金额
    private BigDecimal receiveCash;
    private Integer num;          //总个数
    private Integer receiveNum;   //已领取个数
    private String remark;     //附言
    private Integer hasPay;    //支付状态
    private Integer hasBack;   //退货状态
    private String sn;
    
    private Object memberId;  //发红包人
    private Object receiveId; //单人
    private Object groupId;   //多人
    private Object payType; // 充值类型ID
    //View
    private String nickName;
    private String avatar;
    
    public enum Type {
        SIMPLE(0,""),
        LUCK(1,"");
        
        public int value() { return this.value; }
        public String label() { return this.label; }
        
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private final String label;
        private Type(int value, String label)
        {
            this.value = value;
            this.label = label;
        }
    }
    
    public enum ReceiveType {
        SINGLE(0,""),
        MANY(1,"");
        
        public int value() { return this.value; }
        public String label() { return this.label; }
        
        @Override public String toString() { return String.valueOf( value() ); } 
        
        private final int value;
        private final String label;
        private ReceiveType(int value, String label)
        {
            this.value = value;
            this.label = label;
        }
    }
    
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    
    public BigDecimal getReceiveCash() {
        return receiveCash;
    }

    public void setReceiveCash(BigDecimal receiveCash) {
        this.receiveCash = receiveCash;
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
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

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getHasPay() {
        return hasPay;
    }

    public void setHasPay(Integer hasPay) {
        this.hasPay = hasPay;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public Integer getHasBack() {
        return hasBack;
    }

    public void setHasBack(Integer hasBack) {
        this.hasBack = hasBack;
    }

}
