/**    
 * 文件名：RedPacketItem.java    
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
 * 类名称： 红包子单 创建人：yixuan
 * 
 * @version v1.00 2017-12-2
 */
public class RedPacketItem extends Entity {

    private static final long serialVersionUID = -6310227228316171496L;

    public static String TABLE_NAME = "RP_RED_PACKET_ITEM";

    private Integer type; // 0:普通,1:拼手气红包

    private Integer isLuck;// 手气最好

    private BigDecimal cash; // 余额

    private String sn;
    
    private Object memberId; // 领取人

    private Object rpId;   // 发红包ID
    
    private Object wealthTypeId;

    //http://blog.csdn.net/xluren/article/details/32746183
    private Object rpNumId; // 只有type=1,才有记录
    
    //mysql更新记录时设置自动更新时间戳
    
    //View
    private String nickName;
    
    private String avatar;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsLuck() {
        return isLuck;
    }

    public void setIsLuck(Integer isLuck) {
        this.isLuck = isLuck;
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

    public Object getRpId() {
        return rpId;
    }

    public void setRpId(Object rpId) {
        this.rpId = rpId;
    }

    public Object getRpNumId() {
        return rpNumId;
    }

    public void setRpNumId(Object rpNumId) {
        this.rpNumId = rpNumId;
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
