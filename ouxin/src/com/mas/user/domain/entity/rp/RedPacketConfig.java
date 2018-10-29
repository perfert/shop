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
 * 类名称：   红包配置 
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-2  
 */
public class RedPacketConfig  extends Entity {
    
    private static final long serialVersionUID = -7808323061351444890L;
    
    public static final String TABLE_NAME = "RP_RED_PACKET_CONFIG";
    
    private BigDecimal max;    //最高
    private BigDecimal min;    //最低 
    private String name;
    private Boolean isDefault;
    
    public BigDecimal getMax() {
        return max;
    }
    public void setMax(BigDecimal max) {
        this.max = max;
    }
    public BigDecimal getMin() {
        return min;
    }
    public void setMin(BigDecimal min) {
        this.min = min;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    
}
