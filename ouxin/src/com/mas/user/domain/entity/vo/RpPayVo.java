/**    
 * 文件名：PayVo.java    
 *    
 * 版本信息：    
 * 日期：2017-12-8    
 * Copyright 足下 Corporation 2017     
 * 版权所有    
 *    
 */
package com.mas.user.domain.entity.vo;

/**    
 * 类名称：    
 * 创建人：yixuan  
 * @version v1.00
 * 2017-12-8  
 */
public class RpPayVo {
    
    private int code;
    private Object wealthRecordId;
    private Object receiveWealthRecordId;
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public Object getWealthRecordId() {
        return wealthRecordId;
    }
    public void setWealthRecordId(Object wealthRecordId) {
        this.wealthRecordId = wealthRecordId;
    }
    public Object getReceiveWealthRecordId() {
        return receiveWealthRecordId;
    }
    public void setReceiveWealthRecordId(Object receiveWealthRecordId) {
        this.receiveWealthRecordId = receiveWealthRecordId;
    }
    
}
