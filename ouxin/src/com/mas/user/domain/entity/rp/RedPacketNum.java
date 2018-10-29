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
 * 类名称：拼手气红包随机数 创建人：yixuan
 * 
 * @version v1.00 2017-12-2
 */
public class RedPacketNum extends Entity {

    private static final long serialVersionUID = -7808323061351444890L;

    public static final String TABLE_NAME = "RP_RED_PACKET_NUM";

    private BigDecimal cash; // 金额

    private Object rpId;     // 红包ID
    
    private Integer valid;       // 是否已领取
    
    private Integer isLuck;      //是否最大

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Object getRpId() {
        return rpId;
    }

    public void setRpId(Object rpId) {
        this.rpId = rpId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getIsLuck() {
        return isLuck;
    }

    public void setIsLuck(Integer isLuck) {
        this.isLuck = isLuck;
    }
    
    
    
}
