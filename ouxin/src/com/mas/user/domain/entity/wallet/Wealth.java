/**    
 * 文件名：Wealth.java    
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
 * 类名称： 财富   
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public class Wealth extends Entity {

    private static final long serialVersionUID = -2130920298519594957L;
    public static final String TABLE_NAME = "WAL_WEALTH";
    
    public static final String RP_SEND = "wealth.rp.send";
    public static final String RP_RECEIVE = "wealth.rp.receive";
    public static final String RP_RETURN = "wealth.rp.return";
    public static final String RP_PALTFORM = "wealth.rp.platform";
    public static final String WALLET_SEND = "wealth.qrcode.send";
    public static final String WALLET_RECEIVE = "wealth.qrcode.receive";
    public static final String WALLET_OFC = "wealth.ofc.recharge";
    public static final String WALLET_WITHDRAW = "wealth.withdraw";
    public static final String WALLET_WITHDRAW_HAND = "wealth.withdraw.hand";
    public static final String WALLET_WITHDRAW_SUCCESS = "wealth.withdraw.success";
    public static final String WALLET_WITHDRAW_FAILURE = "wealth.withdraw.error";
    public static final String WALLET_WITHDRAW_RETURN = "wealth.withdraw.return";
    public static final String MORTAGAGE_CREATE = "wealth.mortagage.create";//扣押金
    public static final String MORTAGAGE_BACK = "wealth.mortagage.back";//扣押金
    public static final String ORDER_CONFIRM = "wealth.order.create";
    public static final String ORDER_RETURN = "wealth.order.confirm";
    
    public static final int PAY_TYPE_OFC = 0;
    
    private BigDecimal cash;   // 余额
    private BigDecimal freeze; // 冻结
    private String address;
    private Object wealthType;      // 财富类型

    private Object memberId;   // 会员对应该的财富
    
    //View
    private String payPassword;
    private String avatar;
    private String nickName;
    private String wealthTypeName;
    
    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }

    public Object getMemberId() {
        return memberId;
    }

    public void setMemberId(Object memberId) {
        this.memberId = memberId;
    }

    public Object getWealthType() {
        return wealthType;
    }

    public void setWealthType(Object wealthType) {
        this.wealthType = wealthType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getWealthTypeName() {
        return wealthTypeName;
    }

    public void setWealthTypeName(String wealthTypeName) {
        this.wealthTypeName = wealthTypeName;
    }
    
    
}
