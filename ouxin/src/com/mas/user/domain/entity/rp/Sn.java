/**    
 * 文件名：Sn.java    
 *    
 * 版本信息：    
 * 日期：2017-12-13    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.rp;

import com.mas.core.domain.entity.Entity;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-13  
 */
public class Sn  extends Entity {

    private static final long serialVersionUID = 1L;
    
    /** 类型 */
    private int type;

    private Long lastValue;
    
    public static final String TABLE_NAME = "RP_SN";

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getLastValue() {
        return lastValue;
    }

    public void setLastValue(Long lastValue) {
        this.lastValue = lastValue;
    }
    
    
}
